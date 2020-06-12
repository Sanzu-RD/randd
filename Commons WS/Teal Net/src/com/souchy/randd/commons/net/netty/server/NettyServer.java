package com.souchy.randd.commons.net.netty.server;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilder;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilderPort;
import com.souchy.randd.commons.tealwaters.commons.Factory;
import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyServer {

	private final boolean SSL; 	// = System.getProperty("ssl") != null;
	private final int PORT; 	// = Integer.parseInt(System.getProperty("port", "8007"));
	protected final MessageToByteEncoder<?> encoder;
	protected final Factory<ByteToMessageDecoder> decoder;
	protected final NettyHandler handler;
	private final SslContext sslCtx;
	
	/** server-side channel, closing this closes every connection to it */
	public Channel serverChannel;
	/** list of channels (respresents clients) */
	ChannelGroup channels = new DefaultChannelGroup("all", GlobalEventExecutor.INSTANCE);
	
	
	protected NettyServer(int port, boolean ssl, MessageToByteEncoder<?> encoder, Factory<ByteToMessageDecoder> decoder, NettyHandler handler) 
			//Factory<MessageToByteEncoder<?>> encoder, Factory<ByteToMessageDecoder> decoder, Factory<NettyHandler> handler)
			throws Exception {
		this.PORT = port;
		this.SSL = ssl;
		this.encoder = encoder;
		this.decoder = decoder;
		this.handler = handler;

		// Configure SSL.
		if (SSL) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
		} else {
			sslCtx = null;
		}
		
		// Start
		start();
	}

	public static NettyServerBuilderPort create() {
		NettyServerBuilder builder = new NettyServerBuilder();
		return builder;
	}

	public void start() throws Exception {
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
//			.option(ChannelOption.SO_BACKLOG, 100)
//			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(channelInit);
		
		// Start the server.
		ChannelFuture f = b.bind(PORT).sync();

		serverChannel = f.channel();
		
		serverChannel.closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				bossGroup.shutdownGracefully();
				workerGroup.shutdownGracefully();
			}
		});
			
		// Wait until the server socket is closed.
		//serverChannel.closeFuture().sync();
	}
	
	public void block() throws InterruptedException {
		// Wait until the server socket is closed.
		serverChannel.closeFuture().sync();
	}
	
	private ChannelInitializer<SocketChannel> channelInit = new ChannelInitializer<>() {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// Log.info("Channel init : " + ch);
			ChannelPipeline pipe = ch.pipeline();
			if(sslCtx != null) 
				pipe.addLast(sslCtx.newHandler(ch.alloc()));
			initPipeline(pipe);
		}
	};
	
	protected LengthFieldPrepender lengthEncoder = new LengthFieldPrepender(2);
	protected LengthFieldBasedFrameDecoder lengthDecoder = new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2);
	
	/**
	 * Initialize the ChannelPipeline any new client
	 */
	public void initPipeline(ChannelPipeline pipe) {
		pipe.addLast(connectionHandler);
		
		pipe.addLast(lengthEncoder);
		pipe.addLast(encoder); 
		
		pipe.addLast(lengthDecoder);
		pipe.addLast(decoder.create()); 
		pipe.addLast(handler); 
	}

	/**
	 * Sharable connection handler which removes channels from the group when they go inactive
	 */
	public final ChannelInboundHandlerAdapter connectionHandler = new ChannelInboundHandlerAdapter() {
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			Log.info("Channel active : " + ctx.channel());
			channels.add(ctx.channel()); // register channel to all
			super.channelInactive(ctx);
		}
		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			Log.info("Channel inactive : " + ctx.channel());
			channels.remove(ctx.channel());
			super.channelInactive(ctx);
		}
		@Override
		public boolean isSharable() {
			return true;
		};
	};
	

	public void broadcast(BBMessage msg) {
		channels.forEach(c -> c.writeAndFlush(msg));
	}
	public void sendAll(BBMessage msg) {
		broadcast(msg);
	}

}
