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
package de.uniluebeck.imis.casi.simulation.model.actionHandling.schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction.STATE;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ActionComparator;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.IActionScheduler;

/**
 * The default action scheduler is the first implementation of the
 * {@link IActionScheduler} which tries to schedule by using the EDF algorithm
 * or if that dosn't work, it tries to schedule by using the earliest start time
 * and the priority.
 * 
 * @author Tobias Mende
 * 
 */
public class DefaultActionScheduler implements IActionScheduler {
	/** the development logger */
	@SuppressWarnings("unused")
	private static final Logger log = Logger
			.getLogger(DefaultActionScheduler.class.getName());
	/** serialization identifier */
	private static final long serialVersionUID = -234471419299766380L;

	/** a set of actions that should be performed during the simulation */
	private TreeSet<AbstractAction> todoList;
	/** a set of actions that might be performed during the simulation */
	private ArrayList<AbstractAction> actionPool;
	/** a list of actions that have to be performed immediately */
	private LinkedList<AbstractAction> interruptAction;

	/**
	 * Constructor which uses provided lists of actions for initialization
	 * 
	 * @param todoList
	 *            the todo list
	 * @param actionPool
	 *            the action pool
	 */
	public DefaultActionScheduler(Collection<AbstractAction> todoList,
			Collection<AbstractAction> actionPool) {
		this.todoList.addAll(todoList);
		this.actionPool.addAll(actionPool);
	}

	/**
	 * Default constructor
	 */
	public DefaultActionScheduler() {
		todoList = new TreeSet<AbstractAction>(new ActionComparator());
		actionPool = new ArrayList<AbstractAction>();
		interruptAction = new LinkedList<AbstractAction>();
	}

	@Override
	public void setTodoList(Collection<AbstractAction> todoList) {
		this.todoList.clear();
		this.todoList.addAll(todoList);

	}

	@Override
	public void setActionPool(Collection<AbstractAction> actionPool) {
		this.actionPool.clear();
		this.actionPool.addAll(actionPool);
	}

	@Override
	public void schedule(AbstractAction action) {
		todoList.add(action);
	}

	@Override
	public void addPoolAction(AbstractAction action) {
		action.setState(STATE.SCHEDULED);
		actionPool.add(action);
	}

	@Override
	public AbstractAction getNextAction() {
		AbstractAction action = null;
		if (!interruptAction.isEmpty()) {
			action = interruptAction.remove();
		}
		if (action == null && !todoList.isEmpty()) {
			action = searchIn(todoList);
			action = pollAction(todoList, action);
		}
		if (action == null && !actionPool.isEmpty()) {
			action = searchInActionPool();
		}
		return action;
	}

	/**
	 * Searches and removes a action in the action pool that should be performed
	 * next.
	 * 
	 * @return the action, if one was found or {@code null} if no action was
	 *         found.
	 */
	private AbstractAction searchInActionPool() {
		// its good to shuffle here to get a random selection:
		Collections.shuffle(actionPool);
		AbstractAction tempAction = searchIn(actionPool);
		AbstractAction action = null;
		if (tempAction != null) {
			action = tempAction.clone();
			tempAction.updateParameter();
		}
		return action;
	}


	@Override
	public void addInterruptAction(AbstractAction action) {
		action.setState(AbstractAction.STATE.SCHEDULED);
		interruptAction.add(action);
	}

	@Override
	public boolean isInterruptScheduled() {
		return !interruptAction.isEmpty();
	}

	/**
	 * Searches the next action in a provided tree set
	 * 
	 * @param actionSet
	 *            the set to search in.
	 * @return the action or {@code null}, if no action was found.
	 */
	private AbstractAction searchIn(Collection<AbstractAction> actionSet) {
		AbstractAction action = null;
		AbstractAction tempAction = null;
		// An action without start and end time
		AbstractAction noLimitAction = null;
		// Search the action with the earliest deadline:
		for (AbstractAction a : actionSet) {
			// Deadline exists and Deadline is lower than previous deadline?
			if (a.getDeadline() != null
					&& (tempAction == null || tempAction.getDeadline().after(
							a.getDeadline()))) {
				// If start time exists, start time must be before current time:
				if (a.getEarliestStartTime() == null
						|| a.getEarliestStartTime().before(
								SimulationClock.getInstance().getCurrentTime())) {
					tempAction = a;
				}
			}

		}
		if (tempAction == null) {
			for (AbstractAction a : actionSet) {
				if (a.getEarliestStartTime() != null
						&& a.getEarliestStartTime().before(
								SimulationClock.getInstance().getCurrentTime())) {
					if (tempAction == null
							|| tempAction.getEarliestStartTime().after(
									a.getEarliestStartTime())) {
						tempAction = a;
					}
				}
				if (a.getEarliestStartTime() == null && a.getDeadline() == null
						&& noLimitAction == null) {
					noLimitAction = a;
				}
			}
		}
		// Found a good action, poll:
		action = tempAction;
		// Still no action found. Is there an everytime action?
		if (action == null) {
			action = noLimitAction;
		}

		return action;
	}

	/**
	 * Removes the provided action from the set
	 * 
	 * @param actionSet
	 *            the set to remove the action from
	 * @param action
	 *            the action to remove
	 * @return the action again
	 */
	private AbstractAction pollAction(TreeSet<AbstractAction> actionSet,
			AbstractAction action) {
		actionSet.remove(action);
		return action;
	}

	@Override
	public List<AbstractAction> getTodoListCopy() {
		return new ArrayList<AbstractAction>(todoList);
	}

	@Override
	public List<AbstractAction> getActionPoolCopy() {
		return new ArrayList<AbstractAction>(actionPool);
	}

	@Override
	public List<AbstractAction> getInterruptListCopy() {
		return new ArrayList<AbstractAction>(interruptAction);
	}

}
