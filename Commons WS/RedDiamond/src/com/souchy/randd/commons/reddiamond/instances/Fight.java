package com.souchy.randd.commons.reddiamond.instances;


import java.util.List;

import com.souchy.randd.commons.reddiamond.stats.HistoryStats;

public class Fight extends Entity {
	
	public HistoryStats history;
	
	
	public List<Creature> creatures;
	public Board board;
	
	
	public int getRound() {
		return 0;
	}
	public int getTurn() {
		return 0;
	}
	
}
