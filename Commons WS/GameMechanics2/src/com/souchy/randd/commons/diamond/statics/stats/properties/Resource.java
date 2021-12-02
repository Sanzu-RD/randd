package com.souchy.randd.commons.diamond.statics.stats.properties;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import com.souchy.randd.commons.diamond.statics.stats.properties.StatProperty.StatPropertyID;

public enum Resource { //implements StatProperty {

	life, 
	mana, 
	move, 
	special;
	
//	private Resource() {
//		StatPropertyID.register(this);
//	}
	
	
	public static class ResourceCodec implements Codec<Resource> {
		@Override
		public void encode(BsonWriter writer, Resource value, EncoderContext encoderContext) {
			writer.writeString(value.name());
		}
		@Override
		public Class<Resource> getEncoderClass() {
			return Resource.class;
		}
		@Override
		public Resource decode(BsonReader reader, DecoderContext decoderContext) {
			return Resource.valueOf(reader.readString());
		}
	}
	
}