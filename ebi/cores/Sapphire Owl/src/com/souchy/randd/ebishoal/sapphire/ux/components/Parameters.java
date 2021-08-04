package com.souchy.randd.ebishoal.sapphire.ux.components;

import java.util.function.Consumer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.FocusManager;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.souchy.randd.commons.tealwaters.commons.Lambda;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.lapis.util.DragAndResizeListener;
import com.souchy.randd.ebishoal.commons.lapis.util.LapisUtil;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireComponent;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireHudSkin;
import com.souchy.randd.ebishoal.sapphire.ux.SapphireLmlParser;

public class Parameters extends SapphireComponent {

	@LmlActor("window")
	public Table window;
	@LmlActor("scrollpane")
	public ScrollPane scrollpane;
	@LmlActor("content")
	public Table content;
	
	
	@LmlActor("general")
	public Table general;
	@LmlActor("functionality")
	public Table functionality;
	@LmlActor("ui")
	public Table ui;
	@LmlActor("gfx")
	public Table gfx;
	@LmlActor("sound")
	public Table sound;
	@LmlActor("shortcut")
	public Table shortcut;
	
	
	@LmlActor("general.locale.value")
	public SelectBox<String> localeSelectBox;
	
	
	@Override
	protected void onInit() {
//		this.addListener(new DragAndResizeListener(this));
		
		this.align(Align.center);
		this.getCells().get(0).prefWidth(getStage().getWidth() * 0.5f); // parameters.
		this.getCells().get(0).prefHeight(getStage().getHeight() * 0.8f); // parameters.
//		this.getCells().get(0).align(Align.center);
		
		/*
		// automatic ui fields generation
		for (var field : SapphireOwl.conf.functionality.getClass().getDeclaredFields()) {
			try {
				// <label id="functionality.grid" value="@i18n.functionality.grid"/>
				// <checkbox id="functionality.grid.value" checked="#prefs.functionality.grid"
				// onchange="$onChangeVal"/>
				var name = "functionality." + field.getName();
				
				var lbl = new Label("@i18n." + name, VisUI.getSkin());
				lbl.setName(name);
				
				var box = new CheckBox("", VisUI.getSkin());
				box.setName(name + ".value");
				box.setChecked(field.getBoolean(SapphireOwl.conf.functionality));
				
				functionality.add(lbl);
				functionality.add(box);
				functionality.row();
			} catch (Exception e) {
				Log.error("", e);
			}
		}
		*/
		
		// focus on hover
		LapisUtil.onHover(scrollpane, this::focusScroll, this::unfocusScroll);
		// focus on click
		LapisUtil.onClick(window, this::focusScroll);

		extras();
	}
	
	
	private void extras() {
		var locale = SapphireOwl.conf.general.locale; 
		localeSelectBox.setItems("fr", "en");
		localeSelectBox.setSelected(locale);
		localeSelectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				SapphireLmlParser.init();
				SapphireGame.gfx.hud.reload();
				SapphireGame.gfx.hud.parameters.toggleVisibility();
			}
		});

		Log.info("Parameters set close btn");

//		VisImageButton closeButton = (VisImageButton) window.getTitleTable().getCells().get(1).getActor();
//		closeButton.addListener(new ChangeListener() {
//			@Override
//			public void changed (ChangeEvent event, Actor actor) {
////				Parameters.this.remove();
////				SapphireGame.gfx.hud.parameters = null;
//				Log.info("Parameters close btn");
//				close();
//			}
//		});
//		closeButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				//super.clicked(event, x, y);
//				event.handle();
//				Log.info("Parameters close btn21");
//				close();
//			}
//		});
		
//		this.findActor("functionality.showCursorPos.value").addListener(new ch)
	}
	
	public void btnClose() {
		Log.info("Parameters close btn21");
		close();
	}
	
	
	private void focusScroll() {
		SapphireGame.gfx.hud.getStage().setScrollFocus(scrollpane);
	}
	
	private void unfocusScroll (){
		SapphireGame.gfx.hud.getStage().setScrollFocus(null);
	}
	
	public void close() {
		unfocusScroll();
//		window.fadeOut();
//		Parameters.this.remove();
//		SapphireGame.gfx.hud.parameters = null;
//		dispose();

		SapphireGame.gfx.hud.parameters.setVisible(false);
	}
	
	public void open() {
		SapphireGame.gfx.hud.parameters.setVisible(true);
	}
	
	public void foreachCell(Table group, Consumer<Cell<?>> dothing) {
		group.getCells().forEach(c -> {
			if(c.getActor() instanceof Table) foreachCell((Table) c.getActor(), dothing);
			dothing.accept(c);
		});
	}

	@Override
	public String getTemplateId() {
		return "parameters";
	}
	
	@LmlAction("concede")
	public void concede() {
		
	}
	
	@LmlAction("toggleVisibility")
	public void toggleVisibility() {
		setVisible(!isVisible());
		if(this.isVisible()) {
			focusScroll();
		} else {
			unfocusScroll();
		}
	}


	/**
	 * On any registered change value event
	 */
	@SuppressWarnings("preview")
	@LmlAction("onChangeVal")
	public void onChangeVal(Object obj) {
		var actor = (Actor) obj;
		if(actor instanceof Slider) {
			Slider slider = (Slider) actor;
			Log.info("onChange Slider " + slider.getName() + " = " + (int) slider.getValue());
			SapphireOwl.conf.setPref(slider.getName(), (int) slider.getValue());
		} else if(actor instanceof VisCheckBox) {
			VisCheckBox field = (VisCheckBox) actor;
			SapphireOwl.conf.setPref(field.getName(), field.isChecked());
			Log.info("onChange CheckBox " + field.getName() + " = " + field.isChecked());
		} else if(actor instanceof TextField) {
			TextField field = (TextField) actor;
			SapphireOwl.conf.setPref(field.getName(), field.getText());
			Log.info("onChange TextField " + field.getName() + " = " + field.getText());
		} else if(actor instanceof SelectBox) {
			SelectBox field = (SelectBox) actor;
			SapphireOwl.conf.setPref(field.getName(), field.getSelected());
			Log.info("onChange SelectBox " + field.getName() + " = " + field.getSelected());
		} else {
			Log.info("onChange : " + actor + " is " + actor.getClass());
		}
		SapphireOwl.conf.save();
	}

	@Override
	public void resizeScreen(int w, int h, boolean centerCam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
