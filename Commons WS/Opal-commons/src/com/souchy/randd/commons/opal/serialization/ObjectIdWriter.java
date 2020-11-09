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

import org.bson.types.ObjectId;

import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.buffer.Unpooled;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ObjectIdWriter implements MessageBodyWriter<ObjectId> {
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type == ObjectId.class;
	}
	@Override
	public long getSize(ObjectId id, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// deprecated by JAX-RS 2.0 and ignored by Jersey runtime
		return -1;
	}
	@Override
	public void writeTo(ObjectId id, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream stream) throws IOException, WebApplicationException {
		
		var s = id.toString();
		stream.write(s.length());
		stream.write(s.getBytes());
		
		Log.info("ObjectIdWriter : " + id.toString());
	}
}