package mockingbird;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class testConfig {

	public static class MockConfig extends JsonConfig {
		public String key;
		@Override
		public String toString() {
			return "MockConfig : { key: " + key + " }";
		}
	}
	
	public static void createConfig() {
		MockConfig config = JsonConfig.read(MockConfig.class);
		config = JsonConfig.readExternal(MockConfig.class, "./res/");
		Log.info(config.toString());
		
		config.key = "zxcv";
		config.save();
	}
	
}
