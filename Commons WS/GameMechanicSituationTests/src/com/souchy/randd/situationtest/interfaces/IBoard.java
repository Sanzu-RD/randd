package com.souchy.randd.situationtest.interfaces;

import com.google.common.collect.Table;

public interface IBoard {

	public Table<Integer, Integer, ICell> getCells();
	
}
