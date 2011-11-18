package de.uniluebeck.imis.casi.simulation.model;

import java.util.Collection;

import de.uniluebeck.imis.casi.network.INetworkComponent;
//TODO add fancy solution for triggering actions
/**
 * Template for an actuator, that can be integrated in the simulation world
 * @author Tobias Mende
 *
 */
public abstract class AbstractActuator extends AbstractComponent implements INetworkComponent{
	public AbstractActuator(String identifier) {
		super(identifier);
	}
	/** Time for pulling values from the server in seconds */
	protected int pullIntervall = 10;
	
	/** List of actions, that can be vetoed by this actuator */
	protected Collection<AbstractAction> vetoableActions;
	/** Last message, the actuator has received from the network controller */
	protected Object lastResponse;
	
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
	 * @return <code>true</code> if the action is allowed, <code>false</code> otherwise
	 */
	public final boolean handle(AbstractAction action) {
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
