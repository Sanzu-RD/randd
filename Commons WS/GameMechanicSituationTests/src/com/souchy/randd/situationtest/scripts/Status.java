package com.souchy.randd.situationtest.scripts;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.commons.Disposable;
import com.souchy.randd.jade.api.EventProxy;
import com.souchy.randd.situationtest.eventshandlers.turn.OnTurnEndHandler;
import com.souchy.randd.situationtest.eventshandlers.turn.OnTurnStartHandler;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.properties.ElementBundle;
import com.souchy.randd.situationtest.properties.StatProperty;
import com.souchy.randd.situationtest.properties.Stats;


/**
 * Has to be implemented in ruby.
 * 
 * Then you can instiantiate one of the Ruby implementations (ex Cripple, Bleed, etc)
 * 
 * Just need to register a factory. Can use Discoverers to detect all the status files and create factories.
 * 
 * @author Souchy
 *
 */
public abstract class Status implements EventProxy, Disposable {

	private final EventBus bus = new EventBus();
	
	private final FightContext context;
	private OnTurnStartHandler onStartTurnRouter;
	private OnTurnEndHandler onEndTurnRouter;

	private final Character source;
	/**
	 * FIXME : Status.target can't only be Characters, it has to be able to be Cells too
	 */
	private final Character target;
	
	// private int duration; -> dans redis ?

	
	public final Stats stats;
	
	@Override
	public EventBus bus() {
		return bus;
	}

	public Status(FightContext context, Character source, Character target) {
		this.context = context;

		this.source = source;
		this.target = target;

		onStartTurnRouter = context.<OnTurnStartHandler>route(this);
		onEndTurnRouter = context.<OnTurnEndHandler>route(this);
		
		registerHandlers();
	}
	
	@Override
	public void dispose() {
		unregister();
	}
	
	public void unroute() {
		context.unregister(onStartTurnRouter);
		context.unregister(onEndTurnRouter);
	}
	
	public void unregister() {

	}

	/* ===========================  Status properties accessors to use in script  ===========================*/
	protected Character source() {
		return source;
	}
	protected Character target() {
		return target;
	}
	protected FightContext context() {
		return context;
	}
	
	
	/* ===========================  Status Properties to implement in script  ===========================*/
	public boolean canDebuff() {
		return false;
	}
	public boolean canProlong() {
		return false;
	}
	public boolean canStack() {
		return false;
	}
	public int maxStacks() {
		return 1;
	}
	public int duration() {
		return 1;
	}

	public abstract void registerHandlers();
	
	/*public void scriptImp() {
		register((TurnStartEvent event) -> onTurnStart(event));
		
		Object s1 = this.<OnTurnEndHandler>register((event) -> {
			
		});
		OnTurnEndHandler s2 = register((event) -> {
			
		});
		OnTurnEndHandler s3 = register((TurnEndEvent event) -> {
			
		});
		Object s4 = register((TurnEndEvent event) -> {
			
		});
	}*/

	
	
}
