package mockingbird;

import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.commons.deathebi.msg.GetUser;
import com.souchy.randd.deathshadows.nodes.moonstone.black.BlackMoonstone;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.moonstone.white.WhiteMoonstone;

public class MockingMoonstoneWhite extends EbiShoalCore {
	

	public static void main(String[] args) throws Exception {
		var mooncore = new MockingMoonstoneWhite(args);
		// init le client
		var moon = new WhiteMoonstone("localhost", 443, mooncore);
		// commence par authentifier en demandant le sel, puis le user
		moon.auth("souchy", "z", 1);
		// ensuite on passe par SendSaltHandler (TealNet) et SendUserHandler (MoonstoneWhite) puis envoie le message auth fight
	}

	public MockingMoonstoneWhite(String[] args) throws Exception {
		super(args);
	}

	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.commons.deathebi.msg", "com.souchy.randd.moonstone", "com.souchy.randd.moonstone.white", "com.souchy.randd.ebishoal.sapphire" };
	}
	
}
