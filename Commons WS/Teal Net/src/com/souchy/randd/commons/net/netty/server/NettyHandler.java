package com.souchy.randd.commons.net.netty.server;

import com.souchy.randd.commons.net.Message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyHandler extends ChannelInboundHandlerAdapter {

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    /*	
		@SuppressWarnings("unchecked")
		Message<String> pack = (Message<String>) msg;
		-> // something like that so that when I do pack.onReceive, I know who sent the packet so I can message him back or do things with him
		//maybe it should just be included in the packet data, maybe it should be in the packet header, maybe it should come from the ctx, idk
		pack.from/source = ctx.channel().id(); 
		
		pack.onReceive();
		System.out.println("server < " + pack.id() + ", " + pack.serialize());
	
		Packet<String> p = new Pong(); 
		p.onSend();
        ctx.write(p);
        //ctx.write(msg);
       
      */	
    }

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

}
