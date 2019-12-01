package com.souchy.randd.ebishoal.commons.lapis.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class LapisUtil {
	
	public static void align(Table a) {
		float x = a.getX();
		float y = a.getY();
		// y
		if((a.getAlign() & Align.top) != 0) y = a.getStage().getHeight() - a.getY(); // top
		else if((a.getAlign() & Align.bottom) != 0) y = a.getY(); // bottom
		else y = a.getStage().getHeight() / 2f - a.getY(); // center
		// x
		if((a.getAlign() & Align.right) != 0) x = a.getStage().getWidth() - a.getX(); // right
		else if((a.getAlign() & Align.left) != 0) x = a.getX(); // left
		else x = a.getStage().getWidth() / 2f - a.getX(); // center
		
		a.setPosition(x, y, a.getAlign());
	}

//	public static int percentConverter(String val, float stageWidth) {
//		int result = 0;
//		if(val.contains("%")) {
//			val = val.substring(0, val.length() - 1);
//			result = Integer.parseInt(val);
//			result = (int) (stageWidth * result / 100f);
//		} else {
//			result = Integer.parseInt(val);
//		}
//		return result;
//	}
	
}
