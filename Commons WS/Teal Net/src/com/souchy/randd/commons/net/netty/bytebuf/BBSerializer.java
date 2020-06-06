package com.souchy.randd.commons.net.netty.bytebuf;

import java.util.List;
import java.util.function.BiConsumer;

import com.souchy.randd.commons.tealwaters.commons.Serializer;

import io.netty.buffer.ByteBuf;

public interface BBSerializer extends Serializer<ByteBuf, ByteBuf> {
	
	public default ByteBuf writeString(ByteBuf out, String s) {
		if(s == null) s = "";
		out.writeInt(s.length());
		out.writeBytes(s.getBytes());
		return out;
	}
	
	public default ByteBuf writeInt(ByteBuf out, int i) {
		out.writeInt(i);
		return out;
	}
	
	public default ByteBuf writeByte(ByteBuf out, byte b) {
		out.writeByte(b);
		return out;
	}
	
	public default ByteBuf writeBoo(ByteBuf out, boolean b) {
		out.writeBoolean(b);
		return out;
	}

	public default <T extends Enum<T>> ByteBuf writeEnum(ByteBuf out, Enum<T> e) {
		out.writeInt(e.ordinal());
		return out;
	}
	
	/** for lists of objects that implement Serializer */
	public default <T extends Serializer<ByteBuf, ?>> ByteBuf writeList(ByteBuf out, List<T> list){
		out.writeInt(list.size());
		for(var obj : list)
			obj.serialize(out);
		return out;
	}

	/* * for lists of objects that dont implement Serializer (ex: primitives, strings) */
	/*public default <T> ByteBuf writeList2(ByteBuf out, List<T> list, BiConsumer<ByteBuf, T> serializer){
		out.writeInt(list.size());
		for(var o : list)
			serializer.accept(out, o);
		return out;
	}*/

	/** for lists of objects that dont implement Serializer (ex: primitives, strings) */
	public default <T> ByteBuf writeList2(ByteBuf out, List<T> list, Serializer<T, ByteBuf> serializer){
		out.writeInt(list.size());
		for(var obj : list)
			serializer.serialize(obj);
			//serializer.accept(out, o);
		return out;
	}
	/** for lists of objects that should be written as strings (calls writeString(out, object.toString())); */
	public default <T> ByteBuf writeListString(ByteBuf out, List<T> list){
		out.writeInt(list.size());
		for(var obj : list)
			writeString(out, obj.toString());
		return out;
	}
	/** for lists of integers */
	public default ByteBuf writeListInt(ByteBuf out, List<Integer> list){
		out.writeInt(list.size());
		for(var obj : list)
			writeInt(out, obj);
		return out;
	}
	
	
	
	
}
