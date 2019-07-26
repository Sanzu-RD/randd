package com.souchy.randd.ebishoal.amethyst.ui.tabs;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.opaline.api.Opaline;
import com.souchy.randd.jade.meta.New;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Home {

    @FXML public ImageView playImg;
    @FXML public HBox news;
    @FXML public AnchorPane new1;
    @FXML public ImageView new1_thumbnail;
    @FXML public Label new1_title;
    @FXML public AnchorPane new2;
    @FXML public ImageView new2_thumbnail;
    @FXML public Label new2_title;
    @FXML public AnchorPane new3;
    @FXML public ImageView new3_thumbnail;
    @FXML public Label new3_title;
    
    
	@FXML
    public void play(MouseEvent event) {
		// select a team, then queue for matchmaking
	}
	
	
	@FXML
	public void initialize() {
    	// Play btn
    	{
        	// set a clip to apply rounded border to the original image.
            Rectangle clip = new Rectangle(playImg.getFitWidth(), playImg.getFitHeight());
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            playImg.setClip(clip);
            // snapshot the rounded image.
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage image = playImg.snapshot(parameters, null);
            // remove the rounding clip so that our effect can show through.
            playImg.setClip(null);
            // apply a shadow effect.
            //playImg.setEffect(new DropShadow(20, Color.BLACK));
            // store the rounded image in the imageView.
            playImg.setImage(image);	
    	}
        
		// News
		{
			if(Opaline.isOnline()) {
				Log.info("Amethyst.Home.initialize news : ");
				List<HashMap<String, String>> news = Opaline.news.get();
				Log.info("Amethyst.Home.initialize news : " + news);
				if(news != null && news.size() >= 3) {
					System.out.println("a");
					new1.setOnMouseClicked(e -> browse(news.get(0).get(New.name_url)));
					new1_thumbnail.setImage(new Image(news.get(0).get(New.name_thumbnailUrl)));
					new1_title.setText(news.get(0).get(New.name_title));
					
					new2.setOnMouseClicked(e -> browse(news.get(1).get(New.name_url)));
					new2_thumbnail.setImage(new Image(news.get(1).get(New.name_thumbnailUrl)));
					new2_title.setText(news.get(1).get(New.name_title));
					
					new3.setOnMouseClicked(e -> browse(news.get(2).get(New.name_url)));
					new3_thumbnail.setImage(new Image(news.get(2).get(New.name_thumbnailUrl)));
					new3_title.setText(news.get(2).get(New.name_title));
				}
			}
			// } catch(Exception e) {
			// Log.info("Amethyst.Home.initialize news failed.");
			// }
    	}
	}

	
    public void browse(String url) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				Desktop.getDesktop().browse(new URI(url));
			} catch (IOException | URISyntaxException e1) {
				e1.printStackTrace();
			}
	    }
	
    }
    
	
}