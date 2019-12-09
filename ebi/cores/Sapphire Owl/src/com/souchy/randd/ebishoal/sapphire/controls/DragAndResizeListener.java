package com.souchy.randd.ebishoal.sapphire.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class DragAndResizeListener extends InputListener {
	private static final int MOVE = 1 << 5;
	
	public float resizeBorder = 20;
	private float startX, startY, lastX, lastY;
	public Table table;
	public boolean isResizable = true;
	public boolean isMovable = true;
	public boolean keepWithinStage = true;
	public boolean isModal = true;
	public boolean dragging = false;
	public int edge = 0;
	
	public DragAndResizeListener(Table table) {
		this.table = table;
	}

	private void updateEdge (float x, float y) {
		//x -= table.getX();
		//y -= table.getY();
		float border = resizeBorder / 2f;
		float width = table.getWidth(), height = table.getHeight();
		float padTop = table.getPadTop(), padLeft = table.getPadLeft(), padBottom = table.getPadBottom(), padRight = table.getPadRight();
		float left = padLeft, 
				right = width - padRight, 
				bottom = padBottom,
				top = height;
		edge = 0;
		if (isResizable && x >= left - border && x <= right + border && y >= bottom - border && y <= top + border) {
			if (x < left + border) edge |= Align.left;
			if (x > right - border) edge |= Align.right;
			if (y < bottom + border) edge |= Align.bottom;
			if (y > top - border) edge |= Align.top;
			if (edge != 0) border += 25;
			if (x < left + border) edge |= Align.left;
			if (x > right - border) edge |= Align.right;
			if (y > top - border) edge |= Align.top;
			if (isMovable && edge == 0) // && y <= height && y >= height - padTop && x >= left && x <= right) 
				edge = MOVE;
		}
//		if (isMovable && edge == 0 && y <= height && y >= height - padTop && x >= left && x <= right) 
//			edge = MOVE;

		//Log.info("edge : " + edge + ", x,y : [" + x + "," + y + "], sides : ["+top+","+right+","+left+","+bottom+"],  pad : [" + padTop + ", " + padLeft + ", " + padBottom + ", " + padRight + "]");

		//Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
//		if((edge & Align.left) != 0 || (edge & Align.right) != 0)
//			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
//		else if((edge & Align.top) != 0 || (edge & Align.bottom) != 0)
//			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
	}

	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		if (button == Buttons.LEFT || button == Buttons.RIGHT) {
			updateEdge(x, y);
			dragging = edge != 0;
			startX = x;
			startY = y;
			lastX = x - table.getWidth();
			lastY = y - table.getHeight();
		}
		return edge != 0 || isModal;
	}

	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		dragging = false;
	}

	public void touchDragged (InputEvent event, float x, float y, int pointer) {
		if (!dragging) return;
		float width = table.getWidth(), height = table.getHeight();
		float windowX = table.getX(), windowY = table.getY();

		float minWidth = table.getMinWidth(), maxWidth = table.getMaxWidth();
		float minHeight = table.getMinHeight(), maxHeight = table.getMaxHeight();
		Stage stage = table.getStage();
		boolean clampPosition = keepWithinStage && stage != null && table.getParent() == stage.getRoot();

		if ((edge & MOVE) != 0 && Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			float amountX = x - startX, amountY = y - startY;
			windowX += amountX;
			windowY += amountY;
		}
		if ((edge & Align.left) != 0) {
			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
			float amountX = x - startX;
			if (width - amountX < minWidth) amountX = -(minWidth - width);
			if (clampPosition && windowX + amountX < 0) amountX = -windowX;
			width -= amountX;
			windowX += amountX;
		}
		if ((edge & Align.bottom) != 0) {
			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
			float amountY = y - startY;
			if (height - amountY < minHeight) amountY = -(minHeight - height);
			if (clampPosition && windowY + amountY < 0) amountY = -windowY;
			height -= amountY;
			windowY += amountY;
		}
		if ((edge & Align.right) != 0) {
			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
			float amountX = x - lastX - width;
			if (width + amountX < minWidth) amountX = minWidth - width;
			if (clampPosition && windowX + width + amountX > stage.getWidth()) amountX = stage.getWidth() - windowX - width;
			width += amountX;
			//Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
		}
		if ((edge & Align.top) != 0) {
			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
			float amountY = y - lastY - height;
			if (height + amountY < minHeight) amountY = minHeight - height;
			if (clampPosition && windowY + height + amountY > stage.getHeight())
				amountY = stage.getHeight() - windowY - height;
			height += amountY;
		}
		table.setBounds(Math.round(windowX), Math.round(windowY), Math.round(width), Math.round(height));
	}

	public boolean mouseMoved (InputEvent event, float x, float y) {
		updateEdge(x, y);
		return isModal;
	}

	public boolean scrolled (InputEvent event, float x, float y, int amount) {
		return isModal;
	}

	public boolean keyDown (InputEvent event, int keycode) {
		return isModal;
	}

	public boolean keyUp (InputEvent event, int keycode) {
		return isModal;
	}

	public boolean keyTyped (InputEvent event, char character) {
		return isModal;
	}
}