package com.souchy.randd.deathshadows.opal.api;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.opal.OpalCommons;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.New;

@SuppressWarnings("unused")
@Path(OpalCommons.news)
public class News {
	
	static {
		if(false)
	        for(int i = 0; i < 4; i++) {
	            New a = new New();
	            a.url = "http://localhost:8080/news/"+i;
	            a.thumbnailUrl = "file:/G:/Assets/pack/fantasy%20bundle/rpgclassbadges/Badges_png/Badge_mage.png";
	            a.title = "Hi " + i;
	            a.content = "Some content for our first news. \n This is a new line.";
	            Emerald.news().insertOne(a);
	        }
	}
	
	
	@GET
	@Path("id={id}")
	@PermitAll
	public New get(@PathParam("id") String id, @Context UriInfo ui, @Context HttpHeaders hh, @Context SecurityContext ctx) {
		Log.info("Opal.News.get : id = " + id);
		
	    MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
	    MultivaluedMap<String, String> pathParams = ui.getPathParameters();
	    MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
	    Map<String, Cookie> pathParams2 = hh.getCookies();
		Log.info("Opal.News.get : queryParams = " + queryParams);
		Log.info("Opal.News.get : pathParams = " + pathParams);
		Log.info("Opal.News.get : headerParams = " + headerParams);
		Log.info("Opal.News.get : pathParams2 = " + pathParams2);
	    
		ObjectId _id = new ObjectId(id);
		var result = Emerald.news().find(eq(New.name__id, _id)).first();
		Log.info("Opal.News.get = " + result);
		return result;
	}

	@GET
	@Path("all")
	@PermitAll
	public List<New> getAll(){
		Log.info("Opal.News.getAll");
		var result = Emerald.news().find().into(new ArrayList<>());
		Log.info("Opal.News.getAll = " + result);
		return result;
	}
	
}
