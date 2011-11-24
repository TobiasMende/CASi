package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import de.uniluebeck.imis.casi.utils.WallComperator;

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
	private TreeSet<Wall> walls = new TreeSet<Wall>(new WallComperator());// THINK use map
														// instead?
	/** A polygon that representates the shape of this room */
	private Polygon polygonRepresentation;

	@Override
	public Point getCoordinates() {
		/**
		 * returns the coordinates of the first wall
		 */
		return walls.first().getCoordinates();
	}

	@Override
	public Point2D getCentralPoint() {
		Rectangle2D bounds = getShapeRepresentation().getBounds2D();
		Point2D middleCandidate = new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
		if(getShapeRepresentation().contains(middleCandidate)) {
			return middleCandidate;
		}
		// TODO add another central point calculation for ugly cases
		return null;
	}

	@Override
	public Shape getShapeRepresentation() {
		if(polygonRepresentation == null) {
			List<Point> points = getWallPoints();
			//Adding startpoint again for providing a closed path
			points.add(walls.first().getStartPoint());
			int[] x = new int[points.size()];
			int[] y = new int[points.size()];
			for(int i = 0; i < x.length; i++) {
				x[i] = points.get(i).x;
				y[i] = points.get(i).y;
			}
			polygonRepresentation = new Polygon(x, y, x.length);
		}
		return polygonRepresentation;
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
	 * Calculates an ordered list of points which can be used for generating a polygon representation
	 * @return an ordered list of node points
	 */
	private List<Point> getWallPoints() {
		// TODO naiv: Hope that the set is well sorted, Test how this works in normal environment
		List<Point> points = new LinkedList<Point>();
		for(Wall w: walls) {
			if(!points.contains(w.getStartPoint())) {
				points.add(w.getStartPoint());
			}
			if(!points.contains(w.getEndPoint())) {
				points.add(w.getEndPoint());
			}
		}
		return points;
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
	public boolean contains(Point2D point) {
		return getShapeRepresentation().contains(point);
	}

}
