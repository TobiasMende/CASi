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
package de.uniluebeck.imis.casi.simulation.factory;

import java.awt.geom.Point2D;

/**
 * Collection of helper methods for dealing with graphic objects and for
 * performing vector calculations which are needed for simulation modulation.
 * 
 * @author Tobias Mende
 * 
 */
public class GraphicFactory {

	/**
	 * Method for calculating the direction vector between two points.
	 * 
	 * @param start
	 *            the start point
	 * @param end
	 *            the end point
	 * @return a vector that represents the direction. This vector isn't
	 *         normalized yet.
	 */
	public static Point2D getDirectionVector(Point2D start, Point2D end) {
		return new Point2D.Double(end.getX() - start.getX(), end.getY()
				- start.getY());
	}

	/**
	 * Method for calculating the normalized version of a vector
	 * 
	 * @param vector
	 *            the vector to normalize
	 * @return the normalized version
	 */
	public static Point2D getNormalizedVector(Point2D vector) {
		double length = calculateVectorLength(vector);
		double x = vector.getX() / length;
		double y = vector.getY() / length;
		return new Point2D.Double(x, y);
	}

	/**
	 * Method for calculating the length of a vector
	 * 
	 * @param vector
	 *            the vector
	 * @return the length
	 */
	public static double calculateVectorLength(Point2D vector) {
		return Math.hypot(vector.getX(), vector.getY());
	}

}
