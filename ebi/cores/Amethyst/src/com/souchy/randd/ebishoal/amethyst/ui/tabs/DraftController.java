package com.souchy.randd.ebishoal.amethyst.ui.tabs;

import java.io.File;
import java.util.Timer;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.coral.draft.SelectCreature;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.coraline.Coraline;
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
		
		for (var model : DiamondModels.creatures.values()) {
			Log.info("draft init model " + model);
			try {
				// select btn
				btnSelect.setOnMouseClicked(this::select);
				
				// creature pool
				var data = AssetData.creatures.get(model.id());
				var img = new ImageView(data.getIconURL().toString());
				img.getProperties().put(CreatureModel.class, model);
				creatureList.getChildren().add(img);
				
				creatureList.setOnMouseClicked(e -> {
					Log.info("target " + e.getTarget());
					Log.info("" + e.getPickResult());
					if(e.getTarget() == null) return;
					
					if(selected != null) selected.setEffect(null);
					var imgtarget = (ImageView) e.getTarget();
					imgtarget.setEffect(new Shadow(5, Color.ALICEBLUE));
					selected = imgtarget;
				});
				
				// clear everything else
				teamA.getChildren().clear();
				bansA.getChildren().clear();
				teamB.getChildren().clear();
				bansB.getChildren().clear();
				
			} catch (Exception e) {
				Log.info("Error creating image : ", e);
			}
		}
	}
	
	/**
	 * Called on btn select click
	 * @param e
	 */
	public void select(MouseEvent e) {
		// remove selected creature
		var img = creatureList.getChildren().filtered(i -> i.getEffect() != null).get(0);
		var model = (CreatureModel) img.getProperties().get(CreatureModel.class);
		img.setEffect(null);
		selected = null;
		
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
		Platform.runLater(() -> {
			var icon = AssetData.creatures.get(msg.modelid).getIcon();
			var img = new ImageView(icon.getAbsolutePath());
			switch (msg.team) {
				case A -> teamA.add(img, 0, teamA.getChildren().size());
				case B -> teamB.add(img, 0, teamB.getChildren().size());
			}
			
		});
	}

	/**
	 * Event handler for timer/turnstate message 
	 */
	@Subscribe
	public void receiveTimerMsg(Object msg) {
		var keyframe = new KeyFrame(Duration.seconds(1), ae -> {
			var key = (KeyFrame) ae.getSource();
			lblTime.setText(key.getTime().toSeconds() + "");
		});
		timeline.stop();
		timeline = new Timeline(keyframe);
		timeline.play();
	}
	
}
