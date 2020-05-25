package gamemechanics.models.entities;

import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import gamemechanics.common.generic.Vector2;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.Fight;
import io.netty.buffer.ByteBuf;

public class Cell extends Entity {
	
	// main creature on the cell (ex si elle porte ou mange un autre creature (ex tahm kench/pandawa))
	public List<Creature> creatures;
	
	
	public Cell(Fight f, int x, int y) {
		super(f);
		this.pos = new Vector2(x, y);
	}
	
	
	public boolean hasCreature() {
		return !getCreatures().isEmpty();
	}
	public List<Creature> getCreatures() {
//		for(Creature c : fight.timeline) {
//			if(c.getCell() == this) return c;
//		}
//		return null;
		return creatures;
	}
	
	
}