package mockingbird;

import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.User;

public class testEmerald {

	public static void createUser() {
		User u = new User();
		u.username = "robyn";
		u.password = "z";
		u.pseudo = "Souchy";
		u.email = "music_inme@hotmail.fr";
		Emerald.users().insertOne(u);
		System.out.println("created");
	}
	
}
