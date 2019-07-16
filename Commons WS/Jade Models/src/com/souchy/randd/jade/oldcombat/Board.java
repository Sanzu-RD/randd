package com.souchy.randd.jade.oldcombat;

import com.google.common.collect.Table;

public interface Board {
	
	public Table<Integer, Integer, Cell> getCells();

}
