package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Representation of a wall which is used by {@link Room}s and {@link Door}s
 * 
 * @author Tobias Mende
 * 
 */
public class Wall implements IPosition {
	/** just the logger */
	private static final Logger log = Logger.getLogger(Wall.class.getName());
	/**
	 * id needed for serialization
	 */
	private static final long serialVersionUID = 717672722422035342L;
	/** the point where this wall starts */
	private Point start;
	/** the point where this wall ends */
	private Point end;
	/** A collection of doors that are in this wall */
	private final List<Door> doors = new ArrayList<Door>();

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
		door.setWall(this);
		doors.add(door);
		
	}

	/**
	 * Getter for the doors contained in this walls
	 * @return a collection of doors
	 */
	public List<Door> getDoors() {
		return doors;
	}
	
	/**
	 * Method for checking whether this wall contains doors or not.
	 * @return <code>true</code> if the wall contains at least one door, <code>false</code> otherwise.
	 */
	public boolean hasDoors() {
		return doors.size()>0;
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
		if(!(obj instanceof Wall)) {
			return false;
		}
		Wall other = (Wall) obj;
		boolean areIdentical = this.getStartPoint().equals(other.getStartPoint()) && this.getEndPoint().equals(other.getEndPoint());
		boolean areInversed = this.getStartPoint().equals(other.getEndPoint()) && this.getEndPoint().equals(other.getStartPoint());
		if(areIdentical || areInversed) {
			return true;
		}
		/* TODO add further cases where walls are really equal
		 * 
		 * ATTENTION: They are not equal, if they have different lengths!
		 */
		return false;
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
	
	/**
	 * Getter for a normalized vector that describes the direction of the wall
	 * @return the vector
	 */
	public Point2D getNormalizedWallVector() {
		double xDistance = end.x - start.x;
		double yDistance = end.y - start.y;
		double wallLength = Math.hypot(xDistance, yDistance);
		return new Point2D.Double(xDistance/wallLength, yDistance/wallLength);
	}
	
	/**
	 * Getter for the length of the wall
	 * @return the length
	 */
	public double getLength() {
		Point2D distanceVector = new Point2D.Double(end.getX()-start.getX(), end.getY()-start.getY());
		return Math.hypot(distanceVector.getX(), distanceVector.getY());
	}


}
