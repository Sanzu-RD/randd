package tests.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.annotationprocessor.ChildMustAnnotate;
import com.souchy.randd.annotationprocessor.ID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;


public class AnnotationTests {
	
	@Test
	@DisplayName("test message IDs")
	public void testMessageIDs() {
		Ping ping = new Ping();
		Pong pong = new Pong();
		assertEquals(1, (int)ping.getID());
		assertEquals(2, (int)pong.getID());
	}
	
	@Test
	@DisplayName("test message handler getIDs")
	public void testMessageHandlerIDs() {
		PingHandler ping = new PingHandler();
		PongHandler pong = new PongHandler();
		assertEquals(1, (int)ping.getID());
		assertEquals(2, (int)pong.getID());
	}

	@Test
	@DisplayName("Test MessageHandler getClazz")
	void testMessageHandlerClass3(TestReporter reporter) {
		PingHandler ping = new PingHandler();
		assertEquals(Ping.class, ping.getMessageClass());
	}

	@Test
	@DisplayName("Test MessageHandler ids")
	void testMessageHandlerClass4(TestReporter reporter) {
		PingHandler ping = new PingHandler();
		PongHandler pong = new PongHandler();
		assertEquals(1, (int)ping.getID());
		assertEquals(2, (int)pong.getID());
	}

	@Test
	@DisplayName("Test HandlerManager canHandle without handler")
	void testHandlerManager(TestReporter reporter) {
		HandlerManager.handlers.clear();
		assertFalse(HandlerManager.canHandle(new Ping()));
		assertFalse(HandlerManager.canHandle(new Pong()));
	}
	
	@Test
	@DisplayName("Test HandlerManager canHandle with handler")
	void testHandlerManager2(TestReporter reporter) {
		HandlerManager.handlers.clear();
		HandlerManager.add(new PingHandler());
		HandlerManager.add(new PongHandler());
		assertTrue(HandlerManager.canHandle(new Ping()));
		assertTrue(HandlerManager.canHandle(new Pong()));
	}
	
	
	@Test
	@DisplayName("Test HandlerManager handle data")
	void testHandlerManager3(TestReporter reporter) {
		HandlerManager.handlers.clear();
		HandlerManager.add(new PingHandler());
		HandlerManager.add(new PongHandler());
		Ping ping = new Ping();
		Pong pong = new Pong();
		assertTrue(ping.data.isEmpty());
		assertTrue(pong.data.isEmpty());
		HandlerManager.handle(ping);
		HandlerManager.handle(pong);
		assertTrue(ping.data.equals("hi from ping handler"));
		assertTrue(pong.data.equals("hi from pong handler"));
	}
	
	


	public static interface Identifiable<T> {
		public T getID();
	}
	public static interface IdentifiableInt extends Identifiable<Integer> {
		public Integer getID();
		default int id() {
			return getID();
		}
	}
	
	@ChildMustAnnotate(ID.class)
	public static interface AnnotatedIdentifiable extends Identifiable<Integer> {
		@Override
		default Integer getID() {
			return getID(this.getClass());
		}
		public static int getID(Class<?> c) {
			return c.getAnnotation(ID.class).id();
		}
	}
	/*public static interface UnAnnotatedIdentifiable extends Identifiable {
		default int getID() {
			return getID(this.getClass());
		}
		public static int getID(Class<?> c) {
			return c.getAnnotation(ID.class).id();
		}
	}*/
	
	//@ChildMustAnnotate(ID.class)
	public static interface Message<T> extends AnnotatedIdentifiable { //extends ID {
		public T serialize(T out);
		public Message<T> deserialize(T in);
	}
	public static interface GayMessage extends Message<String> { 
		@Override public GayMessage deserialize(String in); 
	}
	@ID(id = 1)
	public static class Ping implements GayMessage {
		String data = "";
		@Override public String serialize(String out) { return null; }
		@Override public GayMessage deserialize(String in) { return null; } 
	}
	//@ID(id = 2)
	public static class Pong implements GayMessage {
		String data = "";
		@Override public String serialize(String out) { return null; }
		@Override public GayMessage deserialize(String in) { return null; } 
	}
	
	public static class notAnnotated { }
	
	public static interface MessageHandler<T extends Message<?>> extends Identifiable<Integer> {
		public void handle(T message);
		@Override default Integer getID() {
			return AnnotatedIdentifiable.getID(getMessageClass());
		}
		public Class<T> getMessageClass();
	}

	public static interface GayMessageHandler<T extends GayMessage> extends MessageHandler<T> {
		
	}
	
	public static class PingHandler implements GayMessageHandler<Ping> { //MessageHandler<Ping> {
		@Override public void handle(Ping message) {
			message.data = "hi from ping handler";
		}
		@Override
		public Class<Ping> getMessageClass() { return Ping.class; }
	}
	public static class PongHandler implements GayMessageHandler<Pong> {
		@Override public void handle(Pong message) {
			message.data = "hi from pong handler";
		}
		@Override
		public Class<Pong> getMessageClass() { return Pong.class; }
	}
	
	public static class HandlerManager {
		private static Map<Integer, GayMessageHandler<GayMessage>> handlers = new HashMap<>();
		public static boolean canHandle(GayMessage msg) {
			return handlers.containsKey(msg.getID());
		}
		public static void handle(GayMessage msg) {
			handlers.get(msg.getID()).handle(msg);
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static void add(GayMessageHandler handler) {
			handlers.put(handler.getID(),  (GayMessageHandler<GayMessage>) handler);
		}
	}
	
	public static class MessageHandlerDiscoverer {
		public static void explore(String packagepath) {
			
		}
	}
	
	
	
	
}
