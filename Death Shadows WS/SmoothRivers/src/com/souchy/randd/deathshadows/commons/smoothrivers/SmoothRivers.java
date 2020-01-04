package com.souchy.randd.deathshadows.commons.smoothrivers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

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
