package tests.functional;

import java.io.IOException;
import java.nio.charset.Charset;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import tests.functional.SmoothRiversTest.Action;
import tests.functional.SmoothRiversTest.HelloMsg;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class SmoothRiversTest {
	private final static String QUEUE_NAME = "hello";
	private static int receiverID = 3;
	
	@ID(id = 99)
	public static class HelloMsg implements BBMessage {
		public String content;
		private HelloMsg() { }
		public HelloMsg(String s) {
			content = s;
		}
		@Override
		public ByteBuf serialize(ByteBuf out) {
			out.writeInt(content.length());
			out.writeCharSequence(content, Charset.defaultCharset());
			return out;
		}
		@Override
		public BBMessage deserialize(ByteBuf out) {
			System.out.println("HelloMsg.deserialize " + out.capacity() + ", " + out.readableBytes());
			int length = out.readInt();
			content = out.readCharSequence(length, Charset.defaultCharset()).toString();
			System.out.println("HelloMsg.deserialize " + out.capacity() + ", " + out.readableBytes() + ", " + length);
			return this;
		}
		@Override
		public Deserializer<ByteBuf, BBMessage> create() {
			return new HelloMsg();
		}
		@Override
		public int getBufferCapacity() {
			if(content != null && content.length() != 0) {
				return 4 + content.length(); // 4 bytes pour le int contenant la longueur de la string, puis la longueur de la string correspond au nombre de bytes utilisés
			}
			return 10;
		}
	}
	
	public static class SmoothRivers1 {
		
		public static void main(String[] args) throws Exception {
			
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			
			HelloMsg msg = new HelloMsg("z 6");
			int cap = msg.getBufferCapacity();
			ByteBuf out = Unpooled.buffer(cap);
			msg.serialize(out);

			String message = msg.content; //"Hello World!";
			
			channel.basicPublish("", QUEUE_NAME, null, out.array()); // message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
			
			
	//		channel.queueDelete(QUEUE_NAME, false, false);
			channel.close();
			connection.close();
			System.out.println(" [x] Exit");
		}
	}
	
	@FunctionalInterface
	public static interface Action {
		public void act();
	}

	
	public static class SmoothRivers2 {
		public static void main(String[] argv) throws Exception {
			int id = receiverID++;
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			System.out.println(" ["+id+"] Waiting for messages. To exit press CTRL+C");
			
			Action a = () -> {
				try {
				//	channel.queueDelete(QUEUE_NAME, false, false);
					channel.close();
					connection.close();
					System.out.println(" ["+id+"] Exit");
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
					
					ByteBuf in = Unpooled.wrappedBuffer(body);

					Deserializer<ByteBuf, BBMessage> deserializer = new HelloMsg("x").create();
					BBMessage msg = deserializer.deserialize(in);
					HelloMsg h = (HelloMsg) msg;
					
					String message = h.content; // new String(body, "UTF-8");
					System.out.println(" ["+id+"] Received '" + message + "'");
				//	a.act();
				}
			};
			channel.basicConsume(QUEUE_NAME, true, consumer);
		}
	}
	
	
	
}
