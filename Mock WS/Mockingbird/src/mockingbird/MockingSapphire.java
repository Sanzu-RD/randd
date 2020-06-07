package mockingbird;

import java.util.ArrayList;
import java.util.Arrays;

import com.souchy.randd.deathshadows.nodes.moonstone.black.BlackMoonstone;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.moonstone.white.WhiteMoonstone;

import gamemechanics.models.Fight;

public class MockingSapphire {
	
	public static void main(String[] args) throws Exception {
		Fight fight = new Fight();
		fight.id = 1;
		
		// init black moonstone and adds fight to its pool
		String[] blackargs = new String[] { "443", "async" };
		BlackMoonstone.main(blackargs);
		BlackMoonstone.moon.fights.add(fight);
		
		// init sapphire, auth with moonstone and join the fight
		String[] sapphireargs = { "eclipse", "localhost", "443", "souchy", "z", "1" };
		SapphireOwl.main(sapphireargs);
		

		// init sapphire, auth with moonstone and join the fight
		String[] sapphireargs2 = { "eclipse", "localhost", "443", "robyn", "z", "1" };
		SapphireOwl.main(sapphireargs2);
	}
	
	
	
}
