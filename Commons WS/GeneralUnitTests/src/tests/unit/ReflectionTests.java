package tests.unit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.souchy.randd.commons.tealwaters.properties.*;

public class ReflectionTests {

	@Test
	@DisplayName("Is class loaded")
	public void testIsClassLoaded() {
		try {
			//System.out.println("name = ["+ClassToTest.class.getName()+"]");
			//String className = "tests.unit.ReflectionTests$ClassToTest";
			//String className = "tests.functional.DummyClass";
			String className = "com.souchy.randd.commons.tealwaters.properties.Property";  
			
			java.lang.reflect.Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
			m.setAccessible(true);
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			Object test1 = m.invoke(cl, className);
			//System.out.println(test1 != null);
			assertTrue(test1 == null, "class is loaded but shouldnt");
			
			new Property(null, null);
			Object test2 = m.invoke(cl, className);
			//System.out.println(test2 != null);
			assertTrue(test2 != null, "class isnt loaded but should");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
