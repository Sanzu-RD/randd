package com.souchy.randd.ebishoal.commons.lapis.gfx.screen;

public class RenderOptions {
	
	/** Render the ModelCache (ie. map terrain) */
	public static boolean renderCache = true;
	public static boolean activateShadows = true;
	public static boolean onlyShadowMap = false;
	public static boolean activatePP = true;
	public static boolean renderBackground = true;
	public static boolean renderUI = true;
	public static boolean cullback = true; // déjà setté dans les shader classes DefaultShader.Config et DepthShader.Config
	public static boolean renderLines = false;
}
