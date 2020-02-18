package gamemechanics.statics.filters;

import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Tables;
import com.souchy.randd.commons.tealwaters.logging.Log;

public enum AoePattern {
	
	Single(new int[][] { { 1 } }),
	
	H2(new int[][] { { 1, 1 } }),
	
	V2(new int[][] { { 1 }, { 1 }, }), V3(new int[][] { { 1 }, { 1 }, { 1 }, }), V4(new int[][] { { 1 }, { 1 }, { 1 }, { 1 }, }),
	
	Square3(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }),
	
	;
	
	public final int[][] pattern;
	
	private AoePattern(int[][] pattern) {
		this.pattern = pattern;
	}
	
}
