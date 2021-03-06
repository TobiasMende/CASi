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

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * This interfaces describes methods which are necessary to deal with different
 * objects during the simulation. It's needed for positioning purposes.
 * 
 * @author Tobias Mende
 * 
 */
public interface IPosition extends Serializable {

	/**
	 * Getter for the coordinates of this position
	 * 
	 * @return the position.
	 */
	public Point2D getCoordinates();

	/**
	 * Method for determining whether the shape of this object contains a given
	 * position
	 * 
	 * @param position
	 *            the position
	 * @return <code>true</code> if the position is in this shape,
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(IPosition position);

	/**
	 * Method for determining whether the shape of this object contains a given
	 * point
	 * 
	 * @param point
	 *            the point
	 * @return <code>true</code> if the point is in the shape,
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(Point2D point);

	/**
	 * Getter for the polygon representation of this object.
	 * 
	 * @return the existing polygon representation or a new created one if no
	 *         representation exists yet.
	 */
	public Shape getShapeRepresentation();

	/**
	 * A point which is in the shape representation of this object, in best
	 * point a point near to the middle point
	 * 
	 * @return the central point
	 */
	public Point2D getCentralPoint();
}
