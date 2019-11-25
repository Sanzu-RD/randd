package com.souchy.randd.deathshadows.opal.api;

import javax.ws.rs.Path;

import com.souchy.randd.commons.opal.OpalCommons;

@Path(OpalCommons.encyclopedia)
public class Encyclopedia {
	
	public void get(int id) {
		
	}
	
	public void getCreatures() {
		
	}
	public void getSpells() {
		
	}
	public void getItems() {
		
	}
	
	/*
	 * 
	 * Thoughts on 2019-06-20
	 * 
	 * It woudl be cool to have all creatures, items, spells etc content available on the api,
	 *     but these are hardcoded in java.
	 * There is no text/json data to serve.
	 * Also it would be pretty heavy to send the data for all items at once.
	 * 
	 * 
	 * The client doesn't need this either. 
	 * It just has direct access to the classes for each entity/item.
	 * 
	 * 
	 * Maybe one day if we figure out a way to serialize this data in json or w/e.
	 * 1 solution could be text-based engine like PathOfExile but meh.
	 * 
	 * Actually,
	 * 	creatures would send : base stats, personal spells, description + portait image link.
	 * 	items would send : text description (effects as text) + image link.
	 *  spells would send : text description (effects as text) and the cost array + image link.
	 *  
	 * 
	 */
	
	
}
