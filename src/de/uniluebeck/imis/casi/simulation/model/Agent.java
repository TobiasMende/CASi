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

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.IActionScheduler;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.schedulers.DefaultActionScheduler;
import de.uniluebeck.imis.casi.utils.Tools;

/**
 * This class represents an agent which is able to deal with the world and
 * performing actions and so on.
 * 
 * @author Tobias Mende, Marvin Frick, Moritz B&uuml;rger
 * 
 */
public class Agent extends AbstractComponent implements
		ISimulationClockListener, Serializable {
	/** serialization identifier */
	private static final long serialVersionUID = -1166856593367347255L;
	/** the development logger */
	private static final Logger log = Logger.getLogger(Agent.class.getName());

	/** possible states */
	public enum STATE {
		/** the agent is idle */
		IDLE,
		/** the agent is busy */
		BUSY,
		/** the state is unknown */
		UNKNOWN;
	}

	/** The name of the agent */
	private String name;
	/** The type of the agent */
	private String type;
	/**
	 * the default position where the agent starts and where other agents are
	 * looking for it
	 */
	private IPosition defaultPosition;
	/** The current action */
	private AbstractAction currentAction;
	/** the scheduler which handles the actions */
	private IActionScheduler actionScheduler;

	/** Listeners, perhaps already needed in AbstractComponent */
	private transient List<IAgentListener> agentListeners = new LinkedList<IAgentListener>();
	/** a list of extended listeners with veto rigts */
	private transient List<IExtendedAgentListener> extendedListeners = new LinkedList<IExtendedAgentListener>();
	/** the current state */
	private Agent.STATE state = STATE.UNKNOWN;

	/**
	 * The default constructor
	 * 
	 * @param identifier
	 *            the unique identifier
	 */
	private Agent(String identifier) {
		super(identifier);
		actionScheduler = new DefaultActionScheduler();
	}

	/**
	 * Constructor for an abstract agent.
	 * 
	 * @param identifier
	 *            the id of this agent type
	 */
	public Agent(String identifier, String type) {
		this(identifier);
		this.setType(type);
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
		this(identifier);
		this.name = name;
		this.setType(type);
		this.defaultPosition = defaultPosition;
	}

	/**
	 * Constructor for an agent
	 * 
	 * @param identifier
	 *            the intern (still human readable) identifier of this agent
	 * 
	 * @param name
	 *            the name of the agent
	 * @param type
	 *            the type of the agent
	 */
	public Agent(String identifier, String name, String type) {
		this(identifier, name, type, null);
	}

	/**
	 * Setter for the state
	 * 
	 * @param state
	 *            the new state
	 */
	public void setState(STATE state) {
		if(!state.equals(this.state)) {
			informListenersAboutStateChange(state);
			CASi.SIM_LOG.config("State of " + this + " changed to " + state);
			this.state = state;
		}
	}

	/**
	 * Setter for the todo list
	 * 
	 * @param todoList
	 *            the todo list
	 */
	public void setTodoList(Collection<AbstractAction> todoList) {
		actionScheduler.setTodoList(todoList);
		for (AbstractAction a : todoList) {
			a.setState(AbstractAction.STATE.SCHEDULED);
		}
	}

	/**
	 * Adds an action to the pool of actions which might be performed during the
	 * simulation
	 * 
	 * @param action
	 *            the action
	 */
	public void addActionToPool(AbstractAction action) {
		actionScheduler.addPoolAction(action);
	}

	/**
	 * Adds an action to the todo list
	 * 
	 * @param action
	 *            the action to add
	 */
	public void addActionToList(AbstractAction action) {
		action.setState(AbstractAction.STATE.SCHEDULED);
		actionScheduler.schedule(action);
	}

	/**
	 * Tries to set an interrupt action
	 * 
	 * @param action
	 *            the action to interrupt the agent with
	 * @return <code>true</code> if the agent could be interrupted,
	 *         <code>false</code> otherwise.
	 */
	public boolean interrupt(AbstractAction action) {
		if (isInterruptible()) {
			actionScheduler.addInterruptAction(action);
			setInterruptibility(INTERRUPTIBILITY.INTERRUPT_SCHEDULED);
			CASi.SIM_LOG.info(action + " was scheduled for interrupt");
			return true;
		}
		return false;
	}

	/**
	 * Sets a new pool of suggested actions
	 * 
	 * @param actionPool
	 *            the pool
	 */
	public void setActionPool(Collection<AbstractAction> actionPool) {
		actionScheduler.setActionPool(actionPool);
	}

	/**
	 * Adds a normal listener to this agent
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(IAgentListener listener) {
		if (!agentListeners.contains(listener)) {
			agentListeners.add(listener);
		}
	}

	/**
	 * Removes a listener from this agent
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeListener(IAgentListener listener) {
		agentListeners.remove(listener);
		extendedListeners.remove(listener);
	}

	/**
	 * Adds a listener with veto right
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addVetoableListener(IExtendedAgentListener listener) {
		if (!extendedListeners.contains(listener)) {
			extendedListeners.add(listener);
		}
	}

	/**
	 * Removes a listener with veto rights
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeVetoableListener(IAgentListener listener) {
		extendedListeners.remove(listener);
	}

	@Override
	public void timeChanged(SimulationTime newTime) {
		if (actionScheduler.isInterruptScheduled()
				&& interruptibility
						.equals(INTERRUPTIBILITY.INTERRUPT_SCHEDULED)) {
			// store current action as next action and perform interrupt action
			switchActions(true);
			setInterruptibility(INTERRUPTIBILITY.INTERRUPTED);
		} else if (currentAction == null || currentAction.isCompleted()) {
			informListenersAboutFinishingAction(currentAction);
			setInterruptibility(INTERRUPTIBILITY.INTERRUPTIBLE);
			// need a new action to perform:
			currentAction = actionScheduler.getNextAction();
			informListenersAboutStartingAction(currentAction);
			if (currentAction == null) {
				// CASi.SIM_LOG.info(this.name +
				// ": Nothing to do at the moment.");
				setState(STATE.IDLE);
				return;
			}
		}

		if (!askInteractionComponents()) {
			switchActions(false);
			return;
		}

		performAction();
	}

	/**
	 * Switch from the current action to another action
	 * 
	 * @param interrupt
	 *            if {@code true} the action is stored to the interrupt list,
	 *            else it is stored to the todo list.
	 */
	private void switchActions(boolean interrupt) {
		AbstractAction tmp = currentAction;
		informListenersAboutFinishingAction(tmp);
		currentAction = actionScheduler.getNextAction();
		informListenersAboutStartingAction(currentAction);
		if (tmp != null) {
			tmp.setState(AbstractAction.STATE.INTERRUPTED);
			if (interrupt) {
				actionScheduler.addInterruptAction(tmp);
			} else {
				actionScheduler.schedule(tmp);
			}
		}
	}

	/**
	 * Asks the actuators whether the currentAction should be performed or not.
	 * 
	 * @return <code>true</code> if the action should be performed,
	 *         <code>false</code> otherwise.
	 */
	private boolean askInteractionComponents() {
		for (IExtendedAgentListener listener : extendedListeners) {
			if (!listener.handle(currentAction, this)) {
				if (listener.hasVetoRight()) {
					CASi.SIM_LOG.info(this + ": " + listener
							+ " doesn't allow to perform " + currentAction);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Performs the current action.
	 */
	private void performAction() {
		try {
			setState(STATE.BUSY);
			currentAction.perform(this);
			if (currentAction.getState().equals(
					AbstractAction.STATE.INTERRUPTED)) {
				currentAction.setEarliestStartTime(SimulationClock.getInstance().getCurrentTime().plus(360));
				CASi.SIM_LOG
						.info(this
								+ ": Current action was interrupted. Schedule for later");
				currentAction.reset();
				addActionToList(currentAction);
				currentAction = null;
			}
		} catch (IllegalAccessException e) {
			CASi.SIM_LOG
					.severe("The last action wasn't performable. "
							+ this.name
							+ " has better things to do than solving impossible problems. E.g Searching for a Club Mate! State: "
							+ currentAction.getState());
			log.severe(e.fillInStackTrace().toString());
			setState(STATE.UNKNOWN);
			currentAction = null;
		}
	}

	/**
	 * Method for cloning an agent and its components to a relay new agent with
	 * state IDLE.
	 * 
	 * @return the clone
	 */
	public Agent clone() {
		Agent newAgent;
		try {
			newAgent = (Agent) Tools.deepCopy(this);
			newAgent.agentListeners = new LinkedList<IAgentListener>();
			newAgent.extendedListeners = new LinkedList<IExtendedAgentListener>();
			newAgent.setState(STATE.IDLE);
			return newAgent;
		} catch (Exception e) {
			log.severe("An error occured while cloning the agent: "
					+ e.getLocalizedMessage());
			log.severe(e.fillInStackTrace().toString());
		}
		return null;
	}

	@Override
	public void simulationPaused(boolean pause) {
		// nothing to do here

	}

	@Override
	public void simulationStopped() {
		// nothing to do here

	}

	@Override
	public void simulationStarted() {
		// nothing to do here

	}

	@Override
	public boolean contains(IPosition position) {
		return contains(position.getCoordinates());
	}

	@Override
	public boolean contains(Point2D point) {
		return getShapeRepresentation().contains(point);
	}

	@Override
	public Shape getShapeRepresentation() {
		return new Rectangle2D.Double(getCoordinates().getX(), getCoordinates()
				.getY(), 1, 1);
	}

	@Override
	public Point2D getCentralPoint() {
		return getCoordinates();
	}

	@Override
	public void setCoordinates(Point2D coordinates) {
		Point2D oldPoint = super.getCoordinates();
		super.setCoordinates(coordinates);
		informListenersAboutPositionChange(oldPoint, coordinates);
	}

	/**
	 * Informs all listener about a new position
	 * 
	 * @param oldPoint
	 *            where the agent came from
	 * @param newPoint
	 *            where the agent goes to.
	 */
	private void informListenersAboutPositionChange(Point2D oldPoint,
			Point2D newPoint) {
		if (CASi.VERBOSE && CASi.DEV_MODE) {
			log.fine("Position of " + toString() + " changed from " + oldPoint
					+ " to " + newPoint);
		}
		for (IAgentListener listener : extendedListeners) {
			listener.positionChanged(oldPoint, newPoint, this);
		}
		for (IAgentListener listener : agentListeners) {
			listener.positionChanged(oldPoint, newPoint, this);
		}
	}

	/**
	 * Informs the listeners about a new state
	 * 
	 * @param state
	 *            the new state
	 */
	private void informListenersAboutStateChange(STATE state) {
		if (state.equals(this.state)) {
			return;
		}

		for (IAgentListener listener : extendedListeners) {
			listener.stateChanged(state, this);
		}
		for (IAgentListener listener : agentListeners) {
			listener.stateChanged(state, this);
		}

	}

	/**
	 * Getter for the default position
	 * 
	 * @return the default position which is a {@link Room} in most cases
	 */
	public IPosition getDefaultPosition() {
		return defaultPosition == null ? getCurrentPosition() : defaultPosition;
	}

	/**
	 * Setter for the default position.
	 * @param defaultPosition
	 *            the defaultPosition to set
	 */
	public void setDefaultPosition(IPosition defaultPosition) {
		this.defaultPosition = defaultPosition;
		super.setCurrentPosition(defaultPosition);
		Point2D oldPoint = super.getCoordinates();
		informListenersAboutPositionChange(oldPoint, coordinates);
	}

	@Override
	public void setInterruptibility(INTERRUPTIBILITY interruptibility) {
		if (interruptibility.equals(this.interruptibility)) {
			return;
		}
		super.setInterruptibility(interruptibility);
		for (IAgentListener listener : extendedListeners) {
			listener.interruptibilityChanged(interruptibility, this);
		}
		for (IAgentListener listener : agentListeners) {
			listener.interruptibilityChanged(interruptibility, this);
		}
	}

	/**
	 * Informs listeners about the end of an action
	 * 
	 * @param action
	 *            the action that was just completed
	 */
	private void informListenersAboutFinishingAction(AbstractAction action) {
		if (action == null || !action.isCompleted()) {
			return;
		}
		for (IAgentListener listener : extendedListeners) {
			listener.finishPerformingAction(action, this);
		}
		for (IAgentListener listener : agentListeners) {
			listener.finishPerformingAction(action, this);
		}
	}

	/**
	 * Informs listeners about starting with a new action
	 * 
	 * @param action
	 *            the new action
	 */
	private void informListenersAboutStartingAction(AbstractAction action) {
		if (action == null) {
			return;
		}
		for (IAgentListener listener : extendedListeners) {
			listener.startPerformingAction(action, this);
		}
		for (IAgentListener listener : agentListeners) {
			listener.startPerformingAction(action, this);
		}
	}

	/**
	 * Getter for the type
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter for the type
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter for the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the name
	 * 
	 * @param name
	 *            the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the current action
	 * 
	 * @return the currently performed action or {@code null} if the agent is
	 *         {@link STATE#IDLE}.
	 */
	public AbstractAction getCurrentAction() {
		return currentAction;
	}

	/**
	 * Getter for the agents state
	 * 
	 * @return the state
	 */
	public Agent.STATE getState() {
		return state;
	}

	/**
	 * Getter for a copy of the todo list. Changes in this list don't effect the
	 * scheduler
	 * 
	 * @return a copy of the todo list
	 */
	public List<AbstractAction> getTodoListCopy() {
		return actionScheduler.getTodoListCopy();
	}

	/**
	 * Getter for a copy of the action pool. Changes in this list don't effect
	 * the scheduler
	 * 
	 * @return a copy of the action pool
	 */
	public List<AbstractAction> getActionPoolCopy() {
		return actionScheduler.getActionPoolCopy();
	}

	/**
	 * Getter for a copy of the interrupt list. Changes in this list don't
	 * effect the scheduler
	 * 
	 * @return a copy of the interrupt list
	 */
	public List<AbstractAction> getInterruptListCopy() {
		return actionScheduler.getInterruptListCopy();
	}

}
