package gamemechanics.models;

import data.new1.timed.Status;

/**
 * Sert seulement à emmagasiner dans DiamondModels et utiliser le .create() pour créer des instances en général et quand on désérialize.
 * 
 * @author Blank
 * @date 23 mai 2020
 */
public abstract class StatusModel {
	/**
	 * status model id
	 */
	public abstract int modelID();
	/**
	 * Create an instance of the status implementation for deserialisation
	 */
	public abstract Status create(Fight f, int sourceid, int targetid);
	
	public StatusModel() {
		
	}
}