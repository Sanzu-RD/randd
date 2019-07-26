package gamemechanics.properties;


public class Properties {
	
	private boolean isVisible = true;

	private Orientation orientation = Orientation.North;

	private boolean[] targetability = new boolean[8];
	
	
	public boolean can(Targetability targetting) {
		return targetability[targetting.ordinal()];
	}
	public void setCan(Targetability targetting, boolean should) {
		targetability[targetting.ordinal()] = should;
	}
	
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
}
