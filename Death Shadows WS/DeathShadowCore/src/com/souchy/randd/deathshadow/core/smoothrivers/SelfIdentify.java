package com.souchy.randd.deathshadow.core.smoothrivers;

import java.lang.management.ManagementFactory;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.sun.management.OperatingSystemMXBean;

import io.netty.buffer.ByteBuf;

@ID(id = 1001)
public class SelfIdentify implements BBMessage {
	
	public int port = 0;
	public Class<? extends DeathShadowCore> clazz = null;
	
	public long pid = 0;
	public double cpu = 0;
	public long ram = 0;
	
	public SelfIdentify() {}
	public SelfIdentify(DeathShadowCore core) {
		this.port = core.port;
		this.clazz = core.getClass();
//		ManagementFactory.getRuntimeMXBean().getName();
		
		this.pid = ProcessHandle.current().pid();
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		// What % CPU load this current JVM is taking, from 0.0-1.0
		this.cpu = osBean.getProcessCpuLoad();
		this.ram = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(port);
	    writeString(out, clazz.getCanonicalName());
	    out.writeLong(pid);
	    out.writeDouble(cpu);
	    out.writeLong(ram);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		port = in.readInt();
		try {
			clazz = (Class<? extends DeathShadowCore>) Class.forName(readString(in));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			pid = in.readLong();
			cpu = in.readDouble();
			ram = in.readLong();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new SelfIdentify();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
	@Override
	public String toString() {
		return String.join(", ", String.valueOf(port), clazz.getCanonicalName());
	}

}
