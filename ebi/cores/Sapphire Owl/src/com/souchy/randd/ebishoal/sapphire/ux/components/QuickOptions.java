package com.souchy.randd.ebishoal.sapphire.ux.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;

public class QuickOptions extends SapphireComponent {

	@LmlActor("btnIsoTop")
	public Stack btnIsoTop;
	@LmlActor("lblIsoTop")
	public Label lblIsoTop;
	@LmlActor("imgIsoTop")
	public Image imgIsoTop;
	@LmlActor("imgFlag")
	public Image imgFlag;
	@LmlActor("imgRotate")
	public Image imgRotate;
	@LmlActor("imgGrid")
	public Image imgGrid;
	@LmlActor("imgRuler")
	public Image imgRuler;
	@LmlActor("imgCenterline")
	public Image imgCenterline;
	
	public QuickOptions(Stage stage) {
		super(stage);
	}

	@Override
	protected void onInit() {
		//var c = imgIsoTop.getColor();
//		Log.info("Quick options init " + lblIsoTop);
		LapisUtil.onClick(btnIsoTop, this::clickIsoTop);
		LapisUtil.onClick(imgFlag, this::clickFlag);
		LapisUtil.onClick(imgRotate, this::clickRotate);
		LapisUtil.onClick(imgGrid, this::clickGrid);
		LapisUtil.onClick(imgRuler, this::clickCenterline);
		LapisUtil.onClick(imgCenterline, this::clickCenterline);
		//imgIsoTop.setColor(c.r, c.g, c.b, 0.5f);
		//LapisUtil.setColor(this, new Color(1, 1, 1, 0.5f));
		LapisUtil.onHover(btnIsoTop, Color.RED);
		LapisUtil.onHover(imgRuler, Color.RED);
	}

	@Override
	public String getTemplateId() {
		return "quickoptions";
	}
	
	/**
	 * Reset the camera to iso or top view
	 */
	@LmlAction("clickIsoTop")
	public void clickIsoTop() {
		if(lblIsoTop == null) {
			Log.info("lbl " + lblIsoTop);
			return;
		}
		if(lblIsoTop.textEquals("TOP")) {
			lblIsoTop.setText("ISO");
			// align the camera
		} else {
			lblIsoTop.setText("TOP");
			// align the camera
		}
	}
	/**
	 * Rotate the camera around its axis
	 */
	@LmlAction("clickRotate")
	public void clickRotate() {
		
	}
	/**
	 * Place a flag
	 */
	@LmlAction("clickFlag")
	public void clickFlag() {
		
	}
	/**
	 * Show/hide the grid overlay (LineDrawing)
	 */
	@LmlAction("clickGrid")
	public void clickGrid() {
		SapphireOwl.conf.functionality.grid = !SapphireOwl.conf.functionality.grid;
	}
	/**
	 * Show the ruler (letters on one side and numbers on the other side, as in chess)
	 */
	@LmlAction("clickRuler")
	public void clickRuler() {
		SapphireOwl.conf.functionality.ruler = !SapphireOwl.conf.functionality.ruler;
	}
	/**
	 * Activate centerline highliting (highlights the row and column of the currently hovered cell)
	 */
	@LmlAction("clickCenterline")
	public void clickCenterline() {
		SapphireOwl.conf.functionality.centerline = !SapphireOwl.conf.functionality.centerline;
	}

	@Override
	public void resizeScreen(int w, int h, boolean centerCam) {
		// TODO Auto-generated method stub
		
	}
	
}
