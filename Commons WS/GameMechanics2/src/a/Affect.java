package a;

public class Affect {
	
	/*
	 * effects that can be procced : easy
	 * stats : completely different application, OR ARE THEY
	 * 
	 * 
	 * pas besoin de tout ça en bas avec nos status v
	 */
	
	// thing that procs 
	// either instantly (on cast)
	// or delayed (like on end of turn)
	// or a mix of everything
	
	// basically, copy the ScheduledExecutor model
	
	
	// so a thing can proc on a pattern of turns like these :
	// prosc only on cast : 
	//		1, 0, 0, 0, 0  
	// proc every other turn :
	// 		1, 0, 1, 0, 1, 0, 1, 0, 1
	// procs once every turn and twice every other turn :
	// 		1, 2, 1, 2, 1, 2, 1, 2, 1
	// procs for 2 turns then has 2 turns downtime
	// 		1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 
	// procs only after 3 turns :
	// 		0, 0, 0, 1
	// procs only after 3 turns, then every turn :
	// 		0, 0, 0, 1, 1, 1, 1, 1
	// procs only after 3 turns, then every 2 turn :
	// 		0, 0, 0, 1, 0, 1, 0, 1

	// so you have :
	// int initialDelay = 0;		 // minimum number of triggers before applying effect
	// double frequency = 0.0;   // can be 4 for 4 times per trigger or 0.25 for once every 4 trigger  (usually the trigger would be an onTurnStart/End)
	// int duration = 0;		 // maximum number of triggers before stopping
	// Trigger trigger; // EventHandler
	//
	// double time;
	// double counter;
	// 		
	//
	/* register()
	* 		bus.onTrigger -> {
	* 			time++; // total number of triggers over the lifetime of the effect
	* 			counter += frequency; // current counter to charge up before proccing
	* 	
	* 			// if done
	* 			if(time - initialDelay >= duration)
	*				dispose(); 
	*				return;
	*			// if can start
	* 			if(time >= initialDelay)
	* 				while(counter >= 1)
	* 					counter--;
	* 					apply();
	* 		}
	* 
	* 
	*/
	
}
