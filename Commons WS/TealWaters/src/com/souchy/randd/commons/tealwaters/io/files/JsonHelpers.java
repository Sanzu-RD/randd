package com.souchy.randd.commons.tealwaters.io.files;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZonedDateTime;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonHelpers {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Exclude {
		
	}

	public static final ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
	    @Override
	    public boolean shouldSkipClass(Class<?> clazz) {
	        return false;
	    }
	 
	    @Override
	    public boolean shouldSkipField(FieldAttributes field) {
	        return field.getAnnotation(Exclude.class) != null;
	    }
	};
	
	public static class ZonedDateTimeAdapter implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {
		@Override
		public ZonedDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString());
		}
		@Override
		public JsonElement serialize(ZonedDateTime src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}
	public static class InstantAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
		@Override
		public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return Instant.parse(json.getAsJsonPrimitive().getAsString());
		}
		@Override
		public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}
	
}
