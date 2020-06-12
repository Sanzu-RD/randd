package com.souchy.randd.deathshadows.commons.smoothrivers;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.commons.tealwaters.commons.Namespace.*;

import io.netty.buffer.Unpooled;

public class SmoothRivers {
	
	private static String pearl_queue = "pearl";
	private Connection connection;
	private Channel channel;

	
	// pearl register/listen to queue "pearl"
	// pearl pushes ids on queue "idmaker"
	
	// nodes take an id from queue "idmaker"
	// nodes register/listen to queue "notetype#id"
	// nodes send their identification to pearl on queue "pearl"
	
	// pearl send messages to nodes on their queue name "nodetype#id"
	
	// node message structure ? : [packetid][nodetype#id][bodydata] so pearl knows who the message is from
	
	// maybe nodes can listen to global queues with extra routing keys : "node" > "notetype" > "notetype#id"
	
	
	// pearl should have an update loop that checks every node's last heartbeat timemillis in their NodeInfo object
	
	
	public SmoothRivers() {
		
	}
	
	public void connect() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		
		// take an id from the nodeid queue
		var idmsg = channel.basicGet("nodeids", true);
		var buf = Unpooled.copiedBuffer(idmsg.getBody());
		var my_node_id = buf.readInt();
		var my_queue_name = new String[] { "node", this.getClass().getSimpleName(), Integer.toString(my_node_id) };
		
		// declare the node's own specific queue
		channel.queueDeclare(String.join(".", my_queue_name), false, false, false, null);
		
		// consume from "node", "node.type" and "node.type.id"
		DeliverCallback decoder = new ByteToMessageRabbitDecoder();
		CancelCallback cancelConsumer = consumerTag -> { };
		channel.basicConsume(my_queue_name[0], true, decoder, cancelConsumer);
		channel.basicConsume(String.join(".", my_queue_name[0], my_queue_name[1]), true, decoder, cancelConsumer);
		channel.basicConsume(String.join(".", my_queue_name), true, decoder, cancelConsumer);
		
//		channel.queueDeclare(pearl_queue, false, false, false, null);
//		channel.basicConsume(pearl_queue, true, new ByteToMessageRabbitDecoder(), consumerTag -> { });
		
//		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//		    String message = new String(delivery.getBody(), "UTF-8");
//		    System.out.println(" [x] Received '" + message + "'");
//		};
//		channel.basicConsume(pearl_queue, true, deliverCallback, consumerTag -> { });
	}
	
	public static class ByteToMessageRabbitDecoder implements DeliverCallback {
		public Map<Integer, RabbitHandler> handlers;
		public Map<Integer, BBMessage> msgFactories;
		@Override
		public void handle(String consumerTag, Delivery delivery) throws IOException {
			var buf = Unpooled.copiedBuffer(delivery.getBody());
			int packetid = buf.readInt();
			var msg = msgFactories.get(packetid).create().deserialize(buf);
			handlers.get(packetid).handle(consumerTag, delivery, msg);
		}
	}
	
	public static class RabbitHandler {
		public void handle(String consumerTag, Delivery delivery, BBMessage msg) {
		    System.out.println(" [x] Received '" + msg + "'");
		}
	}
	
	
	public void send(String queue, BBMessage msg) {
		var buf = msg.serialize(Unpooled.buffer());
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		try {
			channel.basicPublish("", queue, null, bytes);
		} catch (IOException e) {
			Log.warning("", e);
		}
	}
	
	
}
