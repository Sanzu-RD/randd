package com.souchy.randd.situationtest.models.entities;

import java.util.function.Consumer;

import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.events.cell.EnterCellEvent;
import com.souchy.randd.situationtest.eventshandlers.EventHandler;
import com.souchy.randd.situationtest.models.org.FightContext;

public class Glyph extends IEntity { //implements EventHandler<EnterCellEvent> {
	
	public final Character source;
	private Consumer<EnterCellEvent> onProcScript;
	
	public Glyph(FightContext context, int id, Character source) { //, Consumer<EnterCellEvent> onProcScript) {
		super(context, id);
		this.source = source;
		this.onProcScript = onProcScript;
		
		// need more triggers than just 1 event type, need to be able to subscribe f.ex. to entercellevent + endturnevent + exitcellevent etc
		context.board.getCells(position).forEach(c -> c.bus().register(this));
		//bus().register(this);
	}

	//@Override
	public void handle(EnterCellEvent event) {
		// to be scripted actually,
		// à moins que le script du spell passe une Function dans le constructeur de Trap et qu'on exécute ça ici :
		onProcScript.accept(event);
		// ne pas oublier qu'on a un ActionScriptEffect pour créer les traps/glyphs dont on pourra se servir pour
		//   builder le Trap object, donner la fonction à exécuter lors du proc,
		//    set les eventHandlers sur chaque cell.onEnterCell et sur source.onDeath, etc...
		
		// et c'est le spell qui lance la trap qui va donner la fonction au TrapAction
		
		// Donc overall ça fait : 
		// cast spell -> spell script -> post SpawnTrapAction to context -> context handler spawntrap script -> create trap object + setup handlers etc
		dispose();
	}
	
	
	public void dispose() {
		// enlève la trap de sur les cells
	}
	
}
