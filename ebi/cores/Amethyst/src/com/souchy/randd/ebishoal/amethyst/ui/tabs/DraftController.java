package com.souchy.randd.ebishoal.amethyst.ui.tabs;

import java.io.File;
import java.util.Timer;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.coral.draft.ChangeTurn;
import com.souchy.randd.commons.coral.draft.SelectCreature;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.amethyst.main.Amethyst;
import com.souchy.randd.ebishoal.amethyst.ui.components.DraftRow;
import com.souchy.randd.ebishoal.coraline.Coraline;
import com.souchy.randd.jade.matchmaking.GameQueue;
import com.souchy.randd.jade.matchmaking.Team;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
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
	
	@FXML public FlowPane list;
	
	@FXML public GridPane teamLeft;
	@FXML public GridPane bansLeft;
	
	@FXML public GridPane teamRight;
	@FXML public GridPane bansRight;
	
	@FXML public Label lblTime;
	
	@FXML public Button btnSelect;
	
	private ImageView selected;
	private Timeline timeline;
	private CreatureModel selectedModel;
	public Team team;
	
	
	@FXML
	public void initialize() {
		Coraline.core.bus.register(this);
		Amethyst.core.bus.register(this);
		
		// clear everything else
//		teamLeft.getChildren().clear();
//		bansLeft.getChildren().clear();
//		leftRight.getChildren().clear();
//		bansRight.getChildren().clear();
		
		// select btn
		btnSelect.setOnMouseClicked(this::select);
		
		initCreatvureList();
		
		initTeams();
	}
	
	private void initCreatvureList() {
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
				
				// identification property
				img.getProperties().put(CreatureModel.class, model);
				
				// hover glow
				img.setOnMouseEntered(e -> {
					var imgtarget = (ImageView) e.getTarget();
					if(imgtarget.getEffect() == null) {
						imgtarget.setEffect(new Glow());
					}
				});
				img.setOnMouseExited(e -> {
					var imgtarget = (ImageView) e.getTarget();
					if(imgtarget.getEffect() instanceof Glow) {
						imgtarget.setEffect(null);
					}
				});
				
				list.getChildren().add(img);
				
				
				// click select effect
				list.setOnMouseClicked(e -> {
					Log.info("DraftController target " + e.getTarget());
//					Log.info("" + e.getPickResult());
					if(e.getTarget() == null) return;
					if(e.getTarget() instanceof ImageView == false) return;
					
					if(selected != null) selected.setEffect(null);
					var imgtarget = (ImageView) e.getTarget();
					imgtarget.setEffect(new DropShadow(3, Color.ALICEBLUE));
					selected = imgtarget;
					
					selectedModel = (CreatureModel) imgtarget.getProperties().get(CreatureModel.class);
				});
			} catch (Exception e) {
				Log.info("DraftController Error creating image : " +  url, e);
			}
		}
	}
	
	private void initTeams() {
		
	}
	
	/**
	 * Called on btn select click in the list
	 * @param e
	 */
	public void select(MouseEvent e) { 
		Log.info("select");
		// get selected creature
//		var filtered = creatureList.getChildren().filtered(i -> i.getEffect() != null);
//		// be sure that a creature is selected
//		if(filtered.size() == 0) return;
//		// get creature model
//		var img = filtered.get(0);
//		var model = (CreatureModel) img.getProperties().get(CreatureModel.class);
		var img = selected;
		var model = selectedModel;
		// unselect
		
//		var filtered = creatureList.getChildren().filtered(i -> i.getProperties().get(CreatureModel.class) == model);
//		var img = filtered.get(0);
		img.setEffect(null);
		selected = null;
		selectedModel = null;
		
		Log.info("select " + model);
		
		// send SelectCreature message
		var msg = new SelectCreature();
		msg.modelid = model.id();
		Coraline.write(msg);
	}
	
	/**
	 * On click edit/customize creature
	 */
	public void edit(MouseEvent e) {
		
	}
	
	
	/**
	 * Event handler for SelectCreature message response
	 */
	@Subscribe
	public void receiveSelectMsg(SelectCreature msg) {
		Log.error("DraftController . onSelectCreature event handler " + msg.modelid);
		Platform.runLater(() -> {
//			var icon = AssetData.creatures.get(msg.modelid).getIcon();
//			var node = new ImageView(icon.getAbsolutePath());
//			DraftRow node = new DraftRow(msg.modelid); // Amethyst.app.loadComponent("draftrow"); // new DraftRow(msg.modelid);
			
			var node = getRow(msg.team, Coraline.lobby.getPickTurn());
			
			// if the msg team is equal to this user's team, add the draftrow on the left team
//			if(this.team == msg.team) {
////				teamLeft.add(node, 0, teamLeft.getChildren().size());
//				node = null;
////				teamLeft.cell
//			} else {
//				leftRight.add(node, 0, leftRight.getChildren().size());
//				node.setDisable(true);
//			}
			
			node.init(msg.modelid);
			node.setOnMouseClicked(this::onRowClick);
		});
	}
	
	private DraftRow getRow(Team t, int pickTurn) {
		var col = teamLeft;
		if(this.team != t) col = teamRight;
		for (Node node : col.getChildren()) {
			if(GridPane.getRowIndex(node) == pickTurn) {
				return (DraftRow) node;
			}
		}
		return null;
	}
	
	/**
	 * TODO Event handler for BanCreature message response
	 */
	@Subscribe 
	public void receivebanMsg() { // BanCreature msg) {
		
	}
	
	
	/**
	 * When selecting an owned draft row, highlight its border
	 */
	public void onRowClick(MouseEvent e) {
		var row = (DraftRow) e.getTarget();
		var isLeft = teamLeft.getChildren().contains(row);
		if(!isLeft) return;
		teamLeft.getChildren().forEach(c -> c.setStyle("-fx-border-color: white; -fx-border-width: 2;"));
		row.setStyle("-fx-border-color: borderColor1; -fx-border-width: 2;");
	}

	/**
	 * Event handler for timer/turnstate message 
	 */
	@Subscribe
	public void receiveTimerMsg(ChangeTurn msg) {
//		if(msg.team == Team.A) {
//			teamLeft.getCssMetaData().add(null);
//		}
//		teamLeft.getChildren()
//		Coraline.lobby
		var keyframe = new KeyFrame(Duration.seconds(1), ae -> {
			var key = (KeyFrame) ae.getSource();
			lblTime.setText(key.getTime().toSeconds() + "");
		});
		timeline.stop();
		timeline = new Timeline(keyframe);
		timeline.play();
	}
	
	
	
	
	
	
}
