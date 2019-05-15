package com.souchy.randd.situationtest.scripts;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.tealwaters.commons.Disposable;
import com.souchy.randd.jade.api.EventProxy;
import com.souchy.randd.situationtest.eventshandlers.EventHandler;
import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.events.Event;

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
public abstract class ScriptedStatus implements /*EventProxy,*/ Disposable {

	//private final EventBus bus = new EventBus();
	
	private final FightContext context;
	private final Character source;
	/** FIXME : Status.target can't only be Characters, it has to be able to be Cells too */
	private final Character target;
	
	// private int duration; -> dans redis ?
	//private OnTurnStartHandler onStartTurnRouter;
	//private OnTurnEndHandler onEndTurnRouter;
	
	//public final Stats stats;
	
	/*@Override
	public EventBus bus() {
		return bus;
	}*/
	
	List<EventHandler<?>> handlers = new ArrayList<>();

	public ScriptedStatus(FightContext context, Character source, Character target) {
		this.context = context;
		this.source = source;
		this.target = target;

		//onStartTurnRouter = context.<OnTurnStartHandler>route(this);
		//onEndTurnRouter = context.<OnTurnEndHandler>route(this);
		
		/*context.register((TurnStartEvent e) -> {
			
		});
		register((TurnStartEvent e) -> {
			
		});
		register(new OnTurnStartHandler() {

			@Override
			public void handle(TurnStartEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});*/
		
		registerHandlers();
	}
	
	@Override
	public void dispose() {
		unregister();
	}
	
	
	/*public void unroute() {
		context.unregister(onStartTurnRouter);
		context.unregister(onEndTurnRouter);
	}*/

	/**
	 * À moins que tu veule register ders handler auprès d'autres bus, à ce moment il faudrait avoir un Hash<Bus, EventHandler>
	 * @param h
	 */
	public <T extends Event> void register(EventProxy proxy, EventHandler<T> h) {
		//context.register(h);
		proxy.register(h);
		handlers.add(h);
	}
	
	public void unregister() {
		handlers.forEach(h -> context.unregister(h));
		handlers.clear();
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
