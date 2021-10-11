package com.souchy.randd.commons.net.netty.bytebuf.pipehandlers;

import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageFactories;
import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * TODO je veux la même chose mais pour mes MessageBrokers sur SmoothRivers (avec rabbit mq et guava eventbus)
 * @author Souchy
 *
 */
//@Sharable // Cannot be sharable
public class BBMessageDecoder extends ByteToMessageDecoder  {

	private final BBMessageFactories msgFactories;

	public BBMessageDecoder(BBMessageFactories factory) {
		this.msgFactories = factory;
		//Log.info("new decoder");
	}

	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf in, List<Object> out) throws Exception {
//		Log.info("decoder rcv");
		int packetid = -1;
		String s = null;
		try {
			ByteBuf inData = in.readBytes(in.readableBytes());
			
			packetid = inData.readInt();
			
			if (msgFactories.has(packetid)) { // res.canHandle(packetid)){
				var a = msgFactories.get(packetid).create();
				BBMessage msg = a.deserialize(inData);
				inData.release();
				Log.format("< msg %s : %s", packetid, msg);
				
				out.add(msg);
				
				// out.add(res.get(packetid).handle(data));
			} else {
				Log.error("Packet id [" + packetid + "] missing from msg factory");
			}
//			var d = msgFactories.get(packetid).create();
//			d.deserialize(in);

			//if(in.refCnt() > 0) in.release();
			
			//data.release();
		} catch (Exception e) {
			Log.error("packetid = " + packetid + ", data = " + s, e);
			throw new Exception("packetid = " + packetid + ", data = " + s, e);
		}
	}

}
