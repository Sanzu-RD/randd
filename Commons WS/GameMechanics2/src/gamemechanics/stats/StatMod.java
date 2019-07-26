package gamemechanics.stats;

public class StatMod {
	
	public StatProperty prop;
	public int mods;
	public double value;
	
	public StatMod(double value, StatProperty prop, Modifier... mods) {
		this.prop = prop;
		this.value = value;
		this.mods = Modifier.compile(mods);
	}
	
}
