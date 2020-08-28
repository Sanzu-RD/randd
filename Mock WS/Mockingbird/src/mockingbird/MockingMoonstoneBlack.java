package mockingbird;

import com.souchy.randd.deathshadows.blackmoonstone.main.BlackMoonstone;

public class MockingMoonstoneBlack {


	public static void main(String[] args) throws Exception {
		args = new String[] { "443", "async", "mock" };

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
