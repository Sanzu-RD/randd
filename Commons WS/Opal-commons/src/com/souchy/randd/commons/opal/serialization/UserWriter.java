package com.souchy.randd.commons.opal.serialization;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.meta.User;

import io.netty.buffer.Unpooled;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class UserWriter implements MessageBodyWriter<User> {
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type == User.class;
	}
	@Override
	public long getSize(User user, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// deprecated by JAX-RS 2.0 and ignored by Jersey runtime
		return -1;
	}
	@Override
	public void writeTo(User user, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream stream) throws IOException, WebApplicationException {
		
		var buf = user.serialize(Unpooled.buffer());
		stream.write(buf.array());

		Log.info("UserWriter : " + user.toString());
	}
}