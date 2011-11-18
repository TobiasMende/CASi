package de.uniluebeck.imis.casi.simulation.model;

import java.util.ArrayList;
import java.util.Collection;


public class Agent extends AbstractComponent {
	public Agent(String identifier) {
		super(identifier);
		// TODO Auto-generated constructor stub
	}

	public enum STATE {
		ABSTRACT, IDLE, BUSY, UNKNOWN;
	}
	// Name of the agent
	private String name;
	private String type;
	private IPosition defaultPosition;
	
	
	/** Ordered list of actions the agent should perform in this order during this simulation */
	private Collection<AbstractAction> todoList;
	/** A pool of actions that can be performed by this agent type */
	private Collection<AbstractAction> actionPool;
	/** Listeners, perhaps already needed in AbstractComponent */
	private ArrayList<IAgentListener> agentListeners = new ArrayList<IAgentListener>();
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


	public void addListener(IAgentListener listener) {
		if(!agentListeners.contains(listener)) {
			agentListeners.add(listener);
		}
	}
	
	public void removeListener(IAgentListener listener) {
		agentListeners.remove(listener);
	}

	@Override
	public String getIdentifier() {
		return super.identifier;
	}

}
