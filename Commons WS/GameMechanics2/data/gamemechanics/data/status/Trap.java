package gamemechanics.data.status;

import java.util.List;

import gamemechanics.events.OnEnterCell;
import gamemechanics.events.OnEnterCell.OnEnterCellHandler;
import gamemechanics.models.Effect.CellEffect;
import gamemechanics.models.Effect.CreatureEffect;
import gamemechanics.models.entities.Cell;
import gamemechanics.status.Status;

public abstract class Trap extends Status implements OnEnterCellHandler {

	public List<Cell> cells;
	
	public Trap() {
		// duration ?
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnterCell(OnEnterCell event) {
		// apply effects on all cells + remove status on all cells
		if(cells.contains(event.cell)) {
			cells.forEach(c -> {
				effects.forEach(e -> {
					if(e instanceof CellEffect) e.apply(this.source, event.cell);
					if(e instanceof CreatureEffect) e.apply(this.source, event.creature);
				});
				c.getStatus().remove(this);
			});
		}
	}
	
	
	@Override
	public void fuse(Status s) {
		// TODO Auto-generated method stub
		
	}

	
}
