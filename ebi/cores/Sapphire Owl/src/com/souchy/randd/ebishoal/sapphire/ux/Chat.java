package com.souchy.randd.ebishoal.sapphire.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.Focusable;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.HighlightTextArea;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTextArea;
import com.kotcrab.vis.ui.widget.VisTextArea.TextAreaListener;
import com.kotcrab.vis.ui.widget.VisTextField.VisTextFieldStyle;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.gfx.screen.LapisHud;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;

public class Chat extends SapphireWidget {

	@LmlActor("scroll")
	public VisScrollPane scroll;
	
	@LmlActor("area")
	public ScrollableTextArea area; /// HighlightTextArea

	@LmlActor("field")
	public VisTextField field;

	
	@Override
	public String getTemplateId() {
		return "chat";
	}
    
//	public Chat(Skin skin) {
//		super(skin);
//		Log.info("ctor chat");
//	}

	@Override
	protected void init() {
		refresh();
	}
	
	public void refresh() {
//		area.appendText("x : " + this.getX());
//		area.appendText("\n");
//		area.appendText("y : " + this.getY());
//		area.appendText("bug.3style : " + area.getStyle());
//		field.setText("w :" + field.getWidth());
		
		scroll.setOverscroll(false, false);
		scroll.setFlickScroll(false);
		scroll.setFadeScrollBars(false);
		scroll.setScrollbarsOnTop(false);
		scroll.setScrollingDisabled(true, false);
		scroll.setScrollBarPositions(true, false);
		
		Lambda focus = () -> getStage().setScrollFocus(scroll);
		Lambda unfocus = () -> {
			if(!area.hasKeyboardFocus()) getStage().setScrollFocus(null);
		};
		area.clearListeners();
		LapisUtil.onHover(scroll, focus, unfocus);
		LapisUtil.onClick(area, focus);
		area.addListener(area.new ScrollTextAreaListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				return true;
			}
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				return true;
			}
		});
		
		//this.setColor(1, 1, 1, 0.8f);
		

		Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
		
		this.addListener(new DragAndResize(this));
	}


	
	public static class DragAndResize extends InputListener {
		public static final float resizeBorder = 20;
		private static final int MOVE = 1 << 5;
		
		private float startX, startY, lastX, lastY;
		public Table table;
		public boolean isResizable = true;
		public boolean isMovable = true;
		public boolean keepWithinStage = true;
		public boolean isModal = true;
		public boolean dragging = false;
		public int edge = 0;
		public DragAndResize(Table table) {
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
//			if (isMovable && edge == 0 && y <= height && y >= height - padTop && x >= left && x <= right) 
//				edge = MOVE;

			Log.info("edge : " + edge + ", x,y : [" + x + "," + y + "], sides : ["+top+","+right+","+left+","+bottom+"],  pad : [" + padTop + ", " + padLeft + ", " + padBottom + ", " + padRight + "]");

			//Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
//			if((edge & Align.left) != 0 || (edge & Align.right) != 0)
//				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
//			else if((edge & Align.top) != 0 || (edge & Align.bottom) != 0)
//				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
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
	};

	
	
}
