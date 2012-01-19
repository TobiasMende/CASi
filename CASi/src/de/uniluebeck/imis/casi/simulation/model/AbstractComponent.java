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

import de.uniluebeck.imis.casi.simulation.factory.WorldFactory;

/**
 * This class represents general components in the framework.
 * 
 * @author Marvin Frick, Moritz Bürger, Tobias Mende
 * 
 */
public abstract class AbstractComponent implements IPosition, Serializable {
	/** serialization identifier */
	private static final long serialVersionUID = 7951474356106618134L;

	/** possible interruptibility states */
	public enum INTERRUPTIBILITY {
		/** The component is interruptible */
		INTERRUPTIBLE,
		/** The component is uninterruptible */
		UNINTERRUPTIBLE,
		/** An interrupt is scheduled for this component */
		INTERRUPT_SCHEDULED,
		/** The component is currently interrupted */
		INTERRUPTED
	}

	/** An image which provides a graphical representation of this component */
	private Image representation;
	/** The current position of this component */
	private IPosition position;
	/** The exact coordinates of this component */
	protected Point2D coordinates = new Point2D.Double();
	/** An unique identifier */
	protected final String identifier;
	/** The current interruptibility */
	protected INTERRUPTIBILITY interruptibility = INTERRUPTIBILITY.INTERRUPTIBLE;

	/**
	 * This map may contain configuration options with identifier -> value.
	 */
	private ConfigMap configurationMap;

	/**
	 * Constructor for a new component
	 * 
	 * @param identifier
	 *            the identifier
	 */
	public AbstractComponent(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Getter for the current position
	 * 
	 * @return the position
	 */
	public IPosition getCurrentPosition() {
		if(position == null) {
			position = WorldFactory.getRoomsWithPoint(coordinates).getFirst();
		}
		return position;
	}

	/**
	 * Setter for the current position
	 * 
	 * @param currentPosition
	 *            the position
	 */
	public void setCurrentPosition(IPosition currentPosition) {
		this.position = currentPosition;
		this.coordinates = currentPosition.getCentralPoint();
	}

	/**
	 * Getter for the coordinates
	 * 
	 * @return the coordinates
	 */
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
		position = WorldFactory.getRoomsWithPoint(coordinates).getFirst();
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
	 * Setter for the interruptibility
	 * 
	 * @param interruptibility
	 *            the interruptibility to set
	 */
	public void setInterruptibility(INTERRUPTIBILITY interruptibility) {
		this.interruptibility = interruptibility;
	}

	/**
	 * Getter for the interuptibility
	 * 
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

	@Override
	public String toString() {
		return identifier;
	}

	/**
	 * Adds an configuration object for an unique identifier
	 * 
	 * @param identifier
	 *            the identifier
	 * @param value
	 *            the object value
	 */
	public void addConfiguration(String identifier, Object value) {
		if (configurationMap == null) {
			configurationMap = new ConfigMap();
		}
		configurationMap.put(identifier, value);
	}

	/**
	 * Sets a map as configuration map
	 * 
	 * @param config
	 *            the map
	 * @throws IllegalAccessException
	 *             if the configuration map already exists.
	 */
	public void setConfigurationMap(ConfigMap config)
			throws IllegalAccessException {
		if (configurationMap != null) {
			throw new IllegalAccessException(
					"Configuration map can't be set more than once");
		} else {
			configurationMap = config;
		}
	}

	/**
	 * Getter for the configuration object for a given identifier
	 * 
	 * @param identifier
	 *            the identifier to get the object for
	 * @return the configuration object or {@code null}, if no object for this
	 *         identifier exists.
	 */
	public Object getConfiguration(String identifier) {
		if (configurationMap == null) {
			return null;
		}
		return configurationMap.get(identifier);
	}

}
