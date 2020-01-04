package com.souchy.randd.deathshadows.opal.api.data;

import static com.mongodb.client.model.Filters.eq;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.opal.OpalCommons;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.New;

@Path(OpalCommons.collection)
public class Collection {
	

	// todo
	@GET
	@Path("{id}")
	public New get(@Context SecurityContext ctx) {
		ObjectId id = null;
		return Emerald.news().find(eq(New.name__id, id)).first();
	}
	
}
