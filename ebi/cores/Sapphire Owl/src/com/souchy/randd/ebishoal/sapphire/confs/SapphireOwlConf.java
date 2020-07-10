package com.souchy.randd.ebishoal.sapphire.confs;

import com.badlogic.gdx.graphics.Color;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;

/**
 * @TODO Make it a preference file instead
 * 
 * @author Blank
 * @date 9 juill. 2020 - time of comment
 */
public class SapphireOwlConf extends JsonConfig {
	
	public boolean activateGrid = false;
	public boolean activateRuler = false;
	public boolean activateCenterline = false;
	public Color gridColor = Color.GRAY;
	
	
	
	
	public boolean render_shadows = true;
	public String render_background = "";
	public Color render_backgroundcolor = Color.BLACK;
	
	public boolean render_postProcess = true;
	public byte render_postProcess_blur = 3;
	public boolean render_postProcess_stencil = true;
	
	
}
