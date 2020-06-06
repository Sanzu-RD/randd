package mockingbird;

import org.bson.types.ObjectId;

import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.deathshadows.nodes.moonstone.black.BlackMoonstone;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.jade.meta.UserLevel;

public class MockingMoonstoneBlack {


	public static void main(String[] args) throws Exception {
		args = new String[] { "443", "async" };

//		Emerald.init("192.168.2.15", 27017, "souchy", "hiddenpiranha12;");
//		var user = new User();
//		user.username = "souchy";
//		user.level = UserLevel.Creator;
//		user.password = "z";
//		user.salt = "";
//		user._id = new ObjectId();
//		Emerald.users().insertOne(user);
//		if(true) return;
		
		BlackMoonstone.main(args);
	}
  
}
