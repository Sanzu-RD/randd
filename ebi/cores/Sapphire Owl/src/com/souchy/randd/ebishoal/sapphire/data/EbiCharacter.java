package com.souchy.randd.ebishoal.sapphire.models;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

import gamemechanics.common.Vector2;
import gamemechanics.models.Fight;
import gamemechanics.models.entities.Creature;

public class EbiCharacter extends Creature {

	
	private AnimationController animationController;
	private ModelInstance modelInst;

	public EbiCharacter(Fight context, int id, Vector2 pos) {
		//super(context, id, pos);
		// TODO Auto-generated constructor stub
		
		Model model = null;
		modelInst = new ModelInstance(model);
		animationController = new AnimationController(modelInst);
		animationController.setAnimation("HumanArmature|Spider_Walk", -1);
	}
	
}
