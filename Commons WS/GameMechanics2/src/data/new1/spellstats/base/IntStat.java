package data.new1.spellstats.base;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

//import data.new1.ecs.Component;

public class IntStat implements BBSerializer, BBDeserializer  { //implements Component {
	
	/** flat value */
	//public final double base;
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
	
	public int value() {
		return max() + (int) fight;
		// val += fight;
		// return (int) val;
	}
	
	public int max() {
		if(setter != null) return setter.value();
		var val = baseflat;
		val *= (100d + inc / 100d);
		val += incflat;
		val *= (100d + more / 100d);
		return (int) val;
	}
	
	public IntStat copy() {
		var s = new IntStat(baseflat); //base);
		s.baseflat = baseflat;
		s.inc = inc;
		s.incflat = incflat;
		s.more = more;
		s.fight = fight;
		return s;
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
	
}
