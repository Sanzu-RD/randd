package com.souchy.randd.ebishoal.amethyst.ui.components;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ResourceBundle;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.coral.draft.ChangeTurn;
import com.souchy.randd.commons.diamond.ext.AssetData;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.CreatureModel;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.ebishoal.amethyst.main.Amethyst;
import com.souchy.randd.ebishoal.coraline.Coraline;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.jade.meta.JadeCreature;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class DraftRow extends GridPane {

	@FXML
	public Label name;
	
	@FXML
	public ImageView icon;
	
	@FXML
	public AnchorPane affinities;
	@FXML
	public FlowPane affinitiesList;

	@FXML
	public AnchorPane spells;
	@FXML
	public FlowPane spellsList;
	
	@FXML
	public Button btnPage;
	
//	public Label affinities;
	
	public int turn;
	
	/**
	 * Customization
	 */
	public JadeCreature creature = new JadeCreature();
	public CreatureModel model;

	public static DraftRow create() {
//		var r = Amethyst.app.<DraftRow>loadComponent("draftrow"); // new DraftRow();
		DraftRow r = new DraftRow();
        try {
            FXMLLoader loader = Amethyst.app.loader("draftrow");
            loader.setController(r);
			loader.setRoot(r);
			loader.load();
			Amethyst.core.bus.register(r);
        } catch (IOException exc) {
        	exc.printStackTrace();
        }
		return r;
	}
	
	public DraftRow() {
		try {
			Log.info("draftrow ctor");
//			Amethyst.app.loadComponent(this, "draftrow");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public DraftRow(int creatureModelId) {
//		this();
//		model = DiamondModels.creatures.get(creatureModelId);
//		creature.creatureModelID = creatureModelId;
//		creature.affinities = new int[Elements.values().length];
//		creature.spellIDs = new int[Constants.numberOfSpells];
//	}
	
	@FXML
	public void initialize() throws Exception {
		Log.info("draftrow init");
		this.name.setText("bambi");
	}
	
	public void clear() {
		this.icon.setImage(null);
		this.name.setText("");
	}
	
	public void init(int creatureModelId) {
		try {
			// get model
			model = DiamondModels.creatures.get(creatureModelId);
			// make creature
			creature.creatureModelID = creatureModelId;
			creature.affinities = new int[Elements.values().length];
			creature.spellIDs = new int[Constants.numberOfSpells];
			
			// get i18n name
			// Log.info("draft row initialize " + creatureModelId);
			try {
				ResourceBundle b = ResourceBundle.getBundle("../res/i18n/creatures/bundle");
				var namestr = b.getString("creature." + creatureModelId + ".name");
				// Log.info("DraftRow i18n name : " + namestr);
				this.name.setText(namestr);
			} catch (Exception e) {
				// Log.info("", e);
				if(name != null) this.name.setText("missing bundle");
			}
			// get icon
			var img = new Image(AssetData.creatures.get(creatureModelId).getIconURL().toString());
			this.icon.setImage(img);
			this.icon.setFitHeight(80);
			this.icon.setFitWidth(80);
			
			// refreshAffinities();
			// refreshSpells();
			for (var e : Elements.values()) {
				this.affinities.getChildren().add(new AffinityBox(model, creature, e).init());
			}
		} catch (Exception e) {
			Log.error("", e);
		}
	}

	/**
	 * Coral handler for timer/turnstate message 
	 */
	@Subscribe
	public void receiveTimerMsg(ChangeTurn msg) {
		Platform.runLater(() -> {
			if(msg.turn == this.turn) {
				this.getStyleClass().add("selected");
			} else {
				this.getStyleClass().remove("selected");
			}
		});
	}
	
	
//	public void setAffinity(Element e, int affinity) {
//		creature.affinities[e.val()] = affinity;
//		refreshAffinities();
//	}
	public void setSpell(int index, int id) {
		creature.spellIDs[index] = id;
		refreshSpells();
	}
	
//	private void refreshAffinities() {
//		// 2 rows
//		for(int i = 0; i < creature.affinities.length; i++) {
//			var node = new AffinityBox();
//			affinities.getChildren().add(node);
//		}
//		
////		var halfLength = creature.affinities.length / 2;
////		for (int r = 0; r < 2; r++) {
////			for (int i = r * halfLength; i < (r + 1) * halfLength; i++) {
////				if(r * halfLength + i < creature.affinities.length) {
////					affinities.addRow(r, new Label(String.valueOf(creature.affinities[r * halfLength + i])));
////				}
////			}
////		}
//	}
	
	private void refreshSpells() {
		this.spells.getChildren().clear();
		for(int i = 0; i < creature.spellIDs.length; i++) {
			var spellid = creature.spellIDs[i];
			if(spellid == 0) {
				this.spells.getChildren().add(new ImageView());
				continue;
			}
			var res = AssetData.spells.get(spellid);
			try {
				this.spells.getChildren().add(new ImageView(res.getIconPath().toUri().toURL().toString()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
