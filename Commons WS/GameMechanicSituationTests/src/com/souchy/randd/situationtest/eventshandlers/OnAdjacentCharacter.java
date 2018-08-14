package com.souchy.randd.situationtest.eventshandlers;

import com.souchy.randd.situationtest.events.cell.EnterCellEvent;

@FunctionalInterface
public interface OnAdjacentCharacter extends EventHandler<EnterCellEvent> {

	/*@Override
	public void handle(EnterCellEvent event) {
	
	
		// Exemple de passif qui fait des dommages lorsqu'on entre sur une cellule adjacente à un joueur en particulier.
	
		Player myP;
		board.register(new OnAdjacentCharacter() -> (EnterCellEvent e) {
		
			if(e.source != myP && e.adjacent(myP) && e.source instanceof Character){
			
				IEntity target = e.source;
				post(new DamageEffectEvent1(myP, target, etc etc));
				
			}
		
		});


		
	}*/

}
