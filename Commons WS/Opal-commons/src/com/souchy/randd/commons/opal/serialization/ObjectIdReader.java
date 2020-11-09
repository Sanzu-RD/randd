package com.souchy.randd.commons.opal.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.tealwaters.logging.Log;

public class ObjectIdReader implements MessageBodyReader<ObjectId> {
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
	    return type == ObjectId.class;
	}
	@Override
	public ObjectId readFrom(Class<ObjectId> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
		var bytes = new byte[entityStream.read()];
		entityStream.read(bytes);
		var str = new String(bytes);
		Log.info("ObjectIdReader : " + str);
		return new ObjectId(str);
	}
}