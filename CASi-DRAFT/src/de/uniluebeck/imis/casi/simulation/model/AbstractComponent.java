package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Image;
import java.awt.Point;
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
	
	@Override
	public Point getCoordinates() {
		// TODO implement
		return null;
	}
	
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
	
	
	
}
