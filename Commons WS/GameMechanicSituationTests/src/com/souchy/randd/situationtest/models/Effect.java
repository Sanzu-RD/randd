package com.souchy.randd.situationtest.models;

import java.util.List;
import java.util.function.Predicate;

import com.souchy.randd.jade.api.ICell;
import com.souchy.randd.situationtest.matrixes.ConditionMatrix;
import com.souchy.randd.situationtest.matrixes.EffectMatrix;
import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.models.map.Cell;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.properties.types.Orientation;

/**
 * 
 * TODO les classes d'effet 
 * 
 * TODO la fonction effet.description() peut �tre impl�ment�e en renvoyant une string standard avec des %s dedans. ex les langs dans dofus, on peut juste renvoyer une r�f�rence � une ligne du lang en incluant les certains param�tres. �a permet de faire le i18n en m�me temps 
 * 
 * @author Souchy
 *
 */
public abstract class Effect {

	private FightContext context;
	protected Character source;
	
	
	public List<Predicate<FightContext>> conditions;
	public EffectMatrix aoe;
	//public ConditionMatrix cm;
	
	
	public Effect(FightContext c, Character source, EffectMatrix em, ConditionMatrix cm) {
		context = c;
		this.source = source;
		this.aoe = em;
		//this.cm = cm;
	}
	public Effect(FightContext c, Character source, EffectMatrix em, ConditionMatrix cm, String description) {
		this(c, source, em, cm);
		
	}
	
	
	
	/**
	 * Vérifie si toutes les conditions sont remplies
	 * @param context - contexte/�tat du combat
	 * @return - True si toutes les conditions sont remplies, false si au moins une condition ne l'est pas.
	 */
	public boolean assertConditions(FightContext context) {
		for(Predicate<FightContext> c : conditions)
			if(c.test(context) == false) return false;
		return true;
	}

	/**
	 * Trouve les cellules dans l'Aoe autour de la targetCell pointée. <p>
	 * Filtre les bonnes cellules à affecter grâce à un predicate. <p>
	 * Applique l'effet sur les cellules restantes.
	 * 
	 * @param targetCell Cellule pointé par la souris, cellule sur laquelle on cast le sort ou action
	 * @param filter Predicate pour filtrer les targets dans l'Aoe créée autour de la cellule point�e
	 */
	public void applyAoe(ICell targetCell, Orientation ori, Predicate<ICell> filter) {
		List<ICell> cells = context.board.getTargets(targetCell, ori, aoe); //, targetFlags());
		cells.stream()
		.filter(c -> filter.test(c)) // filtre les bons targets
		/*.filter(c -> {
			int x = c.getPos().x;
			int y = c.getPos().y;
			cm.get(x, y);
		})*/
		.forEach(c -> apply(c)); 	 // applique l'effet à chaque cell
	}
	
	/**
	 * Applique l'effet sur la cellule choisie.
	 * 
	 * @param cell
	 */
	protected abstract void apply(ICell cell);

	
	/**
	 * 
	 */
	public abstract int[][] previsualisation(Cell c);
	
	/**
	 * Tells which kind of targets this effect applies to.
	 * <br>
	 * Example an AoE that hits only enemies, or only allies, or both, or only summons, etc.
	 * @return Prob a byte or short that you do bitwise operations to.
	 * <br> Ex flag = <s>00000000 |=</s> (ALLY_HEROES | ALLY_SUMMONS) pour target juste les alli�s
	 * <br> Ex flag = (ALLY_SUMMONS | ENEMY_SUMMONS) pour target juste les invocations
	 * <br> Ex flag = (ALLY_TRAPS | ENEMY_TRAPS) pour target juste les traps
	 */
	//public abstract int targetFlags();

	/**
	 * Conditions for the application of the effect
	 * <p>
	 * -> pas besoin d'avoir cette fonction dans l'environnement client 
	 * <p>
	 * -> edit : rectification, c'est une bonne chose d'avoir les conditions c�t� client aussi pour pouvoir faire les calculs priliminaires sur chaque cellules de ce c�t�.
	 * <br>Comme �a, une fois que le client a d�cid� de son cast, le serveur a juste besoin de v�rifier le cast sur la cellule demand� plut�t que toutes les cellules
	 */
	//public void conditions();
	

}
