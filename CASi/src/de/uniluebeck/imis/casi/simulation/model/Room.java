package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class is a presentation of rooms in the simulation. Furthermore it
 * provides additional functionalities for dealing with rooms.
 * 
 * @author Tobias Mende
 * 
 */
public class Room implements IPosition {
	private static final Logger log = Logger.getLogger(Room.class.getName());
	private static final long serialVersionUID = 112593179870431369L;
	/** the collection of walls this room consists of */
	private List<Wall> walls = new LinkedList<Wall>();
	/** A polygon that representates the shape of this room */
	private Polygon polygonRepresentation;

	@Override
	public Point getCoordinates() {
		/**
		 * returns the coordinates of the first wall
		 */
		return walls.get(0).getCoordinates();
	}

	@Override
	public Point2D getCentralPoint() {
		Rectangle2D bounds = getShapeRepresentation().getBounds2D();
		Point2D middleCandidate = new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
		if(getShapeRepresentation().contains(middleCandidate)) {
			return middleCandidate;
		}
		//If middle candidate is wrong, give central point of first door
		// THINK how can we add a better calculation in this case (e.g. another point in the room)
		Door firstDoor = getFirstDoor();
		if(firstDoor != null) {
			return firstDoor.getCentralPoint();
		}
		return null;
	}
	
	/**
	 * Method for getting the first door in this room
	 * @return the first door or <code>null</code> if the room has no doors.
	 */
	private Door getFirstDoor() {
		for(Wall w : walls) {
			if(w.hasDoors()) {
				return w.getDoors().get(0);
			}
		}
		log.warning("This room has no doors");
		return null;
	}

	@Override
	public Shape getShapeRepresentation() {
		if(polygonRepresentation == null) {
			List<Point> points = getWallPoints();
			//Adding startpoint again for providing a closed path
			points.add(walls.get(0).getStartPoint());
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
	 * Calculates list of points which can be used for generating a polygon representation
	 * This method expects that the walls are added as they are connected.
	 * @return an ordered list of node points
	 */
	private List<Point> getWallPoints() {
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

	/**
	 * Removes a wall from the room. This method is deprecated because there is no sense in removing walls
	 * @param w the wall to remove
	 */
	@Deprecated
	public void removeWall(Wall w) {
		walls.remove(w);
		invalidatePolygonRepresentation();
	}

}
