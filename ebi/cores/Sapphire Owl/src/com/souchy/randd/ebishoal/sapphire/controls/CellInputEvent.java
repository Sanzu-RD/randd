package com.souchy.randd.ebishoal.sapphire.controls;

import com.badlogic.gdx.math.Vector3;

public class CellInputEvent {
	
	private CellInputEventType type;
	//private Cell target = CellInputListener.selectedCell;
	//private float x; // = SmallListener.vold.x;
	//private float y; // = SmallListener.vold.y;
	private Vector3 newCoord;
	private Vector3 oldCoord;
	private int buttonCode;
	private int keyCode;
	private int scrollAmount;
	
	
	public CellInputEventType getType() {
		return type;
	}
	public Vector3 getTarget() {
		return newCoord;
	}
	public Vector3 getOld() {
		return oldCoord;
	}
	public int getButtonCode() {
		return buttonCode;
	}
	public int getKeyCode() {
		return keyCode;
	}
	public int getScrollAmount() {
		return scrollAmount;
	}

	
}
