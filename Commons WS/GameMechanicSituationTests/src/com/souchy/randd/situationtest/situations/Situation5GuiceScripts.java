package com.souchy.randd.situationtest.situations;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.properties.ElementValue;
import com.souchy.randd.situationtest.properties.types.Damages;
import com.souchy.randd.situationtest.properties.types.Elements;
import com.souchy.randd.situationtest.scripts.actionhandlers.Damage1ActionHandler;
import com.souchy.randd.situationtest.scripts.actions.Damage1Action;
import com.souchy.randd.situationtest.situations.Situation4Events.InputLoop;
import com.souchy.randd.situationtest.situations.Situation4Events.ScriptedEngine;

public class Situation5GuiceScripts {


	private static Damage1ActionHandler rubyDamageEffect1 = null;
	
	public static void main(String[] args) {
		
		//Ruby r;
		//r.defineClass(name, superClass, allocator)
		
		InputLoop.run(() -> {
			Object o =  ScriptedEngine.eval("data/action_effects/Damage1Script.rb");
			System.out.println("o = " + o);
			rubyDamageEffect1 = (Damage1ActionHandler) o;
			System.out.println("rubyDamageEffect1 = " + rubyDamageEffect1 + ", class : " + rubyDamageEffect1.getClass());
		});
		
		ActionScriptModule module = new ActionScriptModule(rubyDamageEffect1.getClass());
		//module.configure();
		//System.out.println("finished configuration");
		
	    /*
	     * Guice.createInjector() takes your Modules, and returns a new Injector
	     * instance. Most applications will call this method exactly once, in their
	     * main() method.
	     */
	    Injector injector = Guice.createInjector(module);

	    /*
	     * Now that we've got the injector, we can build objects.
	     */
	    Damage1ActionHandler damage1script = injector.getInstance(Damage1ActionHandler.class);

		System.out.println("damage1script = " + damage1script);
		
	    FightContext context = new FightContext();
	    IEntity source = new Character(context, 1, null);
	    IEntity	target = new Character(context, 2, null);
	    
	    ElementValue scl = new ElementValue(Elements.Dark, 10);
	    ElementValue flat = new ElementValue(Elements.Dark, 5);
	    
	    Damage1Action action = new Damage1Action(source, target, Damages.Hit, Elements.Dark, scl, flat);
	    damage1script.handle(action);
	}
	
	
	
	public static class ActionScriptModule extends AbstractModule {
		Class<? extends Damage1ActionHandler> c;
		public ActionScriptModule(Class<? extends Damage1ActionHandler> c) {
			this.c = c;
		}
		@Override
		protected void configure() {
			bind(Damage1ActionHandler.class).to(c);
			System.out.println("finished binding");
		}
		
	}
	
	
	
}
