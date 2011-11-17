package de.uniluebeck.imis.casi.simulation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import de.uniluebeck.imis.casi.utils.GenericListener;
import de.uniluebeck.imis.casi.utils.Listenable;


public class Agent extends AbstractComponent implements Listenable<GenericListener<Agent>> {
	public enum STATE {
		ABSTRACT, IDLE, BUSY, UNKNOWN;
	}
	// Name of the agent
	private String name;
	private String type;
	private IPosition defaultPosition;
	
	
	/** Orderd list of actions the agent should perform in this order during this simulation */
	private Collection<AbstractAction> todoList;
	/** A pool of actions that can be performed by this agent type */
	private Collection<AbstractAction> actionPool;
	/** Listeners, perhaps already needed in AbstractComponent */
	private ArrayList<GenericListener<Agent>> listeners = new ArrayList<GenericListener<Agent>>();
	private Agent.STATE state = STATE.UNKNOWN;
	
	public boolean isTemplate() {
		return state.equals(STATE.ABSTRACT);
	}
	
	public void setState(STATE state) throws IllegalStateException {
		if(state == STATE.ABSTRACT && this.state != STATE.UNKNOWN) {
				// Deny changes to unknown or abstract
				throw new IllegalStateException("Can't change the state to "+state);
		}
		state = this.state;
	}

	@Override
	public void addListener(GenericListener<Agent> listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeListener(GenericListener<Agent> listener) {
		listeners.remove(listener);
	}
}
