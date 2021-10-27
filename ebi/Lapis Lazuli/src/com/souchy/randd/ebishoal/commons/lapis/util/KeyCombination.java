package com.souchy.randd.ebishoal.commons.lapis.util;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.KeyCombination.KeyCombinationArray;

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
	
	/**
	 * 
	 * 
	 * @author Blank
	 * @date 26 oct. 2021
	 */
	public static class KeyCombinationListener {
		public Table<Integer, Integer, Lambda> keyCombos = HashBasedTable.create();
		public LinkedList<Integer> keyBuffer = new LinkedList<>();
		/** Can set this lambda to init the combos, or extend the class and override initCombos() */
		public Lambda initCombos = () -> {};
		public KeyCombinationListener() {
		}
		public KeyCombinationListener(Lambda initCombos) {
			this.initCombos = initCombos;
		}
		/** Can extend the class and override initCombos() or set the lambda to init the combos */
		public void initCombos() {
			keyCombos.clear();
			initCombos.call();
		}
		public void putCombo(KeyCombinationArray arr, Lambda l) {
			for(var c : arr.combinations) putCombo(c, l);
		}
		public void putCombo(KeyCombination c, Lambda l) {
			keyCombos.put(c.key1, c.key2, l);
			keyCombos.put(c.key2, c.key1, l);
		}
		public boolean keyDown(int keycode) {
			// CTRL+ALT+H : hard code shortcut to init/reset key combos
			if(keycode == Keys.H && Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Keys.ALT_LEFT)) 
				initCombos();
			
			// if the buffer is already full, remove the head
			if(keyBuffer.size() == 2) keyBuffer.poll();
			
			// add the key to the buffer
			keyBuffer.offer(keycode); 
			
			// get the lambda associated to the key combo then call it if not null
			var key1 = keyBuffer.peekFirst();
			var key2 = keyBuffer.peekLast();
			Lambda c = keyCombos.get(key1, key2);
			Log.info("keyDown (%s) (%s, %s) = %s", keycode, key1, key2, c);
			if(c != null) {
				 c.call();
				 return true;
			}
			return false;
		}
		public boolean keyUp(int keycode) {
			keyBuffer.removeFirstOccurrence(keycode);
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
