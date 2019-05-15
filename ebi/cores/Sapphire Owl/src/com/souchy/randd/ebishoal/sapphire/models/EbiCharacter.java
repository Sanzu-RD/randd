package com.souchy.randd.ebishoal.sapphire.models;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.souchy.randd.situationtest.math.Point3D;
import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.models.org.FightContext;

public class EbiCharacter extends Character {

	
	private AnimationController animationController;
	private ModelInstance modelInst;
	
	public EbiCharacter(FightContext context, int id, Point3D pos) {
		super(context, id, pos);
		// TODO Auto-generated constructor stub
		
		Model model = null;
		modelInst = new ModelInstance(model);
		animationController = new AnimationController(modelInst);
		animationController.setAnimation("HumanArmature|Spider_Walk", -1);
	}
	
}
