package tests.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

public class FilesTests {

	@Test
	@DisplayName("File path test")
	void testFilePath(TestReporter reporter) {
		File f = new File(".");
		assertNotNull(f, "null file");
		assertNotNull(reporter, "null reporter");
		
		String path = f.getPath();
		if(path.isEmpty()) path = "0";
		reporter.publishEntry("File path", path);
	}
	

	@Test
	@DisplayName("Classloader path test")
	void testClassLoaderPath(TestReporter reporter) {
		this.getClass().getClassLoader();
		File f = new File(".");
		assertNotNull(f, "null file");
		assertNotNull(reporter, "null reporter");
			
		String path = f.getPath();
		if(path.isEmpty()) path = "0";
		reporter.publishEntry("File path", path);
	}

}
