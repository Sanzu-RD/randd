package com.souchy.randd.situationtest.situations;

import com.souchy.randd.jade.api.ICell;
import com.souchy.randd.situationtest.effects.resources.DamageEffect;
import com.souchy.randd.situationtest.events.CastSpellEvent;
import com.souchy.randd.situationtest.events.OnHitEvent;
import com.souchy.randd.situationtest.events.statschange.StatChangeEvent;
import com.souchy.randd.situationtest.eventshandlers.EventHandler;
import com.souchy.randd.situationtest.eventshandlers.OnHitReceived;
import com.souchy.randd.situationtest.eventshandlers.OnHitSomeone;
import com.souchy.randd.situationtest.eventshandlers.OnStatChangeHandler;
import com.souchy.randd.situationtest.eventshandlers.turn.OnTurnEndHandler;
import com.souchy.randd.situationtest.math.Point3D;
import com.souchy.randd.situationtest.math.matrixes.ConditionMatrix;
import com.souchy.randd.situationtest.math.matrixes.EffectMatrix;
import com.souchy.randd.situationtest.math.matrixes.Matrix;
import com.souchy.randd.situationtest.math.matrixes.MatrixFlags;
import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.models.stage.Board;
import com.souchy.randd.situationtest.models.stage.Cell;
import com.souchy.randd.situationtest.properties.ElementValue;
import com.souchy.randd.situationtest.properties.types.Damages;
import com.souchy.randd.situationtest.properties.types.Elements;
import com.souchy.randd.situationtest.properties.types.Orientation;
import com.souchy.randd.situationtest.properties.types.StatProperties;
import com.souchy.randd.situationtest.scripts.ScriptedSkill;
import com.souchy.randd.situationtest.scripts.Status;
import com.souchy.randd.situationtest.situations.Situation4Events.ScriptedEngine;

