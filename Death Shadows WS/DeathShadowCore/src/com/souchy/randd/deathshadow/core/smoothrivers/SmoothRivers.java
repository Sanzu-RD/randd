package com.souchy.randd.deathshadow.core.smoothrivers;

import com.souchy.randd.commons.deathebi.Core;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
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
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageFactories;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageHandlers;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.commons.tealwaters.commons.Namespace.*;

import io.netty.buffer.Unpooled;

public class SmoothRivers {
	
	private static SmoothRivers rivers;
	
	private static String pearl_queue = "pearl";
	public Connection connection;
	public Channel channel;
	
	// pearl register/listen to queue "pearl"
	// pearl pushes ids on queue "idmaker"
	
	// nodes take an id from queue "idmaker"
	// nodes register/listen to queue "notetype#id"
	// nodes send their identification to pearl on queue "pearl"
	
	// pearl send messages to nodes on their queue name "nodetype#id"
	
	// node message structure ? : [packetid][nodetype#id][bodydata] so pearl knows
	// who the message is from
	
	// maybe nodes can listen to global queues with extra routing keys : "node" >
	// "notetype" > "notetype#id"
	
	// pearl should have an update loop that checks every node's last heartbeat
	// timemillis in their NodeInfo object
	
	private Core core;
	public int nodeID;
	
	public Map<Integer, RabbitHandler> handlers;
	// public Map<Integer, BBMessage> msgFactories;
	
	public SmoothRivers(DeathShadowCore core) throws IOException, TimeoutException {
		this.core = core;
		rivers = this;
	}
	
	public void connect(int id) throws IOException, TimeoutException {
		this.nodeID = id;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.2.11");
		factory.setPort(5672);
		factory.setUsername("souchy");
		factory.setPassword("z");
		
		connection = factory.newConnection();
		channel = connection.createChannel();
		
		// take an id from the nodeid queue
		// var idmsg = channel.basicGet("nodeids", true);
		// var buf = Unpooled.copiedBuffer(idmsg.getBody());
		// var my_node_id = buf.readInt();
		// var my_node_id = (String) idmsg.getProps().getMessageId(); //
		// .getHeaders().get("id"); // Integer.parseInt((String)
		// idmsg.getProps().getHeaders().get("id"));
		var my_queue_name = new String[] { "nodes", core.getClass().getSimpleName(), Integer.toString(nodeID) }; // Integer.toString(my_node_id) };
		
		// declare the node's own specific queue
		channel.queueDeclare(String.join(".", my_queue_name), false, false, false, null);
		
		// consume from "node", "node.type" and "node.type.id"
		DeliverCallback decoder = new ByteToMessageRabbitDecoder();
		CancelCallback cancelConsumer = consumerTag -> {
		};
		channel.basicConsume(my_queue_name[0], true, decoder, cancelConsumer);
		channel.basicConsume(String.join(".", my_queue_name[0], my_queue_name[1]), true, decoder, cancelConsumer);
		channel.basicConsume(String.join(".", my_queue_name), true, decoder, cancelConsumer);
		
		// channel.queueDeclare(pearl_queue, false, false, false, null);
		// channel.basicConsume(pearl_queue, true, new ByteToMessageRabbitDecoder(),
		// consumerTag -> { });
		
		// DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		// String message = new String(delivery.getBody(), "UTF-8");
		// System.out.println(" [x] Received '" + message + "'");
		// };
		// channel.basicConsume(pearl_queue, true, deliverCallback, consumerTag -> { });
	}
	
	public class ByteToMessageRabbitDecoder implements DeliverCallback {
		@Override
		public void handle(String consumerTag, Delivery delivery) throws IOException {
			var buf = Unpooled.copiedBuffer(delivery.getBody());
			int packetid = buf.readInt();
			
//			Log.info("SmoothRivers rcv " + packetid + " / " + core.getMessages());
			
			var msg = core.getMessages().get(packetid).create().deserialize(buf);
//			handlers.get(packetid).handle(consumerTag, delivery, msg);
			core.getHandlers().handle(null, msg);
		}
	}
	
	public static class RabbitHandler {
		public void handle(String consumerTag, Delivery delivery, BBMessage msg) {
			System.out.println(" [x] Received '" + msg + "'");
		}
	}
	
	public static void sendPearl(BBMessage msg) {
		send("nodes.Pearl", msg);
	}
	
	public static void send(String queue, BBMessage msg) {
		// serialize to a bytebuf
		var buf = Unpooled.buffer();
		buf.writeInt(msg.getID());
		msg.serialize(buf);
		
		// get the bytes
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		try {
			rivers.channel.basicPublish("", queue, null, bytes);
		} catch (IOException e) {
			Log.warning("", e);
		}
	}
	
	
}
