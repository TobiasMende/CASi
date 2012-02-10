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

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The path object can be used for describing a way from a point to another.
 * 
 * @author Tobias Mende
 * 
 */
public class Path extends LinkedList<Point2D> {
	/** id for serialization */
	private static final long serialVersionUID = 5513927165139455322L;

	/** the start point */
	private final Point2D startPoint;

	/** the end point */
	private final Point2D endPoint;



	/**
	 * Constructor for a new path that saves the way between the given start and
	 * end point
	 * 
	 * @param start
	 *            the start point
	 * @param end
	 *            the end point
	 */
	public Path(Point2D start, Point2D end) {
		super();
		startPoint = start;
		endPoint = end;
	}

	/**
	 * Getter for the start point of this path
	 * 
	 * @return the start point
	 */
	public Point2D getStartPoint() {
		return startPoint;
	}

	/**
	 * Getter for the end point of this path
	 * 
	 * @return the end point
	 */

	public Point2D getEndPoint() {
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

	/**
	 * Creates a reversed version of the path, meaning a path from end to start
	 * point
	 * 
	 * @return the reversed path
	 */
	public Path reversed() {
		Path reversedPath = new Path(endPoint, startPoint);
		for (Iterator<Point2D> iter = descendingIterator(); iter.hasNext();) {
			reversedPath.add(iter.next());
		}
		return reversedPath;
	}
}