public class Situation7Matrixes {
	
	
	public static void main(String args[]) {
		/*
		EffectMatrix effect = new EffectMatrix(new int[][]{
			{2+4,4,4},
			{4,0,0},
			{4,0,0},
		});
		
		Matrix effectOnMap = Matrix.toMapMatrix(effect, new Point2D(1, 1), 4, 5);

		PositionMatrix position = new PositionMatrix(new int[][]{
			{1,1},
			{1,1},
		});

		Matrix positionOnMap = Matrix.toMapMatrix(position, new Point2D(1, 1), 4, 5);
		
		Matrix plex = Matrix.ahh(positionOnMap, effectOnMap);
		
		// woo it's working 
		// tho the Y-axes is horizontal and the X-axis is vertical, doesnt matter
		System.out.println(plex.toString()); 
		
		// now we have the cells where to actually apply the effect
		//List<IEntity> entities1 = 
		*/
		
		

		Matrix heightMap = new Matrix(new int[][] {
			{1,2,3,4,5,6},
			{2,0,0,0,0,0},
			{3,0,0,0,0,0},
			{4,0,0,0,0,0},
			{5,0,0,0,0,0},
		});
		EffectMatrix aoe1 = new EffectMatrix(new int[][]{
			{4,4,4},
			{4,4,0},
			{4,0,0},
		});
		ConditionMatrix cond1 = new ConditionMatrix(new int[][]{
			{0,0,0},
			{0,0,0},
			{0,0,0},
		});
		
		// Créé le contexte et la map
		FightContext context = new FightContext();
		Board board = context.board;
		heightMap.foreach((i, j) -> {
			Cell c = new Cell(i, j, heightMap.get(i, j));
			board.map.put(i, j, c);
		});
		// Créé deux perso
		// need to optimize this so we write the pos only once etc, make everything linked in 1
		Character source = new Character(context, 1, new Point3D(1, 2, 0));
		Character target = new Character(context, 2, new Point3D(2, 2, 0));
		board.getCell(1, 2).testSetCharacter(source);
		board.getCell(2, 2).testSetCharacter(target);
		source.baseStats.get(StatProperties.Resource1).value = 30;
		target.baseStats.get(StatProperties.Resource1).value = 100;

		// Créé des EventHandlers
		// target.register((OnHitEvent e)  -> { // marche pas :(
		OnHitReceived h = target.register(e -> {
			System.out.println("target has been hit by (sourceID:"+e.source.getID()+") !");
		});
		OnHitReceived h1 = source.register(e -> {
			System.out.println("source has been hit by (sourceID:"+e.source.getID()+") !");
		});
		OnHitSomeone h2 = source.register(e -> {
			System.out.println("source has hit (targetID:"+e.target.getID()+") !");
		});
		source.register(new OnStatChangeHandler(source, true, e -> {
			System.out.println("source("+e.source.getID()+") has changed target(" + e.target.getID() + ") property by : " + e.changedProp + ",\n\t result :"  + ((Character)e.target).stats.get(e.changedProp.type));
		}));
		target.register(new OnStatChangeHandler(target, false, e -> {
			System.out.println("target has had a property changed by : " + e.changedProp + ",\n\t result :"  + ((Character)e.target).stats.get(e.changedProp.type));
		}));
		
		/* this comment block code works. commented just to try it from a scriptedskill
		// Spell effects creation :
		ElementValue scl = new ElementValue(Elements.Dark, 50);
		ElementValue flat = new ElementValue(Elements.Dark, 50);
		DamageEffect effect1 = new DamageEffect(context, source, Damages.Hit, Elements.Dark, scl, flat, aoe1, cond1);
		
		// Spell application :
		ICell targetCell = board.getCell(1, 2);
		effect1.applyAoe(targetCell, Orientation.North, c -> c.hasCharacter());
		 */
		// List<ICell> targets = board.getTargets(targetCell, Orientation.North, aoe1);
		
		ScriptedSkill spell = ScriptedEngine.eval("data/test/spells/Bubble.rb");
		ICell targetCell = board.getCell(1, 1);
		spell.getOnCastHandler().handle(new CastSpellEvent(context, source, (Cell)targetCell, spell));
	}
	
	
	/**
	 * to be done in ruby
	 */
	public static class BleedStatus extends Status {
		private DamageEffect effect1;
		//private OnStatChangeHandler onStatRouter;
		public BleedStatus(FightContext context, Character source, Character target) {
			super(context, source, target);

			int[][] em = { {  MatrixFlags.PositionningFlags.TargetCell.getID() } };
			int[][] cm = { { MatrixFlags.CellConditiontFlags.EnemyCell.getID() } };
			
			// TODO faudrait mettre les paramètres de dommages dans le ctor du bleedstatus
			effect1 = new DamageEffect(context, source, Damages.Dot, Elements.Physical, 
					new ElementValue(Elements.Physical, 50), new ElementValue(Elements.Physical, 50), 
					new EffectMatrix(em),  new ConditionMatrix(cm));
		}

		@Override
		public void registerHandlers() {
			/*// route les statChangeEvents vers le bus de ce status
			// ceci retourne un handler pour qu'on puisse le unregister plus tard dans status.unroute()
			onStatRouter = this.context().<OnStatChangeHandler>route(this);
			// puis enregistre un handler pour ces statChangeEvents
			bus().register(new OnStatChangeHandler(target(), false, p -> {
				System.out.println("target has had a property changed by : " + p 
						+ ",\n\t result :"  + target().stats.get(p.type));
			}));*/
			this.<OnTurnEndHandler>register((e) -> {
				target().getOccupiedCells().getCells().forEach(c -> {
					effect1.applyAoe(c, Orientation.North, c2 -> true);
					// TODO devrait mettre un flag dans le context pour dire si un character a déjà été affecté par un même effet
					// pour pas l'affecter 2 fois (via aoe sur une entité 2x2) d'un coup
					// on mettrait la condition dans effect.applyAoe
				});
			});
		}
		@Override
		public void unroute() {
			super.unroute();
			// enlève le router du contexte
			/*context().unregister(onStatRouter);*/
			// pourrait enlever le handler de ce bus aussi
		}
		
	}
	
	
	
}
