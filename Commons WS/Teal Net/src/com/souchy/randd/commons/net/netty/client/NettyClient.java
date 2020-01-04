package com.souchy.randd.commons.net.netty.client;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.server.NettyHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyClient {

	private final boolean SSL; 
	private final String IP;
	private final int PORT; 
	private final MessageToByteEncoder<?> encoder;
	private final ByteToMessageDecoder decoder;
	private final NettyHandler handler;
	private final SslContext sslCtx;
	private final boolean logs;
	
	public Channel channel;
	
	protected NettyClient(String ip, int port, boolean ssl, boolean logs, MessageToByteEncoder<?> encoder, ByteToMessageDecoder decoder, NettyHandler handler)  throws Exception {
		this.IP = ip;
		this.PORT = port;
		this.SSL = ssl;
		this.encoder = encoder;
		this.decoder = decoder;
		this.handler = handler;
		this.logs = logs;

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
	
//	public static NettyClient create(String ip, int port, boolean ssl, boolean logs, 
//			MessageToByteEncoder<?> encoder, ByteToMessageDecoder decoder, NettyHandler handler) throws Exception {
//		return new NettyClient(ip, port, ssl, logs, encoder, decoder, handler);
//	}

	private void start() throws InterruptedException {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
        //try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(channelInit);
            
            // Start the client.
            ChannelFuture f = b.connect(IP, PORT).sync(); // (5)

            channel = f.channel();

            channel.closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
    			@Override
    			public void operationComplete(Future<? super Void> future) throws Exception {
    				workerGroup.shutdownGracefully();
    			}
    		});
    		
            // Wait until the connection is closed.
            //f.channel().closeFuture().sync();
//        } catch (InterruptedException e) {
//			e.printStackTrace();
//		} finally {
//            workerGroup.shutdownGracefully();
//        }
	}
	
	/** block execution until the client closes */
	public void block() throws InterruptedException {
		channel.closeFuture().sync();
	}

	public void close() {
		channel.close();
	}
	
	
	public void write(BBMessage msg) {
		channel.writeAndFlush(msg);
	}

	private ChannelInitializer<SocketChannel> channelInit = new ChannelInitializer<>() {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline p = ch.pipeline();
			if (sslCtx != null) 
				p.addLast(sslCtx.newHandler(ch.alloc()));
			initPipeline(p);
		}
	};
	
	protected void initPipeline(ChannelPipeline pipe) {
		pipe.addLast(new LoggingHandler(LogLevel.INFO));
		pipe.addLast(decoder); 
		pipe.addLast(encoder); 
		pipe.addLast(handler); 
	}
	
	
}
