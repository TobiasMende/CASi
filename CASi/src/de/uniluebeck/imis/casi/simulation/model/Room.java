/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende
 *
 *  This program is free software. It is licensed under the
 *  GNU Lesser General Public License with one clarification.
 *  
 *  You should have received a copy of the 
 *  GNU Lesser General Public License along with this program. 
 *  See the LICENSE.txt file in this projects root folder or visit
 *  <http://www.gnu.org/licenses/lgpl.html> for more details.
 */
package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
	
	private String identifier;
	private static int idCounter;
	public Room() {
		super();
		identifier = "room-"+idCounter;
		idCounter++;
	}

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
		Point2D middleCandidate = new Point2D.Double(bounds.getCenterX(),
				bounds.getCenterY());
		if (getShapeRepresentation().contains(middleCandidate)) {
			return middleCandidate;
		}
		// If middle candidate is wrong, give central point of first door
		// THINK how can we add a better calculation in this case (e.g. another
		// point in the room)
		Door firstDoor = getFirstDoor();
		if (firstDoor != null) {
			return firstDoor.getCentralPoint();
		}
		return null;
	}

	/**
	 * Method for getting the first door in this room
	 * 
	 * @return the first door or <code>null</code> if the room has no doors.
	 */
	private Door getFirstDoor() {
		for (Wall w : walls) {
			if (w.hasDoors()) {
				return w.getDoors().get(0);
			}
		}
		log.warning("This room has no doors");
		return null;
	}

	/**
	 * Getter for all doors in this room
	 * 
	 * @return a set of doors
	 */
	public Set<Door> getDoors() {
		Set<Door> doors = new HashSet<Door>();
		for (Wall w : walls) {
			doors.addAll(w.getDoors());
		}
		return doors;
	}

	@Override
	public Shape getShapeRepresentation() {
		if (polygonRepresentation == null) {
			List<Point> points = getWallPoints();
			// Adding startpoint again for providing a closed path
			points.add(walls.get(0).getStartPoint());
			int[] x = new int[points.size()];
			int[] y = new int[points.size()];
			for (int i = 0; i < x.length; i++) {
				x[i] = points.get(i).x;
				y[i] = points.get(i).y;
			}
			polygonRepresentation = new Polygon(x, y, x.length);
		}
		return polygonRepresentation;
	}

	/**
	 * Adds a wall to the room
	 * 
	 * @param w
	 *            the wall to add
	 */
	public void addWall(Wall w) {
		if (!walls.contains(w)) {
			walls.add(w);
			invalidatePolygonRepresentation();
		}
	}

	public List<Wall> getWalls() {
		return walls;
	}

	/**
	 * Calculates list of points which can be used for generating a polygon
	 * representation This method expects that the walls are added as they are
	 * connected.
	 * 
	 * @return an ordered list of node points
	 */
	private List<Point> getWallPoints() {
		List<Point> points = new LinkedList<Point>();
		for (Wall w : walls) {
			if (!points.contains(w.getStartPoint())) {
				points.add(w.getStartPoint());
			}
			if (!points.contains(w.getEndPoint())) {
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
		if(getShapeRepresentation().contains(point)) {
			return true;
		}
		for(Wall w : walls) {
			if(w.contains(point)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes a wall from the room. This method is deprecated because there is
	 * no sense in removing walls
	 * 
	 * @param w
	 *            the wall to remove
	 */
	@Deprecated
	public void removeWall(Wall w) {
		walls.remove(w);
		invalidatePolygonRepresentation();
	}
	
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return identifier;
	}

}
