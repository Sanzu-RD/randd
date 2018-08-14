package tests.scripts;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Scanner;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class JRubyTest {
	
	
	public void hey() {
		System.out.println("hey");
	}
	
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("JRubyTest.");
		
		//Ruby runtime = Ruby.getDefaultInstance();
		//runtime.executeScript(scripting, filename);
		
		String rubyScript = "def write(msg) \r\n" + "      puts msg \r\n" + "end \r\n" + "\r\n" + " def anotherFunction() \r\n" + "     # do something \r\n"
				+ "end \r\n";

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("jruby");
		

		while(true) {
			System.out.println("Waiting...");
			//Scanner in = new Scanner(System.in);
		    Scanner in = new Scanner(System.in);
		    in.nextLine();
			//String s = in.next();
			//System.in.read();
			try {

				System.out.println("JRubyTest.eval");
				engine.eval(new FileReader("res/rubytest.rb"));
				//engine.eval(rubyScript);

				System.out.println("JRubyTest.invoke");
				Invocable inv = (Invocable) engine;
				inv.invokeFunction("write", new Object[] { "hello" });
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}

		// prints out:
		// hello
		
	}
	
	

}
