package com.souchy.randd.deathshadows.opal.api.data;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.opal.OpalCommons;
import com.souchy.randd.jade.meta.UserLevel;


@Path(OpalCommons.shop)
public class Shop {
	
	@Context
	private SecurityContext ctx;
	
	
	@POST
    @RolesAllowed(UserLevel.name_user)
	//@Path("buy/{id}")
	public void buy(ObjectId id) {
		
	}
	
	
	
}
