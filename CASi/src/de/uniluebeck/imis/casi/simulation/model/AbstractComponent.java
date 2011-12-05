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

import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * 
 * @author Marvin Frick, Moritz Bürger, Tobias Mende
 * 
 */
public abstract class AbstractComponent implements IPosition, Serializable {
	public enum INTERRUPTIBILITY {
		INTERRUPTIBLE, UNINTERRUPTIBLE, INTERRUPT_SCHEDULED, INTERRUPTED
	}

	private static final long serialVersionUID = 7951474356106618134L;
	private Image representation;
	private IPosition position;
	private Point2D coordinates = new Point2D.Double();
	protected final String identifier;
	protected INTERRUPTIBILITY interruptibility = INTERRUPTIBILITY.INTERRUPTIBLE;

	public AbstractComponent(String identifier) {
		this.identifier = identifier;
	}

	public IPosition getCurrentPosition() {
		return position;
	}

	public boolean setCurrentPosition(IPosition currentPosition) {
		this.position = position;
		setCoordinates(position.getCentralPoint());
		return true;
	}

	public Point2D getCoordinates() {
		return coordinates;
	}

	/**
	 * Setter for the coordinates of this component
	 * 
	 * @param coordinates
	 *            the coordinates to set
	 */
	public void setCoordinates(Point2D coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Getter for the identifier
	 * 
	 * @return the identifier
	 */
	public final String getIdentifier() {
		return identifier;
	}

	/**
	 * @param interruptibility
	 *            the interruptibility to set
	 */
	public void setInterruptibility(INTERRUPTIBILITY interruptibility) {
		this.interruptibility = interruptibility;
	}

	/**
	 * @return the interruptibility
	 */
	public INTERRUPTIBILITY getInterruptibility() {
		return interruptibility;
	}

	/**
	 * Checks whether the component is interruptible or not
	 * 
	 * @return <code>true</code> if the component is interruptible,
	 *         <code>false</code> otherwise.
	 */
	public boolean isInterruptible() {
		return interruptibility.equals(INTERRUPTIBILITY.INTERRUPTIBLE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
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
		AbstractComponent other = (AbstractComponent) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}

	@Override
	public boolean contains(IPosition position) {
		return contains(position.getCentralPoint());
	}

	@Override
	public boolean contains(Point2D point) {
		return getShapeRepresentation().contains(point);
	}

}
