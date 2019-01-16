package com.souchy.randd.situationtest.models.entities;

import java.util.function.Consumer;

import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.events.cell.EnterCellEvent;
import com.souchy.randd.situationtest.eventshandlers.EventHandler;
import com.souchy.randd.situationtest.models.org.FightContext;

/**
 * 
 * 
 * TODO what if traps/glyphs were just Status's instead of entities
 * 
 * @author Souchy
 *
 */
public class Trap extends AEntity implements EventHandler<EnterCellEvent> {
	
	public final Character source;
	private Consumer<EnterCellEvent> onProcScript;
	
	public Trap(FightContext context, int id, Character source, Consumer<EnterCellEvent> onProcScript) {
		super(context, id);
		this.source = source;
		this.onProcScript = onProcScript;
		
		context.board.getCells(position).forEach(c -> c.bus().register(this));
		//bus().register(this);
	}

	@Override
	public void handle(EnterCellEvent event) {
		// to be scripted actually,
		// � moins que le script du spell passe une Function dans le constructeur de Trap et qu'on ex�cute �a ici :
		onProcScript.accept(event);
		// ne pas oublier qu'on a un ActionScriptEffect pour cr�er les traps/glyphs dont on pourra se servir pour
		//   builder le Trap object, donner la fonction � ex�cuter lors du proc,
		//    set les eventHandlers sur chaque cell.onEnterCell et sur source.onDeath, etc...
		
		// et c'est le spell qui lance la trap qui va donner la fonction au TrapAction
		
		// Donc overall �a fait : 
		// cast spell -> spell script -> post SpawnTrapAction to context -> context handler spawntrap script -> create trap object + setup handlers etc
		dispose();
	}
	
	
	public void dispose() {
		// enl�ve la trap de sur les cells
	}

}
