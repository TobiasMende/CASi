package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.util.Collection;
import java.util.Vector;

public class Room implements IPosition{
	
	private static final long serialVersionUID = 112593179870431369L;
	private Collection<Wall> walls = new Vector<Wall>();
	
	@Override
	public Point getCoordinates() {
		// TODO Auto-generated method stub
		return null;
	}


}
