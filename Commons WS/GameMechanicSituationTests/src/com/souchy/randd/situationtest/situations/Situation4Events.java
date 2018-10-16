package com.souchy.randd.situationtest.situations;

import java.io.FileReader;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.souchy.randd.situationtest.events.CastSpellEvent;
import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.models.stage.Board;
import com.souchy.randd.situationtest.models.stage.Cell;
import com.souchy.randd.situationtest.scripts.ScriptedSkill;

public class Situation4Events {
	
	public static class ScriptedEngine {
		private static ScriptEngineManager manager = new ScriptEngineManager();
		private static ScriptEngine engine = manager.getEngineByName("jruby");
		public static <T> T eval(String path) {
			try {
				Object o = engine.eval(new FileReader(path));
				return (T) o;
			} catch(Exception e) {
				return null;
			}
		}
	}
	
	public static class InputLoop {
		public static void loop(Runnable lamb) {
			while(true) {
				try {
					System.out.println("Waiting...");
				    Scanner in = new Scanner(System.in);
				    in.nextLine();
				    
				    lamb.run();
				    
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		}

		public static void run(Runnable lamb) {
			try {
				System.out.println("Waiting...");
				Scanner in = new Scanner(System.in);
				in.nextLine();

				lamb.run();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	public static void main(String[] args) {

		FightContext context = new FightContext();
		Board board = new Board();
		//context.board = board;
		
		Character source = new Character(context, 1, null); //, new ArrayList<>());
		Character target = new Character(context, 2, null); //, null);
		Cell targetCell = new Cell(2, 3, 4);
		board.getCells().put(targetCell.getPos().x, targetCell.getPos().y, targetCell);

		InputLoop.loop(() -> {
		    ScriptedSkill spell = ScriptedEngine.eval("res/spells/PoisonTrap.rb");
			spell.getOnCastHandler().handle(new CastSpellEvent(context, source, targetCell, spell));
		});

		InputLoop.loop(() -> {
		    ScriptedSkill spell = ScriptedEngine.eval("res/classes/Grim/DarkHarvest.rb");
			spell.getOnCastHandler().handle(new CastSpellEvent(context, source, targetCell, spell));
		});

	}
	
	
}
