package mockingbird;

import java.util.Arrays;

import com.souchy.randd.deathshadows.nodes.moonstone.black.BlackMoonstone;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;

public class MockingSapphire {
	
	public static void main(String[] args) throws Exception {
		var a = Arrays.asList(args);
		a.add("async");
		args = (String[]) a.toArray();
		BlackMoonstone.main(args);
		SapphireOwl.main(args);
	}
	
	
	
}
