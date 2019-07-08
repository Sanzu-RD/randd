package com.souchy.randd.commons.net.netty.bytebuf;

import com.souchy.randd.commons.net.MessageHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public interface BBMessageHandler<T extends BBMessage> extends MessageHandler<ChannelHandlerContext, T> { // Responsibility<T> {

}
