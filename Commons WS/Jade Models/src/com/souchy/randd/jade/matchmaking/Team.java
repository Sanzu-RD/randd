package com.souchy.randd.jade.matchmaking;

public enum Team {
	A,
	B,
	/** C is Neutral */
//	C,
	
	;
	
	public Team inverse() {
		if(this == A) return B;
		else return A;
	}
	
}