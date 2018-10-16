package com.souchy.randd.jade.combat;

import com.google.common.collect.Table;

public interface Board {
	
	public Table<Integer, Integer, Cell> getCells();

}
