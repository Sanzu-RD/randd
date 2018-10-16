package com.souchy.randd.jade.api;

import com.google.common.collect.Table;

public interface IBoard {

	public Table<Integer, Integer, ICell> getCells();
	
}
