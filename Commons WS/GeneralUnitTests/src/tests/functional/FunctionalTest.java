package tests.functional;

import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;

public class FunctionalTest {

	
	//@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		
		
		//String packagename = "com.souchy.randd.commons.tealwaters.commons";
		String packagename = "com.souchy.randd.moonstone";
		
		//if(true) return;

		DefaultClassDiscoverer dis = new ClassDiscoverer.DefaultClassDiscoverer();
		
		//File f = new File("F:\\Users\\Souchy\\Desktop\\Robyn\\Git\\r and d\\annotationprocessor.jar\\com\\souchy\\randd\\annotationprocessor\\");
		
		//System.out.println("test : ["+f.getAbsolutePath()+"]");
		
		//File[] files = f.listFiles();
		//if(files != null && files.length > 0)
		//for(File f1 : files)
		//	System.out.println("test : ["+f1.getAbsolutePath()+"]");
		
		List<Class<Object>> handlers = dis.explore(packagename); //, c -> BBMessageHandler.class.isAssignableFrom(c));
		handlers.forEach(c -> {
			// boolean pass = filter.test(c);
			System.out.println("handler : [" + c.getName() + "]");// is handler [" + pass + "]");
			// if(pass) {
			if(BBMessageHandler.class.isAssignableFrom(c))
			try {
				BBMessageHandler<?> handler = (BBMessageHandler<?>) c.getDeclaredConstructor().newInstance();
				// FightActionHandler fa = (FightActionHandler) handler;

				Class<?> msgClass = handler.getMessageClass();
				int id = handler.getID();

				System.out.println(" |- handles message ID [" + id + "] [" + msgClass.getName() + "]");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// }
		});
		
		System.out.println("----");
		//packagename = "com.souchy.randd.moonstone";
		List<Class<Object>> messages = dis.explore(packagename); //, c -> BBMessage.class.isAssignableFrom(c));
		messages.forEach(c -> {
			// boolean pass = filter.test(c);
			// if(pass) {
			if(BBMessage.class.isAssignableFrom(c))
			try {
				BBMessage message = (BBMessage) c.getDeclaredConstructor().newInstance();
				// FightActionHandler fa = (FightActionHandler) handler;

				//Class<?> msgClass = handler.getMessageClass();
				int id = message.getID();

				System.out.println("message : [" + id + "] [" + c.getName() + "]");// is handler [" + pass + "]");
				//System.out.println(" |- handles message ID [" + id + "] [" + msgClass.getName() + "]");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// }
		});
	}

	
}
