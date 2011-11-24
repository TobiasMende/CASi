package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.utils.Tools;

/**
 * 
 * @author Tobias Mende, Marvin Frick, Moritz B&uuml;rger
 * 
 *         XXX enhance this draft, it's really raw
 */
public class Agent extends AbstractComponent implements
		ISimulationClockListener, Serializable {
	private static final long serialVersionUID = -1166856593367347255L;
	private static final Logger log = Logger.getLogger(Agent.class.getName());

	public enum STATE {
		ABSTRACT, IDLE, BUSY, UNKNOWN;
	}

	// Name of the agent
	private String name;
	private String type;
	private IPosition defaultPosition;

	/**
	 * Ordered list of actions the agent should perform in this order during
	 * this simulation
	 */
	private Collection<AbstractAction> todoList;
	/** A pool of actions that can be performed by this agent type */
	private Collection<AbstractAction> actionPool;
	/** Listeners, perhaps already needed in AbstractComponent */
	private transient ArrayList<IAgentListener> agentListeners = new ArrayList<IAgentListener>();
	private Agent.STATE state = STATE.UNKNOWN;

	/**
	 * Constructor for an abstract agent.
	 * 
	 * @param identifier
	 *            the id of this agent type
	 */
	public Agent(String identifier, String type) {
		super(identifier);
		this.type = type;
		state = STATE.ABSTRACT;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for an Agent.
	 * 
	 * @param name
	 *            the name of the agent
	 * @param type
	 *            the type of the agent
	 * @param defaultPosition
	 *            the default position
	 */
	public Agent(String identifier, String name, String type,
			IPosition defaultPosition) {
		super(identifier);
		this.name = name;
		this.type = type;
		this.defaultPosition = defaultPosition;
	}

	/**
	 * Constructor for an agent
	 * 
	 * @param name
	 *            the name of the agent
	 * @param type
	 *            the type of the agent
	 */
	public Agent(String identifier, String name, String type) {
		this(identifier, name, type, null);
	}

	public void setState(STATE state) throws IllegalStateException {
		if (state.equals(STATE.ABSTRACT) && this.state != STATE.UNKNOWN) {
			// Deny changes to unknown or abstract
			throw new IllegalStateException("Can't change the state to "
					+ state);
		}
		if (state.equals(STATE.ABSTRACT)) {
			// remove the agent as clock listener if state changes to abstract
			SimulationClock.getInstance().removeListener(this);
		} else if (this.state.equals(STATE.ABSTRACT)) {
			// add the agent as clock listener if state changes from abstract to
			// some other state
			SimulationClock.getInstance().addListener(this);
		}
		state = this.state;
	}

	public boolean isTemplate() {
		return state.equals(STATE.ABSTRACT);
	}

	public Collection<AbstractAction> getTodoList() {
		return todoList;
	}

	public Collection<AbstractAction> getActionPool() {
		return actionPool;
	}

	public void setTodoList(Collection<AbstractAction> todoList) {
		this.todoList = todoList;
	}
	
	
	public void addActionToPool(AbstractAction action) {
		actionPool.add(action);
	}
	
	public void addActionToList(AbstractAction action) {
		todoList.add(action);
	}

	public void setActionPool(Collection<AbstractAction> actionPool) {
		this.actionPool = actionPool;
	}

	public void addListener(IAgentListener listener) {
		if (!agentListeners.contains(listener)) {
			agentListeners.add(listener);
		}
	}

	public void removeListener(IAgentListener listener) {
		agentListeners.remove(listener);
	}

	@Override
	public void timeChanged(SimulationTime newTime) {
		// TODO handle the actions here
	}

	/**
	 * Method for cloning an agent and its components to a relay new agent with state IDLE.
	 * @return the clone
	 */
	public Agent clone() {
		Agent newAgent;
		try {
			newAgent = (Agent) Tools.deepCopy(this);
			newAgent.setState(STATE.IDLE);
			return newAgent;
		} catch (Exception e) {
			log.severe("An error occured while cloning the agent: "+e.getLocalizedMessage());
			log.severe(e.fillInStackTrace().toString());
		} 
		return null;
	}

	@Override
	public void simulationPaused(boolean pause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulationStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulationStarted() {
		// TODO Auto-generated method stub
		
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
	public Point getCentralPoint() {
		// TODO Auto-generated method stub
		return null;
	}

}
