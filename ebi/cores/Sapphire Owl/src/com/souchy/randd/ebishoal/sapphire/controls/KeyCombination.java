package com.souchy.randd.ebishoal.sapphire.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

/**
 * 
 * @author Blank
 * @date 3 oct. 2021
 */
public class KeyCombination {
	
	/**
	 * might want to wrap the fors in try/catch for concurrent modification
	 * 
	 * @author Blank
	 * @date 3 oct. 2021
	 */
	public static class KeyCombinationArray {
		public KeyCombination[] combinations = new KeyCombination[0];
		public KeyCombinationArray() {
			
		}
		public KeyCombinationArray(KeyCombination... combinations) {
			this.combinations = combinations;
		}
		public boolean isPressed() {
			for(var combo : combinations) {
				if(combo.isPressed()) return true;
			}
			return false;
		}
		public boolean isJustPressed() {
			for(var combo : combinations) {
				if(combo.isJustPressed()) return true;
			}
			return false;
		}
	}
	
	
	public int key1 = Keys.ANY_KEY;
	public int key2 = Keys.ANY_KEY;

	public KeyCombination() {
		
	}
	public KeyCombination(int key1) {
		this(key1, key1);
	}
	
	public KeyCombination(int key1, int key2) {
		this.key1 = key1;
		this.key2 = key2;
	}

	public KeyCombination(String key1) {
		this(key1, key1);
	}
	public KeyCombination(String key1, String key2) {
		this(Keys.valueOf(key1), Keys.valueOf(key2));
	}
	
	public boolean isPressed() {
		return check();
	}

	public boolean isJustPressed() {
		return check();
	}
	
	private boolean check() {
		// key1 cannot be ANY_KEY because we need at least 1 proper key to activate the combination
		// key2 can be ANY_KEY
		// we only check Gdx.input if the key1 or key2 is valid (not ANY_KEY)
		return     (key1 != Keys.ANY_KEY && Gdx.input.isKeyPressed(key1)) 
				&& (key2 == Keys.ANY_KEY || Gdx.input.isKeyPressed(key2));
	}
	
	
}
