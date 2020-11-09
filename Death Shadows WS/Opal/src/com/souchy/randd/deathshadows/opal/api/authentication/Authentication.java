package com.souchy.randd.deathshadows.opal.api.authentication;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.Map;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.souchy.randd.commons.opal.IAuthentication;
import com.souchy.randd.commons.opal.OpalCommons;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.User;

@Path(OpalCommons.authentication)
//@XmlType(name="")
public class Authentication implements IAuthentication {

	// Note: you could even inject this as a method parameter
	@Context public HttpServletRequest request;
	@Context UriInfo ui;
	@Context HttpHeaders hh;
	@Context SecurityContext ctx;
	
	public Authentication() {
		Log.info("Opal.Authentication");
		if(ui != null) {
		    MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
		    MultivaluedMap<String, String> pathParams = ui.getPathParameters();
			Log.info("Opal.Authentication : queryParams = " + queryParams);
			Log.info("Opal.Authentication : pathParams = " + pathParams);
		}
		if(hh != null) {
		    MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
		    Map<String, Cookie> pathParams2 = hh.getCookies();
			Log.info("Opal.Authentication : headerParams = " + headerParams);
			Log.info("Opal.Authentication : pathParams2 = " + pathParams2);
		}
	}
	
	@GET
	@Path("signin/{token}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed(UserLevel.name_anonymous)
	@PermitAll
	@Override
	public User signin(@PathParam("token") String token) { // LoginToken token) {
		Log.info("Opal.signin : token = " + token);
		try {
			var data = token.split("@", 2);
			var username = data[0];
			var hashedpass = data[1];
			var user = getUserEmerald(username, hashedpass); // token.username, token.hashedPassword);
			Log.format("Opal.signin : user %s", user);
			return user;
		} catch (Exception e) {
			Log.info("", e);
			return null;
		}
	}
	
	@POST
	@Path("signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed(UserLevel.name_anonymous)
	@PermitAll
	@Override
	public User signup(RegistrationToken token) {
		Log.info("Opal.signup : token = " + token);

	    MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
	    MultivaluedMap<String, String> pathParams = ui.getPathParameters();
	    MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
	    Map<String, Cookie> pathParams2 = hh.getCookies();
		Log.info("Opal.signup : queryParams = " + queryParams);
		Log.info("Opal.signup : pathParams = " + pathParams);
		Log.info("Opal.signup : headerParams = " + headerParams);
		Log.info("Opal.signup : pathParams2 = " + pathParams2);
		
		// user already exists
		if(getUserEmerald(token.username, token.password) != null) {
			Log.info("Opal.signup : user already exists.");
			return null;
		}
		User user = new User();
		user.pseudo = token.pseudo;
		user.username = token.username;
		user.password = token.password;
		user.salt = token.salt;
		user.email = token.email;
		try {
			Emerald.users().insertOne(user);
			Log.info("Opal.signup : user inserted.");
			return user;
		} catch (Exception e) {
			Log.info("Opal.signup : user creation failed.", e);
			return null;
		}
	}
	
	@GET
	@Path("salt/{username}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	//@RolesAllowed(UserLevel.name_anonymous)
	@PermitAll
	@Override
	public String getSalt(@PathParam("username") String username) {
		Log.info("Opal.getSalt : username = " + username);
		User user = Emerald.users().find(eq(User.name_username, username)).first();
		if(user != null) {
			return user.salt;
		}
		return null;
	}
	
	@POST
	@Path("forgotWemail/{email}")
	@Consumes(MediaType.TEXT_PLAIN)
	//@RolesAllowed(UserLevel.name_anonymous)
	@PermitAll
	@Override
	public void forgotAccountFromEmail(@PathParam("email") String email) {
		
	}
	
	@POST
	@Path("forgotWphone/{phoneNumber}")
	@Consumes(MediaType.TEXT_PLAIN)
	//@RolesAllowed(UserLevel.name_anonymous)
	@PermitAll
	@Override
	public void forgotAccountFromPhone(@PathParam("phoneNumber") String phoneNumber) {
		
	}
	
	private User getUserEmerald(String username, String hashedPassword) {
		return Emerald.users().find(
				and(
					eq(User.name_username, username), 
					eq(User.name_password, hashedPassword)
				)
			).first();
	}

	/*

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String index() {
		return "hello";
	}

    //@RolesAllowed("User")
	@GET
	@Path("get")
	// @Consumes(value = "")
	@Produces(MediaType.TEXT_PLAIN)
	public String authenticate(
			@DefaultValue("") @QueryParam("user") String username, 
			@DefaultValue("") @QueryParam("pass") String password, 
			@Context UriInfo uriInfo, @Context HttpHeaders hh, @Context Request re, @Context SecurityContext sc) {
		
		System.out.println(username + ":" + password);
		System.out.println(uriInfo.getPath());
		System.out.println(hh.getCookies());
		hh.getCookies().put("hello", new Cookie("hello", "world"));

		if(username.isBlank() || password.isBlank()) return "empty username or password";
		try {
			User user = Emerald.users().find(and(eq(User.name_username, username), eq(User.name_password, password))).first(); 
			if(user != null) {
				// request.getSession(true);
				// Set the session attributes as you wish
				return "success";
			}else 
				return "doesnt exist";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "this is very bad";
	}

	@GET
	@Path("get-2")
	@Produces("text/plain")
	public String hello(@Context HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		Object foo = session.getAttribute("foo");
		if(foo != null) {
			System.out.println(foo.toString());
		} else {
			foo = "bar";
			session.setAttribute("foo", "bar");
		}
		return foo.toString();
    }
	
	public void createUser() {
		User u = new User();
		u.username = "robyn";
		u.password = "z";
		u.pseudo = "Souchy";
		u.email = "music_inme@hotmail.fr";
		Emerald.users().insertOne(u);
	}
	*/
	
}
