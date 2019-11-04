package gamemechanics.statics.stats;

public enum RangePattern {

	normal,
	line,
	diagonal,
	square,
	;
	
	public int val() {
		return (int) Math.pow(ordinal(), 2);
	}
	
	public static int pattern(RangePattern... patterns) {
		int tot = 0;
		for(var p : patterns) {
			tot |= p.val();
		}
		return tot;
	}
	
}
