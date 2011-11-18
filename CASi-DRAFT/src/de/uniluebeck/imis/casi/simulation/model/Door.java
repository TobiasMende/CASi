package de.uniluebeck.imis.casi.simulation.model;

public class Door extends AbstractComponent{
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
	
	
	
	

}
