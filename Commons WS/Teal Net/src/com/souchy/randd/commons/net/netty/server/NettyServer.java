package com.souchy.randd.commons.net.netty.server;

import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilder;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilderPort;
import com.souchy.randd.commons.tealwaters.commons.Factory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
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

public class NettyServer {

	private final boolean SSL; 	// = System.getProperty("ssl") != null;
	private final int PORT; 	// = Integer.parseInt(System.getProperty("port", "8007"));
	private final Factory<MessageToByteEncoder<?>> encoderFactory;
	private final Factory<ByteToMessageDecoder> decoderFactory;
	private final Factory<NettyHandler> handlerFactory;
	private final SslContext sslCtx;

	NettyServer(int port, boolean ssl, Factory<MessageToByteEncoder<?>> encoder, Factory<ByteToMessageDecoder> decoder, Factory<NettyHandler> handler)
			throws Exception {
		this.PORT = port;
		this.SSL = ssl;
		this.encoderFactory = encoder;
		this.decoderFactory = decoder;
		this.handlerFactory = handler;

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
			// p.addLast(new LoggingHandler(LogLevel.INFO));
			p.addLast(decoderFactory.create()); // new BBMessageDecoder(new BBMessageFactory()));
			p.addLast(encoderFactory.create()); // new BBMessageEncoder());
			p.addLast(handlerFactory.create()); // new NettyHandler());
		}
	};

}
