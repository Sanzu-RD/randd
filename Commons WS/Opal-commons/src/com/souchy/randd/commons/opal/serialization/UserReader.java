package com.souchy.randd.commons.opal.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.meta.User;

import io.netty.buffer.Unpooled;

public class UserReader implements MessageBodyReader<User> {
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	    return type == User.class;
	}
	@Override
	public User readFrom(Class<User> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream stream) throws IOException, WebApplicationException {
		
		var bytes = stream.readAllBytes();
		var in = Unpooled.wrappedBuffer(bytes);
		var user = new User();
		user.deserialize(in);

		Log.info("UserReader : " + user.toString());
		
		return user;
	}
	
}