package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.LinkedList;

/**
 * The path object can be used for describing a way from a point to another.
 * @author Tobias Mende
 *
 */
public class Path extends LinkedList<Point2D> {
	/** id for serialization */
	private static final long serialVersionUID = 5513927165139455322L;
	
	/** the start point */
	private final Point startPoint;
	
	/** the end point */
	private final Point endPoint;
	
	/**
	 * Constructor for a new path that saves the way between the given start and end point
	 * @param start the start point
	 * @param end the end point
	 */
	public Path(Point start, Point end) {
		super();
		startPoint = start;
		endPoint = end;
	}

	/**
	 * Getter for the start point of this path
	 * @return the start point
	 */
	public Point getStartPoint() {
		return startPoint;
	}
	
	/**
	 * Getter for the end point of this path
	 * @return the end point
	 */

	public Point getEndPoint() {
		return endPoint;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((endPoint == null) ? 0 : endPoint.hashCode());
		result = prime * result
				+ ((startPoint == null) ? 0 : startPoint.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (endPoint == null) {
			if (other.endPoint != null)
				return false;
		} else if (!endPoint.equals(other.endPoint))
			return false;
		if (startPoint == null) {
			if (other.startPoint != null)
				return false;
		} else if (!startPoint.equals(other.startPoint))
			return false;
		if (!super.equals(obj))
			return false;
		return true;
	}

	
}
