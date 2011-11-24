package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.Collection;
import java.util.Vector;

/**
 * This class is a presentation of rooms in the simulation. Furthermore it
 * provides additional functionalities for dealing with rooms.
 * 
 * @author Tobias Mende
 * 
 */
public class Room implements IPosition {

	private static final long serialVersionUID = 112593179870431369L;
	/** the collection of walls this room consists of */
	private Collection<Wall> walls = new Vector<Wall>();// THINK use map
														// instead?
	/** A polygon that representates the shape of this room */
	private Polygon polygonRepresentation;

	@Override
	public Point getCoordinates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getCentralPoint() {
		// TODO implement
		return null;
	}

	@Override
	public Shape getShapeRepresentation() {
		// TODO implement
		return null;
	}

	/**
	 * Adds a wall to the room
	 * @param w the wall to add
	 */
	public void addWall(Wall w) {
		if (!walls.contains(w)) {
			walls.add(w);
			invalidatePolygonRepresentation();
		}
	}

	/**
	 * Removes a wall from the room
	 * @param w the wall to remove
	 */
	public void removeWall(Wall w) {
		walls.remove(w);
		invalidatePolygonRepresentation();
	}

	/**
	 * Deletes the polygon representation
	 */
	private void invalidatePolygonRepresentation() {
		polygonRepresentation = null;
	}
	
	@Override
	public boolean contains(IPosition position) {
		return contains(position.getCoordinates());
	}
	
	@Override
	public boolean contains(Point point) {
		return getShapeRepresentation().contains(point);
	}

}
