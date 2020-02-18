package gamemechanics.data.status;

import java.util.List;

import data.new1.timed.Status;
import gamemechanics.events.OnEnterCell;
import gamemechanics.events.OnEnterCell.OnEnterCellHandler;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public abstract class Trap extends Status implements OnEnterCellHandler {

	public List<Cell> cells;
	
	public Trap(Entity source, Entity target) {
		super(source, target);
		// duration ?
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onEnterCell(OnEnterCell event) {
		// apply effects on all cells + remove status on all cells
		if(cells.contains(event.cell)) {
			cells.forEach(target -> {
//				effects.forEach(e -> {
//					if(e instanceof CellEffect) e.apply(this.source, event.cell);
//					if(e instanceof CreatureEffect) e.apply(this.source, event.creature);
//				});
				
				// TODO apply effects
				apply(target);
				
				target.getStatus().remove(this);
			});
		}
	}
	
	public abstract void apply(Cell target);
	
	
	@Override
	public boolean fuse(Status s) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
