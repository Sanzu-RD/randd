package mockingbird;

import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.moonstone.white.Moonstone;

import console.ConsoleApp;

public class MockingMoonstoneWhite extends EbiShoalCore {
	

	public static void main(String[] args) throws Exception {
		var mooncore = new MockingMoonstoneWhite(args);
		// init le client
		var moon = new Moonstone("localhost", 443, mooncore);
		// commence par authentifier en demandant le sel, puis le user
		moon.channel.attr(Moonstone.authKey).set(new String[] { "souchy", "z", 1 + "" });
//		Moonstone.moon.write(new GetSalt());
		moon.write(new GetSalt("souchy"));
		// ensuite on passe par SendSaltHandler (TealNet) et SendUserHandler (MoonstoneWhite) puis envoie le message auth fight
		
		ConsoleApp.main();
	}

	public MockingMoonstoneWhite(String[] args) throws Exception {
		super(args);
	}

	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.commons.deathebi.msg", "com.souchy.randd.moonstone", "com.souchy.randd.moonstone.white", "com.souchy.randd.ebishoal.sapphire" };
	}
	
}
