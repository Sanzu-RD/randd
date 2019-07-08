package com.souchy.randd.ebishoal.sapphire.ui.roundImage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.sapphire.ui.SapphireBatch;

public class RoundImage extends Image {
	public RoundImage(Drawable d) {
		super(d);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		validate();

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		float x = getX();
		float y = getY();
		float scaleX = getScaleX();
		float scaleY = getScaleY();

		//Log.info("drawer = " + getDrawable() + ", " + (getDrawable() instanceof TextureRegionDrawable));
		if(getDrawable() instanceof TextureRegionDrawable) {
			var drawer = ((TextureRegionDrawable) getDrawable());
			//Log.info("region = " + drawer.getRegion() + ", " + (drawer.getRegion() instanceof RoundTextureRegion));
			if(drawer.getRegion() instanceof RoundTextureRegion) {
				((SapphireBatch) batch).draw((RoundTextureRegion) drawer.getRegion(), x + getImageX(), y + getImageY(), getImageWidth() * scaleX, getImageHeight() * scaleY);
				return;
			}
		}
		
		if (getDrawable() instanceof TransformDrawable) {
			float rotation = getRotation();
			if (scaleX != 1 || scaleY != 1 || rotation != 0) {
				((TransformDrawable)getDrawable()).draw(batch, x + getImageX(), y + getImageY(), getOriginX() - getImageX(), getOriginY() - getImageY(),
					getImageWidth(), getImageHeight(), scaleX, scaleY, rotation);
				return;
			}
		}
		if (getDrawable() != null) getDrawable().draw(batch, x + getImageX(), y + getImageY(), getImageWidth() * scaleX, getImageHeight() * scaleY);
	}
}