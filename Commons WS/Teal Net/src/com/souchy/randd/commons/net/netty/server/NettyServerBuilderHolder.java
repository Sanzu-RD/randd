package com.souchy.randd.commons.net.netty.server;

import com.souchy.randd.commons.tealwaters.commons.Factory;

import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

@SuppressWarnings("exports")
public final class NettyServerBuilderHolder {

	public static interface NettyServerBuilderPort {
		public NettyServerBuilderSSL port(int port);
	}

	public static interface NettyServerBuilderSSL {
		public NettyServerBuilderEncoder SSL(boolean ssl);
	}

	public static interface NettyServerBuilderEncoder {
		public NettyServerBuilderDecoder encoder(Factory<MessageToByteEncoder<?>> encoder);
	}

	public static interface NettyServerBuilderDecoder {
		public NettyServerBuilderHandler decoder(Factory<ByteToMessageDecoder> decoder);
	}

	public static interface NettyServerBuilderHandler {
		public NettyServerConfiguration handler(Factory<NettyHandler> handler);
	}

	public static interface NettyServerConfiguration {
		public NettyServerBuilderWrapper adapt();
	}

	public static interface NettyServerBuilderWrapper {
		public NettyServer wrap() throws Exception;
	}

	public static final class NettyServerBuilder implements NettyServerBuilderPort, NettyServerBuilderSSL, NettyServerBuilderEncoder, NettyServerBuilderDecoder,
			NettyServerBuilderHandler, NettyServerConfiguration, NettyServerBuilderWrapper {
		private boolean ssl;
		private int port;
		private Factory<MessageToByteEncoder<?>> encoder;
		private Factory<ByteToMessageDecoder> decoder;
		private Factory<NettyHandler> handler;

		@Override
		public NettyServerBuilderSSL port(int port) {
			this.port = port;
			return this;
		}

		@Override
		public NettyServerBuilderEncoder SSL(boolean ssl) {
			this.ssl = ssl;
			return this;
		}

		@Override
		public NettyServerBuilderDecoder encoder(Factory<MessageToByteEncoder<?>> encoder) {
			this.encoder = encoder;
			return this;
		}

		@Override
		public NettyServerBuilderHandler decoder(Factory<ByteToMessageDecoder> decoder) {
			this.decoder = decoder;
			return this;
		}

		@Override
		public NettyServerConfiguration handler(Factory<NettyHandler> handler) {
			this.handler = handler;
			return this;
		}

		@Override
		public NettyServerBuilderWrapper adapt() {
			return this;
		}

		@Override
		public NettyServer wrap() throws Exception {
			return new NettyServer(port, ssl, encoder, decoder, handler);
		}
	}

}
