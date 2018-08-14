package com.souchy.randd.situationtest.situations;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.jruby.Ruby;
import org.jruby.RubyRuntimeAdapter;
import org.jruby.embed.ScriptingContainer;
import org.jruby.javasupport.JavaEmbedUtils;
import org.jruby.util.JRubyFile;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.situationtest.events.CastSpellEvent;
import com.souchy.randd.situationtest.scripts.ScriptedSkill;
import com.souchy.randd.commons.tealwaters.commons.*;

public class Situation3SpellScript {
	
	
	public static void main(String[] args) {
		
		//ScriptingContainer container = new ScriptingContainer();
		//container.

        Ruby runtime = JavaEmbedUtils.initialize(new ArrayList<>());
        RubyRuntimeAdapter evaler = JavaEmbedUtils.newRuntimeAdapter();
		
		evaler.eval(runtime, "");
		
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("jruby");
		
		Object z  = new Namespace.RedisNamespace("sdfg", "p");
		System.out.println("z= " + z);

		//String spellFile = "res/spells/fireball.rb";
		String spellFile = "res/spells/PoisonTrap.rb";
		
		while(true) {
			try {
				System.out.println("Waiting...");
			    Scanner in = new Scanner(System.in);
			    in.nextLine();
			    //in.close();
			    
				System.out.println("Situation3SpellScript.eval");
				Object o = engine.eval(new FileReader(spellFile));
				//System.out.println("java my object : " + o);
				ScriptedSkill a = (ScriptedSkill) o;
				//System.out.println("java spell name : " + a.name);
				//System.out.println("java my object2 : " + a);
				//a.test();
				//a.hiRuby();

				System.out.println("\n After analysis : ");
				System.out.println("sdfg:" + a.effects);
				
				a.getOnCastHandler().handle(new CastSpellEvent(null, null, null, a));


				/*
				System.out.println("Situation3SpellScript.invoke");
				Invocable inv = (Invocable) engine;
				ScriptedSpell spell = inv.getInterface(ScriptedSpell.class);
				System.out.println("java my spell : " + spell);
				spell.test();
				spell.hiRuby();
				//CastSpellEvent castEvent = new CastSpellEvent(null, null, null);
				//spell.onCast.handle(castEvent);
				*/
				System.out.println("");
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
}
