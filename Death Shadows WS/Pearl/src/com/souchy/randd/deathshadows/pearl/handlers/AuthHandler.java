package com.souchy.randd.deathshadows.pearl.handlers;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.Acknowledge;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.Authenticate;
import com.souchy.randd.jade.meta.User;

import io.netty.channel.ChannelHandlerContext;

public class AuthHandler implements BBMessageHandler<Authenticate> {

	@Override
	public void handle(ChannelHandlerContext ctx, Authenticate message) {
		Log.info("AuthHandler handling message [" + message + "]");

		User user = Emerald.users().find(and(eq(User.name_username, message.user), eq(User.name_password, message.pass))).first(); 

		ctx.channel().write(new Acknowledge(message, user != null));
	}

	@Override
	public Class<Authenticate> getMessageClass() {
		return Authenticate.class;
	}

}
