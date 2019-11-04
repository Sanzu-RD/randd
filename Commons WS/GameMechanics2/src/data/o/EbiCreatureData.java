package data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;

import data.CreatureData;

public abstract class EbiCreatureData {
	
	/**
	 * Textures
	 */
	public CreatureSkin skin;
	
	/**
	 * i18n bundle for every name, spell, description about this creature
	 */
	public I18NBundle i18n;
	
	/**
	 * Model model
	 */
	public Model model;
	
	/**
	 * Animation controller should be created foor each instance
	 */
	public AnimationController anim;
	
	public EbiCreatureData() {
		super();
		skin = new CreatureSkin();
		i18n = I18NBundle.createBundle(file("i18n/bundle"));
		// avatar
		skin.add(getIDName(), roundTexture("gfx/avatar.png"));
	}

	/**
	 * The creature name in the correct i18n language example : "Tree", "Arbre"
	 */
	public String getCreatureName() {
		return i18n.get(getNamePropertyID()); //creatureNameID());
	}
	

	/**
	 * Folder name is in lower case
	 */
	public String folder() {
		return "creatures/" + getIDName().toLowerCase() + "/";
	}
	public FileHandle file(String name) {
		return Gdx.files.internal(folder() + name);
	}
	/*
	public TextureRegionDrawable roundTexture(String path) {
		return new TextureRegionDrawable(new RoundTextureRegion(new Texture(file(path), true)));
	}
	*/
	
}
