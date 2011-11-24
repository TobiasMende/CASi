package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * Representation of a wall which is used by {@link Room}s and {@link Door}s
 * 
 * @author Tobias Mende
 * 
 */
public class Wall implements IPosition {
	private static final Logger log = Logger.getLogger(Wall.class.getName());
	private Point start;
	private Point end;

	private final Collection<Door> doors = new Vector<Door>();

	/**
	 * Constructor creating a wall with provided start and end point
	 * 
	 * @param start
	 *            the start point
	 * @param end
	 *            the end point
	 */
	public Wall(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Method for adding a door to the wall
	 * 
	 * @param door
	 *            the door to add
	 */
	public void addDoor(Door door) {
		doors.add(door);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doors == null) ? 0 : doors.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wall other = (Wall) obj;
		if (doors == null) {
			if (other.doors != null)
				return false;
		} else if (!doors.equals(other.doors))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	/**
	 * Getter for the start point
	 * 
	 * @return the start point
	 */
	public Point getStartPoint() {
		return start;
	}

	/**
	 * Getter for the end point of the wall
	 * 
	 * @return the end point
	 */
	public Point getEndPoint() {
		return end;
	}

	@Override
	public Point getCoordinates() {
		return getStartPoint();
	}

	@Override
	public boolean contains(IPosition position) {
		return contains(position.getCoordinates());
	}

	@Override
	public boolean contains(Point2D point) {
		return getShapeRepresentation().ptLineDist(point) == 0;
	}

	@Override
	public Line2D getShapeRepresentation() {
		return new Line2D.Double(start, end);
	}

	@Override
	public Point2D getCentralPoint() {
		Point2D middle = new Point2D.Double();
		double x = ((double) (start.x + end.x)) / 2;
		double y = ((double) (start.y + end.y)) / 2;
		middle.setLocation(x, y);
		if (contains(middle)) {
			return middle;
		}
		log.severe("Calculated Middle is not on the wall. This shouldn't happen. Start = "
				+ start
				+ ", End = "
				+ end
				+ ", Middle = "
				+ middle
				+ ", Distance = " + getShapeRepresentation().ptLineDist(middle));
		return start;
	}

}
