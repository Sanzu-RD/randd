package com.souchy.randd.deathshadows.commons.smoothrivers;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.Message;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class SmoothRivers {

	
	public SmoothRivers() {
		
	}
	

	
	public void connect() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
	}
	
	
	public void send(BBMessage m) {
		
	}
	
	
}
