package com.souchy.randd.commons.diamond.statics;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.StatProperty;
import com.souchy.randd.commons.diamond.statics.stats.properties.StatProperty.StatPropertyID;

public interface Element extends StatProperty {
	
	public static List<Element> values = new ArrayList<>();
	
	/**
	 * Global affinity
	 */
	public static Element global = new Element() {
		{
			StatPropertyID.register(this);
			values.add(this);
		}
		
		public String name() {
			return "global";
		}
	};
	public String name();
	
	public static int count() {
		return values.size();
	}
	
	
	public static class ElementCodec implements Codec<Element> {
		@Override
		public void encode(BsonWriter writer, Element value, EncoderContext encoderContext) {
			writer.writeString(value.name());
		}
		@Override
		public Class<Element> getEncoderClass() {
			return Element.class;
		}
		@Override
		public Element decode(BsonReader reader, DecoderContext decoderContext) {
			var str = reader.readString();
			for(var e : Element.values)
				if(e.name().equals(str))
					return e;
			return null;
		}
	}
	
}
