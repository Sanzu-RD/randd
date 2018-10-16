package com.souchy.randd.ebishoal.commons.lapis.discoverers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.souchy.randd.commons.tealwaters.commons.Discoverer;

public class FontDiscoverer implements Discoverer<String, FileHandle, LabelStyle> {

	@Override
	public List<LabelStyle> explore(String path) {
		List<LabelStyle> styles = new ArrayList<>();

		FileHandle directory = Gdx.files.internal(path);
		if(directory == null) return styles;
		if(!directory.exists()) return styles;
		if(!directory.isDirectory()) return styles;

		loop(styles, directory);
		
		return styles;
	}
	
	
	private void loop(List<LabelStyle> styles, FileHandle directory) {
		for (FileHandle file : directory.list()) {
			if(file.isDirectory()) {
				loop(styles, file);
			} else 
			if(identify(file)) {
				//File textureFile = new File(file.getAbsolutePath().replace(".fnt", ".png"));
				FileHandle textureFile = directory.child(file.nameWithoutExtension() + ".png");
				Texture texture = new Texture(textureFile, true); //Gdx.files.internal("fonts/hongkonghustle-hiero-100_00.png"), true); // true enables mipmaps
				texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear); // linear filtering in nearest mipmap image
				BitmapFont font = new BitmapFont(file, new TextureRegion(texture), false); // Gdx.files.internal("fonts/hongkonghustle-hiero-100.fnt")
				LabelStyle style = new LabelStyle();
				style.font = font;
				styles.add(style);
			}
		}
	}

	@Override
	public boolean identify(FileHandle toFilter) {
		return toFilter.extension().contentEquals(".fnt");
	}
	
}
