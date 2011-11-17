package de.uniluebeck.imis.casi.simulation.model;

import java.util.ArrayList;
import java.util.Collection;

import de.uniluebeck.imis.casi.network.INetworkComponent;

/**
 * Template for a sensor, that can be integrated in the simulation world
 * @author Tobias Mende
 *
 */
public abstract class AbstractSensor extends AbstractComponent implements INetworkComponent{

	/** List of actions, that can be recognized by this sensor */
	protected Collection<AbstractAction> sensableActions;
	/** actual value this sensor has recognized*/
	protected Object lastValue;
	
	/**
	 * Getter for the type of this sensor
	 * @return the sensor type
	 */
	public String getType() {
		return this.getClass().getName();
	}
	/**
	 * Method for handling an action
	 * @param action the action to handle
	 * @return <code>true</code> if the action was handled successful, <code>false</code> otherwise
	 */
	public boolean handle(AbstractAction action) {
		//Do fancy things
		return handleInternal(action);
	}
	/**
	 * Method for handling an action internal. Overwrite for customized behavior
	 * @param action the action to handle
	 * @return <code>true</code> if the action is allowed, <code>false</code> otherwise
	 */
	protected abstract boolean handleInternal(AbstractAction action);
}
