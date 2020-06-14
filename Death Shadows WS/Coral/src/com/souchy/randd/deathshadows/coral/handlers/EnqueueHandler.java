package com.souchy.randd.deathshadows.coral.handlers;

import com.souchy.randd.commons.coral.in.Enqueue;
import com.souchy.randd.commons.coral.out.EnqueueAck;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.deathshadows.coral.main.Coral;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.jade.mm.Queuee;

import io.netty.channel.ChannelHandlerContext;

public class EnqueueHandler implements BBMessageHandler<Enqueue> {

	@Override
	public void handle(ChannelHandlerContext ctx, Enqueue message) {
		
		var client = ctx.channel().attr(User.attrkey).get();

		// check in all queues if the user is already queued
		Queuee q = null;

		if(q == null) q = Coral.getBlindQueuee(client);
		if(q == null) q = Coral.getDraftQueuee(client);

		if(q != null) {
			//return "already in a queue";
			ctx.writeAndFlush(new EnqueueAck(q.queue, true, true));
			return;
		} else {
			q = new Queuee();
			q.userid = client._id;
			q.mmr = client.mmr;
			q.timeQueued = System.currentTimeMillis();
			q.queue = message.queue;

			switch(message.queue) {
				case blind:
					Emerald.queue_simple_blind().insertOne(q);
					break;
				case draft:
					Emerald.queue_simple_draft().insertOne(q);
					break;
				case mock:
					//Emerald.queue_simple_mock().insertOne(q);
					break;
			}
			
			//return "inserted in blind queue";
			ctx.writeAndFlush(new EnqueueAck(q.queue, false, true));
		}
	}

	@Override
	public Class<Enqueue> getMessageClass() {
		return Enqueue.class;
	}
	
}
