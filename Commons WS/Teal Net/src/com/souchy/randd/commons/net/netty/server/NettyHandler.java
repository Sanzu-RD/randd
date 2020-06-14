package com.souchy.randd.commons.net.netty.server;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageHandlers;
import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class NettyHandler extends ChannelInboundHandlerAdapter {

	private final BBMessageHandlers msgHandlers;
	
	public NettyHandler(BBMessageHandlers msgHandlers) {
		this.msgHandlers = msgHandlers;
	}
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//super.channelRead(ctx, msg);
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
		
//		Log.info("NettyHandler : " + msg.toString());
		var bb = (BBMessage) msg;
//		if (msgHandlers.canHandle(bb)) 
		msgHandlers.handle(ctx, bb);
		
		// this would pass to the next handler, but the message is already handled here so no point firing an event when there's no other handler to take it 
//		super.channelRead(ctx, msg);
    }
	

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		Log.error("NettyHandler", cause); // + cause.getLocalizedMessage() + stackTrace);
	//	cause.printStackTrace();
		ctx.close();
	}
	
	
}
