package com.souchy.randd.ebishoal.amethyst.ui.tabs;

import java.io.File;

import com.souchy.randd.commons.coral.draft.SelectCreature;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.coraline.Coraline;

import gamemechanics.ext.AssetData;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.Creature;
import gamemechanics.models.CreatureModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

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
	
	@FXML public Button btnSelect;
	@FXML public Label lblTime;
	
//	@FXML public Chat chatController;
	
	@FXML
	public void initialize() {
		for (var model : DiamondModels.creatures.values()) {
			Log.info("draft init model " + model);
			try {
				var data = AssetData.creatures.get(model.id());
//				var icon = data.getIcon(); // Environment.fromRoot("res/textures/creatures/" + data.icon + ".png").toUri().toURL(); //AssetData.getCreatureIconPath(data.icon);
//				Log.info("data icon : " + icon.toString());
				var img = new ImageView(new Image(data.getIcon().toUri().toURL().toString()));
				img.getProperties().put(CreatureModel.class, model);
				creatureList.getChildren().add(img);
			} catch (Exception e) {
				Log.info("Error creating image : ", e);
			}
		}
	}
	
	public void select() {
		var msg = new SelectCreature();
		msg.modelid = 1;
		Coraline.write(msg);
	}
	
	
}
