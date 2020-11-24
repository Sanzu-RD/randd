package com.souchy.randd.ebishoal.amethyst.ui.tabs;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.coral.draft.ChangeTurn;
import com.souchy.randd.commons.coral.draft.SelectCreature;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.amethyst.main.Amethyst;
import com.souchy.randd.ebishoal.amethyst.ui.components.DraftRow;
import com.souchy.randd.ebishoal.coraline.Coraline;
import com.souchy.randd.jade.matchmaking.Lobby;
import com.souchy.randd.jade.matchmaking.Team;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
	private Timeline timeline = new Timeline();
	private CreatureModel selectedModel;
	public Team team;
	
	public DraftController() throws Exception {
		Amethyst.core.bus.register(this);
	}
	
	@FXML
	public void initialize() throws Exception {
		try {
			// select btn
			btnSelect.setOnMouseClicked(this::select);
			
			initCreatureList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		try {
			initTeams();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initCreatureList() {
		// fill creature list with images + click handler
		for (var model : DiamondModels.creatures.values()) {
			String url = "";
			try {
				
				// creature pool
				var data = AssetData.creatures.get(model.id());
				// Log.info("DraftController init model " + model + " data " + data);
				// Log.info("DraftController path 1 : " + data.getIconURL().toString());
				// Log.info("DraftController path 2 : " + data.getIcon().getAbsolutePath());
				// Log.info("DraftController path 3 : " + data.getIconPath().toString());
				url = data.getIconURL().toString();
				// url = data.getIcon().getAbsolutePath();
				// url = data.getIconPath().toString();
				// url = "res/textures/creatures/luna.png";
				// Log.info("DraftController . img url " + url);
				
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
					// Log.info("" + e.getPickResult());
					if(e.getTarget() == null) return;
					if(e.getTarget() instanceof ImageView == false) return;
					
					if(selected != null) selected.setEffect(null);
					var imgtarget = (ImageView) e.getTarget();
					imgtarget.setEffect(new DropShadow(3, Color.ALICEBLUE));
					selected = imgtarget;
					
					selectedModel = (CreatureModel) imgtarget.getProperties().get(CreatureModel.class);
				});
			} catch (Exception e) {
				Log.info("DraftController Error creating image : " + url, e);
			}
		}
	}
	
	private void initTeams() throws Exception {
		Platform.runLater(() -> {
			this.team = Coraline.lobby.team(Amethyst.user._id);
			Log.info("DraftController init team " + team);
			
			for(Team t : Team.values()) {
				var bans = (t == this.team) ? bansLeft : bansRight;
				var picks = (t == this.team) ? teamLeft : teamRight;
//				bans.getChildren().clear();
//				picks.getChildren().clear();
				
				// init bans 
				for(int i = 0; i < Coraline.lobby.bansPerTeam; i++) {
					var img = new ImageView();
					img.setFitHeight(32);
					img.setFitWidth(32);
					var p = new AnchorPane(img);
					p.getStyleClass().add("banborder");
//					p.setStyle("-fx-border-width: 1; -fx-border-color: gold;"); 
					
					bans.add(p, i, 0); 
				}

				// init picks
//				picks.getStyleClass().add("rowborder");
//				picks.setStyle("-fx-background-color: pink;");
				for (int i = 0; i < Coraline.lobby.picksPerTeam; i++) {
					DraftRow row = DraftRow.create(); // new DraftRow();
//					DraftRow row = getPickRow(t, i);
					row.clear();
					row.turn = Coraline.lobby.totalBans() + i * Lobby.teamCount;
					row.getStyleClass().add("rowborder");
					row.name.setText("bambi " + i);
//					row.setStyle("-fx-border-width: 1; -fx-border-color: gold; -fx-background-color: cyan;"); 
					picks.add(row, 0, i);
					
					Log.info("draftrow team " + t + " pick id " + i);
				}
			}


		});
	}
	
	/**
	 * Called on btn select click in the list
	 * @param e
	 */
	public void select(MouseEvent e) { 
//		Log.info("select");
		// get selected creature
//		var filtered = creatureList.getChildren().filtered(i -> i.getEffect() != null);
//		// be sure that a creature is selected
//		if(filtered.size() == 0) return;
		
//		// get creature model
//		var img = filtered.get(0);
//		var model = (CreatureModel) img.getProperties().get(CreatureModel.class);
		var img = selected;
		var model = selectedModel;

		Log.info("select " + model);
		
		if(img == null || model == null) {
			return;
		}

		if(Coraline.lobby.isPickbanOver()) return;
		
		// unselect
//		var filtered = creatureList.getChildren().filtered(i -> i.getProperties().get(CreatureModel.class) == model);
//		var img = filtered.get(0);
		if(img != null) img.setEffect(null);
		
		selected = null;
		selectedModel = null;
		
		
		
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
	 * Coral handler for SelectCreature message response
	 */
	@Subscribe
	public void receiveSelectMsg(SelectCreature msg) {
		Log.error("DraftController . onSelectCreature event handler " + msg.modelid + ", on turn " + msg.turn);
		Platform.runLater(() -> {
			// set turn
			Coraline.lobby.turn(msg.turn);
			msg.team = Coraline.lobby.getTeamPlaying();
			
			// enlève la picked creature de la creatureList
			// ...
			
			// set ban or pick
			if(Coraline.lobby.isBanPhase()) {
				Log.info("select turn " + Coraline.lobby.turn() + ", team " + Coraline.lobby.getTeamPlaying() + ", ban " + Coraline.lobby.getTeamBanIndex());
				var node = getBanColumn(msg.team, Coraline.lobby.getTeamBanIndex());
				// set image + tooltip
				node.setImage(new Image(AssetData.creatures.get(msg.modelid).getIconURL().toString()));
			} else {
				Log.info("select turn " + Coraline.lobby.turn() + ", team " + Coraline.lobby.getTeamPlaying() + ", pick " + Coraline.lobby.getTeamPickIndex());
				var node = getPickRow(msg.team, Coraline.lobby.getTeamPickIndex());
				node.init(msg.modelid);
				node.getStyleClass().add("selected");
			}
		});
	}

	private ImageView getBanColumn(Team t, int pickTurn) {
		var teamGrid = bansLeft;
		if(this.team != t) teamGrid = bansRight;
		Log.format("DraftController getbanColumn (%s vs %s) = %s + %s", team, t, teamGrid, pickTurn);
		for (Node node : teamGrid.getChildren()) {
			var col = GridPane.getColumnIndex(node);
			if(col == null) col = 0;
			if(col == pickTurn) {
				return (ImageView) ((AnchorPane) node).getChildren().get(0);
			}
		}
		return null;
	}
	private DraftRow getPickRow(Team t, int pickTurn) {
		var teamGrid = teamLeft;
		if(this.team != t) teamGrid = teamRight;
		Log.format("DraftController getPickRow (%s vs %s) = %s + %s", team, t, teamGrid, pickTurn);
		for (Node node : teamGrid.getChildren()) {
			var row = GridPane.getRowIndex(node);
			if(row == null) row = 0;
			if(row == pickTurn) {
				return (DraftRow) node;
			}
		}
		return null;
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
	 * Coral handler for timer/turnstate message 
	 */
	@Subscribe
	public void receiveTimerMsg(ChangeTurn msg) {
		Coraline.lobby.turn(msg.turn);
		
		Platform.runLater(() -> {
			for (Node node : bansLeft.getChildren()) {
				node.getStyleClass().remove("selected");
			}
			for (Node node : bansRight.getChildren()) {
				node.getStyleClass().remove("selected");
			}
			
			if(Coraline.lobby.isBanPhase()) {
				var node = getBanColumn(Coraline.lobby.getTeamPlaying(), Coraline.lobby.getTeamBanIndex());
				node.getStyleClass().add("selected");
			} else {
				// var node = getPickRow(Coraline.lobby.getTeamPlaying(),
				// Coraline.lobby.getTeamPickIndex());
				// node.getStyleClass().add("selected");
			}
			
			this.lblTime.setText("" + msg.turn);
			
			if(timeline != null) {
				try {
					var keyframe = new KeyFrame(Duration.seconds(1), ae -> {
						var key = (KeyFrame) ae.getSource();
						lblTime.setText(msg.turn + " - " + key.getTime().toSeconds());
					});
					timeline.stop();
					timeline = new Timeline(keyframe);
					timeline.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	
	
	
}
