package com.souchy.randd.deathshadows.opal.api;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.jade.meta.UserLevel;

@Provider
@Priority(Priorities.AUTHENTICATION)  // needs to happen before authorization
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;
    
    
	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {
		Method method = resourceInfo.getResourceMethod();
		Log.info("AuthenticationFilter.filter : method = " + method);
		
		// If access is denied for all
		if(method.isAnnotationPresent(DenyAll.class)) {
			Log.info("AuthenticationFilter.filter : DenyAll.");
			ctx.abortWith(Response.status(Response.Status.FORBIDDEN).entity("Access blocked for all users !!").build());
			return;
		}
		
		// If access is not allowed for all
		if(!method.isAnnotationPresent(PermitAll.class)) {
			Log.info("AuthenticationFilter.filter : restricted.");
			String authHeader = ctx.getHeaderString(HttpHeaders.AUTHORIZATION); // (1)
			if(authHeader == null || authHeader.trim().isEmpty()) {
				ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid authorization header.").build());
				return;
			}

			String authzHeader = ctx.getHeaderString(HttpHeaders.AUTHORIZATION); // (1)
	        String decoded = Base64.decodeAsString(authzHeader);

	        String[] split = decoded.split(":");
	        String username = split[0];
	        String password = split[1];
			User user = Emerald.users().find(and(eq(User.name_username, username), eq(User.name_password, password))).first(); // (2)

			if(user == null) { // (3)
				Log.info("AuthenticationFilter.filter : restricted with a null user.");
				//throw new IOException(); // UnauthorizedException();
				ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User does not exist").build());
				return;
			}
	        SecurityContext oldContext = ctx.getSecurityContext();               // (4)
	        ctx.setSecurityContext(new BasicSecurityContext(user, oldContext.isSecure()));
		} else {
			Log.info("AuthenticationFilter.filter : PermitAll.");
		}
		//ctx.getSecurityContext().isUserInRole(role)
		//method.invoke(obj, args);
		//resourceInfo.
		// Otherwise access is allowed for all
	}
	
	
	public static class BasicSecurityContext implements SecurityContext {
		   private final User user;
		   private final boolean secure;
		   public BasicSecurityContext(User user, boolean secure) {
		       this.user = user;
		       this.secure = secure;
		   }
		   @Override
		   public Principal getUserPrincipal() {
		       return new Principal() {
		           @Override
		           public String getName() {
		                return user.username; //.getUsername();
		           }
		       };
		   }
		   @Override
		   public String getAuthenticationScheme() {
		       return SecurityContext.BASIC_AUTH;
		   }
		   @Override
		   public boolean isSecure() { return secure; }

		   @Override
		   public boolean isUserInRole(String role) {
		       return user.level.ordinal() >= UserLevel.valueOf(role).ordinal(); //.getRoles().contains(role);
		   }
		}
	
}