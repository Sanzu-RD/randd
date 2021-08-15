package com.souchy.randd.commons.diamond.models.stats.base;

import java.util.function.Function;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

//import data.new1.ecs.Component;

public class IntStat implements BBSerializer, BBDeserializer  { 
	
	/** flat value */
	public double baseflat;
	/** % increase */
	public double inc;
	/** increase flat after %inc */
	public double incflat;
	/** % more */
	public double more;
	/**
	 * fight modifier separates the current from the max/total value for resources
	 */
	public double fight;
	
	/** this overrides everything */
	public IntStat setter;
	
	
	
	public IntStat(double base) {
		this.baseflat = base;
	}
	public IntStat(double base, Function<Function<IntStat, Double>, Double> compounder) {
		this(base);
		this.compounder = compounder;
	}
	public IntStat(double base, double inc, double incflat, double more) {
		this.baseflat = base;
		this.inc = inc;
		this.incflat = incflat;
		this.more = more;
	}
	
	public int value() {
		return max() + (int) realFight();
	}
	
	public int max() {
		if(setter != null) return setter.value();
		var val = realBaseflat();
		val += val * (realInc() / 100.0);
		val += realIncFlat();
		val += val * (realMore() / 100.0);
		return (int) val;
	}
	
	private Function<Function<IntStat, Double>, Double> compounder;
	public double realBaseflat() {
		if(compounder != null)
			return compounder.apply(s -> s.baseflat);
		return baseflat;
	}
	public double realInc() {
		if(compounder != null)
			return compounder.apply(s -> s.inc);
		return inc;
	}
	public double realIncFlat() {
		if(compounder != null)
			return compounder.apply(s -> s.incflat);
		return incflat;
	}
	public double realMore() {
		if(compounder != null)
			return compounder.apply(s -> s.more);
		return more;
	}
	public double realFight() {
		if(compounder != null)
			return compounder.apply(s -> s.fight);
		return fight;
	}
	
	
	
	public IntStat copy() {
		var s = new IntStat(baseflat, compounder); //base);
		s.baseflat = baseflat;
		s.inc = inc;
		s.incflat = incflat;
		s.more = more;
		s.fight = fight;
		return s;
	}

	@Override
		public String toString() {
			return String.format("((%f + %f) * %f * %f) + %f = %d / %d", baseflat, inc, incflat, more, fight, value(), max());
		}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeDouble(baseflat);
		out.writeDouble(inc);
		out.writeDouble(incflat);
		out.writeDouble(more);
		out.writeDouble(fight);
		out.writeBoolean(setter != null);
		if(setter != null) {
			setter.serialize(out);
		}
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.baseflat = in.readDouble();
		this.inc = in.readDouble();
		this.incflat = in.readDouble();
		this.more = in.readDouble();
		this.fight = in.readDouble();
		boolean hasSetter = in.readBoolean();
		if(hasSetter) {
			this.setter = new IntStat(0);
			this.setter.deserialize(in);
		}
		return null;
	}
	
	public void add(IntStat s) {
		this.baseflat += s.baseflat;
		this.inc += s.inc;
		this.incflat += s.incflat;
		this.more += s.more;
		this.fight += s.fight;
	}
	
	public void remove(IntStat s) {
		this.baseflat -= s.baseflat;
		this.inc -= s.inc;
		this.incflat -= s.incflat;
		this.more -= s.more;
		this.fight -= s.fight;
	}
	
}
