package com.souchy.randd.deathshadow.core.handlers;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.*;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.commons.deathebi.msg.GetUser;
import com.souchy.randd.commons.deathebi.msg.SendSalt;
import com.souchy.randd.commons.deathebi.msg.SendUser;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.User;

//import com.souchy.randd.commons.nettyauth.Authentication;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class AuthenticationFilter extends ChannelInboundHandlerAdapter { //implements NettyMessageFilter { 
	
	public final Map<User, Channel> userChannels = new HashMap<>();

	/** 
	 * Filter messages so only channels with a User attribute can go through 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		Log.info("AuthenticationFilter read");
		// si cest un message getsalt
		if(msg instanceof GetSalt) 
			getSalt(ctx, (GetSalt) msg);
		else
		// si cest un msg getuser
		if(msg instanceof GetUser) 
			getUser(ctx, (GetUser) msg);
		else 
		// sinon c'est qu'il doit déjà avoir un user attribute
		if(ctx.channel().hasAttr(User.attrkey)) 
			super.channelRead(ctx, msg); // if ctx has user attr, pass the message to the next handlers
	}
	
	
	/** remove disconnecting users */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//		Log.info("AuthenticationFilter inactive");
		if(ctx.channel().hasAttr(User.attrkey))
			userChannels.remove(ctx.channel().attr(User.attrkey).get());
		super.channelInactive(ctx);
	}
	
	
	/** get and send a salt */
	public void getSalt(ChannelHandlerContext ctx, GetSalt msg) {
		Log.info("AuthenticationFilter getSalt");
		// trouve le salt associé au username et renvoi le
		var user = Emerald.users().find(eq(User.name_username, msg.username)).first();
		if(user != null) 
			ctx.writeAndFlush(new SendSalt(user.salt));
		else 
			ctx.writeAndFlush(new SendSalt(null)); //SendSaltNull()); // username doesnt exist
	}
	
	/** get and send a user */
	public void getUser(ChannelHandlerContext ctx, GetUser msg) {
		Log.info("AuthenticationFilter getUser");
		// trouve un user avec le username + hashedpassword et renvoi le 
		// + set l'attribute dans le channel 
		// + ajoute le channel aux users connectés
		var user = Emerald.users().find(and(eq(User.name_username, msg.username), eq(User.name_password, msg.hashedPassword))).first();
		if(user != null) {
			ctx.channel().attr(User.attrkey).set(user);
			userChannels.put(user, ctx.channel());
			ctx.writeAndFlush(new SendUser(user));
		}
		else 
			ctx.writeAndFlush(new SendUser(null)); //SendUserNull()); // username + password miscombination
	}
	
	
}
