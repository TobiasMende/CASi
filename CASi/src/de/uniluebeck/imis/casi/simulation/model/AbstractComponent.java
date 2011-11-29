/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
 */
package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * 
 * @author Marvin Frick, Moritz Bürger, Tobias Mende
 * 
 */
public abstract class AbstractComponent implements IPosition, Serializable {
	private Image representation;
	private IPosition position;
	protected final String identifier;

	public AbstractComponent(String identifier) {
		this.identifier = identifier;
	}

	public IPosition getCurrentPosition() {
		return position;
	}

	public boolean setCurrentPosition(IPosition currentPosition) {
		this.position = position;
		return true;
	}

	public Point2D getCoordinates() {
		// TODO implement
		return null;
	};

	public final String getIdentifier() {
		return identifier;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Point2D point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Shape getShapeRepresentation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point2D getCentralPoint() {
		// TODO Auto-generated method stub
		return null;
	}

}
