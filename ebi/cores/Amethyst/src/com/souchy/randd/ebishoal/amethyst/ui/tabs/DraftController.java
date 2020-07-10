package com.souchy.randd.ebishoal.amethyst.ui.tabs;

import java.io.File;
import java.util.Timer;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.coral.draft.ChangeTurn;
import com.souchy.randd.commons.coral.draft.SelectCreature;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.amethyst.ui.components.DraftRow;
import com.souchy.randd.ebishoal.coraline.Coraline;
import com.souchy.randd.jade.matchmaking.GameQueue;
import com.souchy.randd.jade.matchmaking.Team;

import gamemechanics.ext.AssetData;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.Creature;
import gamemechanics.models.CreatureModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class DraftController {
	
	// creature list flowpane
	// team A - modifiable affinities
	// bans A
	// team B
	// bans B
	
	// timer
	// select button
	
	// beryl chat	
	
	// talk with coral to build jademodels (creature customisations)
	
	
	// packets :
	// <-> select creature
	// <- start timer / change turn
	// -> jadecreature array (affinity customization)
	
	@FXML public FlowPane creatureList;
	
	@FXML public GridPane teamA;
	@FXML public GridPane bansA;
	
	@FXML public GridPane teamB;
	@FXML public GridPane bansB;
	
	@FXML public Label lblTime;
	
	@FXML public Button btnSelect;
	
	private ImageView selected;
	private Timeline timeline;
	
	@FXML
	public void initialize() {
		Coraline.core.bus.register(this);
		
		// clear everything else
		teamA.getChildren().clear();
		bansA.getChildren().clear();
		teamB.getChildren().clear();
		bansB.getChildren().clear();
		
		// select btn
		btnSelect.setOnMouseClicked(this::select);
				
		// fill creature list with images + click handler
		for (var model : DiamondModels.creatures.values()) {
			String url = "";
			try {
				
				// creature pool
				var data = AssetData.creatures.get(model.id());
//				Log.info("DraftController init model " + model + " data " + data);
//				Log.info("DraftController path 1 : " + data.getIconURL().toString());
//				Log.info("DraftController path 2 : " + data.getIcon().getAbsolutePath());
//				Log.info("DraftController path 3 : " + data.getIconPath().toString());
				url = data.getIconURL().toString();
//				url = data.getIcon().getAbsolutePath();
//				url = data.getIconPath().toString();
//				url = "res/textures/creatures/luna.png";
//				Log.info("DraftController . img url " + url);
				
				
				var img = new ImageView(url);
				img.setPreserveRatio(true);
				img.setSmooth(true);
				img.setFitWidth(48);
				img.setFitHeight(48);
				creatureList.getChildren().add(img);
				
				// identification property
				img.getProperties().put(CreatureModel.class, model);
				
				// click select effect
				creatureList.setOnMouseClicked(e -> {
//					Log.info("DraftController target " + e.getTarget());
//					Log.info("" + e.getPickResult());
					if(e.getTarget() == null) return;
					if(e.getTarget() instanceof ImageView == false) return;
					
					if(selected != null) selected.setEffect(null);
					var imgtarget = (ImageView) e.getTarget();
					imgtarget.setEffect(new Shadow(5, Color.ALICEBLUE));
					selected = imgtarget;
				});
			} catch (Exception e) {
				Log.info("DraftController Error creating image : " +  url, e);
			}
		}
	}
	
	/**
	 * Called on btn select click
	 * @param e
	 */
	public void select(MouseEvent e) { 
		Log.info("select");
		// get selected creature
		var filtered = creatureList.getChildren().filtered(i -> i.getEffect() != null);
		// be sure that a creature is selected
		if(filtered.size() == 0) return;
		// get creature model
		var img = filtered.get(0);
		var model = (CreatureModel) img.getProperties().get(CreatureModel.class);
		// unselect
		
//		var filtered = creatureList.getChildren().filtered(i -> i.getProperties().get(CreatureModel.class) == model);
//		var img = filtered.get(0);
		img.setEffect(null);
		selected = null;
		
		Log.info("select " + model);
		
		// send SelectCreature message
		var msg = new SelectCreature();
		msg.modelid = model.id();
		Coraline.write(msg);
	}
	
	/**
	 * Event handler for SelectCreature message response
	 */
	@Subscribe
	public void receiveSelectMsg(SelectCreature msg) {
		Log.info("DraftController . onSelectCreature msg handler");
		Platform.runLater(() -> {
//			var icon = AssetData.creatures.get(msg.modelid).getIcon();
//			var node = new ImageView(icon.getAbsolutePath());
			var node = new DraftRow(msg.modelid);
			switch (msg.team) {
				case A -> teamA.add(node, 0, teamA.getChildren().size());
				case B -> teamB.add(node, 0, teamB.getChildren().size());
			}
		});
	}

	/**
	 * Event handler for timer/turnstate message 
	 */
	@Subscribe
	public void receiveTimerMsg(ChangeTurn msg) {
		if(msg.team == Team.A) {
			teamA.getCssMetaData().add(null);
		}
		var keyframe = new KeyFrame(Duration.seconds(1), ae -> {
			var key = (KeyFrame) ae.getSource();
			lblTime.setText(key.getTime().toSeconds() + "");
		});
		timeline.stop();
		timeline = new Timeline(keyframe);
		timeline.play();
	}
	
	
}
