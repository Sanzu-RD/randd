package com.souchy.randd.commons.net.netty.server;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilder;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilderPort;
import com.souchy.randd.commons.tealwaters.commons.Factory;
import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.GlobalEventExecutor;

public final class NettyServer {

	private final boolean SSL; 	// = System.getProperty("ssl") != null;
	private final int PORT; 	// = Integer.parseInt(System.getProperty("port", "8007"));
	private final MessageToByteEncoder<?> encoder;
	private final Factory<ByteToMessageDecoder> decoder;
	private final NettyHandler handler;
	private final SslContext sslCtx;

	/** list of channels (respresents clients) */
	private Channel serverChannel;
	ChannelGroup channels = new DefaultChannelGroup("all", GlobalEventExecutor.INSTANCE);
	
	
	NettyServer(int port, boolean ssl, MessageToByteEncoder<?> encoder, Factory<ByteToMessageDecoder> decoder, NettyHandler handler) 
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
	}

	public static NettyServerBuilderPort create() {
		NettyServerBuilder builder = new NettyServerBuilder();
		return builder;
	}

	public void start() throws Exception {
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 100)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(channelInit);

			// Start the server.
			ChannelFuture f = b.bind(PORT).sync();

			serverChannel = f.channel();
			
			// Wait until the server socket is closed.
			f.channel().closeFuture().sync();
		} finally {
			// Shut down all event loops to terminate all threads.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}
	
	
	private ChannelInitializer<SocketChannel> channelInit = new ChannelInitializer<>() {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline p = ch.pipeline();
			if (sslCtx != null) {
				p.addLast(sslCtx.newHandler(ch.alloc()));
			}
			//Log.info("Channel init : " + ch);
			p.addLast(connectionHandler);
			p.addLast(decoder.create()); 
			p.addLast(encoder); 
			p.addLast(handler); 
		}
	};

	/**
	 * Sharable connectino handler which removes channels from the group when they go inactive
	 */
	private ChannelInboundHandlerAdapter connectionHandler = new ChannelInboundHandlerAdapter() {
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelInactive(ctx);
			Log.info("Channel active : " + ctx.channel());
			channels.add(ctx.channel()); // register channel to all
		}
		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			super.channelInactive(ctx);
			Log.info("Channel inactive : " + ctx.channel());
			channels.remove(ctx.channel());
		}
		@Override
		public boolean isSharable() {
			return true;
		};
	};
	
	
	public void sendAll(BBMessage msg) {
		channels.forEach(c -> c.writeAndFlush(msg));
	}

}
