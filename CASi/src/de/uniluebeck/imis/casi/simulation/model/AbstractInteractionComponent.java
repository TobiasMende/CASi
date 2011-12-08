/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2011 2012  Moritz Bürger, Marvin Frick, Tobias Mende
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

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import de.uniluebeck.imis.casi.communication.ICommunicationComponent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

/**
 * This class represents components that are able to interact with their
 * surroundings and that have an area in which they are interested.
 * 
 * @author Tobias Mende
 * 
 */
public abstract class AbstractInteractionComponent extends AbstractComponent implements ICommunicationComponent{
	/** Enumeration for possible directions in which this component looks */
	public enum Face {
		NORTH(0), SOUTH(180), EAST(90), WEST(270), NORTH_EAST(45), SOUTH_EAST(
				135), NORTH_WEST(315), SOUTH_WEST(225);
		/** The radian representation of this face */
		private double radian;

		/** Private Constructor */
		private Face(double radian) {
			this.radian = radian;
		}

		/**
		 * Getter for the radian value.
		 * 
		 * @return the radian representation
		 */
		private double radian() {
			return radian;
		}
	}

	/** Counter for created interaction components */
	private static int idCounter;

	/** The radius of the monitored shape */
	protected int radius = -1;
	/** The direction in which this component looks */
	protected Face direction;
	/** The extent of the monitored area */
	protected double opening = -1;
	
	
	/** The {@link Arc2D} representation of the monitored area */
	private Shape shapeRepresentation;

	/**
	 * id for serialization
	 */
	private static final long serialVersionUID = -9016454038144232069L;

	/**
	 * Constructor for a new interaction component with a given identifier and
	 * position
	 * 
	 * @param identifier
	 *            the identifier
	 * @param coordinates
	 *            the coordinates of this component
	 */
	public AbstractInteractionComponent(String identifier, Point coordinates) {
		super(identifier);
		this.coordinates = coordinates;
	}

	/**
	 * Constructor for a new interaction component with a given position
	 * 
	 * @param coordinates
	 *            the coordinates of this component
	 */
	public AbstractInteractionComponent(Point coordinates) {
		this("ioComponent-" + idCounter, coordinates);
		idCounter++;
	}

	/**
	 * Constructor which creates an interaction component with a specified
	 * monitored area
	 * 
	 * @param coordinates
	 *            the position of this component
	 * @param radius
	 *            the radius of the monitored area
	 * @param direction
	 *            the direction in which this component "looks"
	 * @param opening
	 *            the opening angle of the area
	 */
	public AbstractInteractionComponent(Point coordinates, int radius,
			Face direction, int opening) {
		this(coordinates);
		this.radius = radius;
		this.direction = direction;
		this.opening = opening;
	}

	@Override
	public Shape getShapeRepresentation() {
		if (shapeRepresentation != null) {
			// Shape was calculated before:
			return shapeRepresentation;
		}
		if (radius < 0 && getCurrentPosition() != null) {
			// Monitor the whole room:
			shapeRepresentation = getCurrentPosition().getShapeRepresentation();
			return shapeRepresentation;
		}
		// Calculate the new shape:
		double currentOpening = opening < 0 ? 360 : opening;
		Face currentDirection = direction == null ? Face.NORTH : direction;
		double startAngle = currentDirection.radian() - (currentOpening / 2);
		shapeRepresentation = new Arc2D.Double(calculateCircleBounds(),
				startAngle, currentOpening, Arc2D.PIE);
		return shapeRepresentation;
	}

	/**
	 * Calculates a quadratic area which is exactly big enough to contain the
	 * circle which is used to calculate the monitored area
	 * 
	 * @return the bounds
	 */
	private Rectangle2D calculateCircleBounds() {
		double y = coordinates.getY() - radius;
		double x = coordinates.getX() - radius;
		return new Rectangle2D.Double(x, y, 2 * radius, 2 * radius);
	}

	@Override
	public Point2D getCentralPoint() {
		return getCoordinates();
	}
	
	/**
	 * Setter for a new monitored area
	 * 
	 * @param direction
	 *            the direction in which this component "looks"
	 * @param radius
	 *            the radius of the monitored area
	 * @param opening
	 *            the opening angle
	 */
	public void setMonitoredArea(Face direction, int radius, int opening) {
		shapeRepresentation = null;
		this.direction = direction;
		this.radius = radius;
		this.opening = opening;

	}

	/**
	 * Resets the monitored area. Components with unspecified monitored areas
	 * monitor the whole room in which they are contained.
	 */
	public void resetMonitoredArea() {
		setMonitoredArea(null, -1, -1);
	}

	/**
	 * Method for handling an action internal. Overwrite for customized behavior
	 * 
	 * @param action
	 *            the action to handle
	 * @return <code>true</code> if the action is allowed, <code>false</code>
	 *         otherwise
	 */
	protected abstract boolean handleInternal(AbstractAction action);
	
	/**
	 * Getter for human readable version of the current value
	 * @return the value in a nicer format
	 */
	public abstract String getHumanReadableValue();
	
	/**
	 * Method for handling an action
	 * 
	 * @param action
	 *            the action to handle
	 * @return <code>true</code> if the action is allowed, <code>false</code>
	 *         otherwise
	 */
	public final boolean handle(AbstractAction action) {
		// TODO Do fancy things
		return handleInternal(action);
	}
	
	/**
	 * Getter for the type of this sensor
	 * 
	 * @return the sensor type
	 */
	public String getType() {
		return this.getClass().getName();
	}
}
