package com.souchy.randd.ebishoal.sapphire.data;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.souchy.randd.ebishoal.sapphire.ui.roundImage.RoundTextureRegion;

/**
 * Facilitator class to add textures and roundtextures to a libgdx skin made for a creature
 * 
 * @author Blank
 *
 */
public class CreatureSkin extends Skin {
	
	public void add(String name, FileHandle file) {
		add(name, new Texture(file, true));
	}
	public void add(String name, Texture resource) {
		add(name, new TextureRegion(resource));
	}
	public void addRound(String name, FileHandle file) {
		addRound(name, new Texture(file, true));
	}
	public void addRound(String name, Texture resource) {
		add(name, new RoundTextureRegion(resource));
	}

	public void add(String name, TextureRegion resource) {
		add(name, new TextureRegionDrawable(resource));
	}

	public void add(String name, RoundTextureRegion resource) {
		add(name, new TextureRegionDrawable(resource));
	}
	
}
