package com.souchy.randd.deathshadows.opal.api;

import static com.mongodb.client.model.Filters.eq;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.opal.OpalCommons;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.Deck;

@Path(OpalCommons.decks)
public class Decks {
	
	@GET
	@Path("{id}")
	public Deck get() {
		ObjectId id = null;
		return Emerald.decks().find(eq(Deck.name__id, id)).first();
	}

}
