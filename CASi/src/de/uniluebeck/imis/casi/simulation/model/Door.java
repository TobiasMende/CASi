package de.uniluebeck.imis.casi.simulation.model;

/**
 * 
 * 
 * @author Tobias Mende
 *
 */
public class Door extends AbstractComponent{
	public static final int DEFAULT_DOOR_SIZE = 1;
	// the default offset is -1, so the Wall takes care of placing the door centered!
	public static final int DEFAULT_DOOR_OFFSET = -1;
	private static int id;
	private int offset;
	private int size;
	
	private Door(String identifier) {
		super(identifier);
	}

	public int getOffset() {
		return offset;
	}

	public int getSize() {
		return size;
	}

	
	public Door(int offset, int size) {
		this("door-"+id);
		id++;
		this.offset = offset;
		this.size = size;
	}
	
	public Door() {
		this("door-"+id);
		id++;
		this.offset = DEFAULT_DOOR_OFFSET;
		this.size = DEFAULT_DOOR_SIZE;
	}
	
	

}
