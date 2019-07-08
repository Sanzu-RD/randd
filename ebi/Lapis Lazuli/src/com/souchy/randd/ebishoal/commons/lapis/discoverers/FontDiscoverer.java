package com.souchy.randd.ebishoal.commons.lapis.discoverers;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class FontDiscoverer extends LibGdxFileDiscoverer<LabelStyle> {

	@Override
	public boolean identify(FileHandle toFilter) {
		return toFilter.extension().contentEquals(".fnt");
	}


	@Override
	protected LabelStyle output(FileHandle file) {
		FileHandle directory = file.parent();
		
		//File textureFile = new File(file.getAbsolutePath().replace(".fnt", ".png"));
		FileHandle textureFile = directory.child(file.nameWithoutExtension() + ".png");
		Texture texture = new Texture(textureFile, true); //Gdx.files.internal("fonts/hongkonghustle-hiero-100_00.png"), true); // true enables mipmaps
		texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear); // linear filtering in nearest mipmap image
		BitmapFont font = new BitmapFont(file, new TextureRegion(texture), false); // Gdx.files.internal("fonts/hongkonghustle-hiero-100.fnt")
		LabelStyle style = new LabelStyle();
		style.font = font;
		//styles.add(style);
		
		return style;
	}
	
}
