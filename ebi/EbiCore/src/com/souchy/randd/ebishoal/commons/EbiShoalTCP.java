package com.souchy.randd.ebishoal.commons;

import com.souchy.randd.commons.net.netty.bytebuf.pipehandlers.BBMessageDecoder;
import com.souchy.randd.commons.net.netty.bytebuf.pipehandlers.BBMessageEncoder;
import com.souchy.randd.commons.net.netty.client.NettyClient;
import com.souchy.randd.commons.net.netty.server.NettyHandler;

public class EbiShoalTCP extends NettyClient {
	
	public EbiShoalTCP(String ip, int port, EbiShoalCore core) throws Exception {
		super(ip, port, false, false, new BBMessageEncoder(), new BBMessageDecoder(core.getMessages()), new NettyHandler(core.getHandlers()));
	}
	
}
