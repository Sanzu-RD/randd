package com.souchy.randd.commons.diamond.models.stats.base;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

public class ObjectStat<T extends BBSerializer /* & BBDeserializer */> implements BBSerializer { //, BBDeserializer  {
	public T base;
	public T setter;
	
	public ObjectStat(T t) {
		this.base = t;
	}
	
	public T value() {
		if(setter != null) return setter;
		return base;
	}

	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeBoolean(base != null);
		out.writeBoolean(setter != null);
		if(base != null) base.serialize(out);
		if(setter != null) setter.serialize(out);
		return out;
	}
	
	/*
	 * public static class ObjectStat2<T extends BBSerializer> implements
	 * BBSerializer { public T base; public T setter; public ObjectStat2(T t) {
	 * this.base = t; } public T value() { if(setter != null) return setter; return
	 * base; }
	 * 
	 * @Override public ByteBuf serialize(ByteBuf out) { out.writeBoolean(base !=
	 * null); out.writeBoolean(setter != null); if(base != null)
	 * base.serialize(out); if(setter != null) setter.serialize(out); return out; }
	 * }
	 */
	
	
//
//	@Override
//	public BBMessage deserialize(ByteBuf in) {
//		boolean hasbase = in.readBoolean();
//		boolean hassetter = in.readBoolean();
//		try {
//			if(hasbase) {
//				var ba = (T) base.getClass().getDeclaredConstructor().newInstance();
//				ba.deserialize(in);
//			}
//			if(hassetter) {
//				var se = (T) setter.getClass().getDeclaredConstructor().newInstance();
//				se.deserialize(in);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	
}
