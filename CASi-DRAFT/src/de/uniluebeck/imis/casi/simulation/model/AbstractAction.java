package de.uniluebeck.imis.casi.simulation.model;


/**
 * Template for an action that can be performed in the simulator
 * @author Tobias Mende
 *
 */
public abstract class AbstractAction {
	public enum STATE {
		SCHEDULED, INTERRUPTED, COMPLETED, ABSTRACT, UNKNOWN;
	}

	protected SimulationTime earliestStartTime;
	protected SimulationTime latestEndTime; 
	
	/** priority of this action (integer between 0 and 10) */
	protected int priority = 5;
	/** duration of this action in minutes */
	protected int duration;
	private AbstractAction.STATE state = STATE.UNKNOWN;
	/**
	 * Method for performing this action
	 * @return <code>true</code> if action was performed successful, <code>false</code> otherwise. 
	 */
	public final boolean perform() {
		STATE state = internalPerform();
		setState(state);
		
		return state.equals(STATE.COMPLETED);
	}
	/**
	 * Method for really perform the action
	 * @return the state that this action should get.
	 */
	public abstract STATE internalPerform();
	/**
	 * Getter for the type of this action
	 * @return the action type
	 */
	public String getType() {
		return this.getClass().getName();
	}
	
	
	public final boolean isAbstract() {
		return state.equals(STATE.ABSTRACT);
	}
	
	/**
	 * Method for setting the current state of this action
	 * @param state the new state
	 * @throws IllegalStateException if the state is illegal
	 */
	public final void setState(STATE state) throws IllegalStateException {
		if(state == STATE.ABSTRACT || state == STATE.UNKNOWN) {
			if(this.state != STATE.UNKNOWN) {
				// Deny changes to unknown or abstract
				throw new IllegalStateException("Can't change the state to "+state);
			}
		}
		state = this.state;
	}
	
}
