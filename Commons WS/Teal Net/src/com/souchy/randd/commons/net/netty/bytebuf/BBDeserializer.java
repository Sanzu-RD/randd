package com.souchy.randd.commons.net.netty.bytebuf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

public interface BBDeserializer extends Deserializer<ByteBuf, BBMessage> {
	
	public default String readString(ByteBuf in) {
		// read string
		var bytes = new byte[in.readInt()];
		in.readBytes(bytes);
		return new String(bytes);
	}
	
	public default int readInt(ByteBuf in) {
		return in.readInt();
	}
	
	public default byte readByte(ByteBuf in) {
		return in.readByte();
	}
	
	public default boolean readBool(ByteBuf in) {
		return in.readBoolean();
	}

	public default <T extends Enum<T>> Enum<T> readEnum(ByteBuf out, Class<T> e) {
		return e.getEnumConstants()[out.readInt()];
	}

	public default <T> List<T> readList(ByteBuf in, Supplier<T> deserializer) {
		var list = new ArrayList<T>();
		var l = in.readInt();
		while(l-- > 0)
			list.add(deserializer.get()); //list.add(deserializer.deserialize(in));
		return list;
	}
	
	
	
}
