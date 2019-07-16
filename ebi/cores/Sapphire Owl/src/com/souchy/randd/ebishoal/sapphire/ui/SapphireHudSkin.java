package com.souchy.randd.ebishoal.sapphire.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.souchy.randd.ebishoal.sapphire.ui.roundImage.RoundTextureRegion;

public class SapphireHudSkin extends Skin {
	
	public SapphireHudSkin(FileHandle file) {
		super(file);

		//parser.getData().setDefaultSkin(skin);
		
		var tex1 = new Texture(Gdx.files.absolute("G:/Assets/pack/kenney/kenney_emotespack/PNG/Pixel/Style 1/emote_alert.png"), true);
		var tex2 = new Texture(Gdx.files.internal("gdx/ui/res/borders/ring_frame.PNG"), true);
		var tex3 = new Texture(Gdx.files.internal("gdx/ui/res/buttons/slider_02_03.png"), true);
		var tex4 = new Texture(Gdx.files.internal("gdx/ui/res/buttons/slider_02_04.png"), true);
		var ava1 = new Texture(Gdx.files.internal("avatars/Tex_AnimeAva_15.png"), true);
		
		tex1.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		tex2.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		tex3.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		tex4.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		ava1.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		add("emote_alert", new TextureRegionDrawable(new RoundTextureRegion(tex1)));
		add("ring_frame", tex2);
		add("up", tex3);
		add("down", tex4);
		add("ava1", new TextureRegionDrawable(new RoundTextureRegion(ava1)));
		
		//Sungjin.loadResources(this);
		
	}
	
}
