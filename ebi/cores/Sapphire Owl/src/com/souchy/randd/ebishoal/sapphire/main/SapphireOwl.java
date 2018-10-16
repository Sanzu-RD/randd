package com.souchy.randd.ebishoal.sapphire.main;

import com.badlogic.gdx.math.Vector2;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisCore;
import com.souchy.randd.ebishoal.commons.lapis.main.LapisGame;

public class SapphireOwl extends LapisCore {
	
	public final static SapphireOwl core = new SapphireOwl();
	
	public static void main(String[] args) throws Exception {
		launch(core);
	}
	

	/*public static void main (String[] arg) {
		Cat cat = new Cat(5);
		truc(() -> {});
		System.out.println("blue1 = ["+Color.BLUE.toIntBits()+"]"); // blue1 = [-65536]
		System.out.println("blue2 = ["+new Color(Color.BLUE.toIntBits()).toIntBits()+"]"); // blue2 = [65536]
		System.out.println("blue3 = ["+new Color(new Color(Color.BLUE.toIntBits()).toIntBits()).toIntBits()+"]"); // blue3 = [-65536] 
		//int ma = Color.rgba8888(Color.BLUE);
		//Color.rgba8888ToColor(new Color(), ma);
		//new Color(ma);
		
		//System.exit(0);
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	    config.title = "Sapphire Owl T";
	    
		new LwjglApplication(new SapphireGame(), config);
		System.out.println("hello");
	}*/

	
	@Override
	protected LapisGame createGame() {
		return new SapphireGame();
	}
	
	@Override
	public String getAppTitle() {
		return "Sapphire Owl";
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.ebishoal.sapphire" };
	}
	
	@Override
	public Vector2 getSize() {
		// TODO Auto-generated method stub
		return new Vector2(1600, 900);
	}
	
	@Override
	public int getSamples() {
		return 2;
	}
	
	@Override
	public SapphireGame getGame() {
		return (SapphireGame) super.getGame();
	}
	
}
