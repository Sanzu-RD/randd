package com.souchy.randd.ebishoal.amethyst.ui.components;

import java.util.List;

import javafx.scene.input.MouseEvent;

public class TeamSelector {
	
	public List<Object> teams;
	
	
	public void onSelect(MouseEvent e) {
		Object team = e.getTarget();
		
		// Amethyst.selectedTeam = team;
		// this.close();
		
		//return team;
	}
	
	
}
