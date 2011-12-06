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

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;

import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
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
	private TreeSet<AbstractAction> todoList;
	private TreeSet<AbstractAction> actionPool;
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
		this();
		this.todoList.addAll(todoList);
		this.actionPool.addAll(actionPool);
	}

	/**
	 * Default constructor
	 */
	public DefaultActionScheduler() {
		todoList = new TreeSet<AbstractAction>(new ActionComparator());
		actionPool = new TreeSet<AbstractAction>(new ActionComparator());
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
		actionPool.add(action);
	}

	@Override
	public AbstractAction getNextAction() {
		if(!interruptAction.isEmpty()) {
			return interruptAction.remove();
		}
		// FIXME implement!!! It's just a quick fix
		AbstractAction action = null;
		if(!todoList.isEmpty()) {
			if(todoList.first().getEarliestStartTime().before(SimulationClock.getInstance().getCurrentTime())) {
				action = todoList.pollFirst();
			}
		} else {
			if(!actionPool.isEmpty() && actionPool.first().getEarliestStartTime().before(SimulationClock.getInstance().getCurrentTime())) {
				action = actionPool.pollFirst();
			}
		}
		return action;
	}

	@Override
	public void addInterruptAction(AbstractAction action) {
		interruptAction.add(action);
	}

	@Override
	public boolean isInterruptScheduled() {
		return !interruptAction.isEmpty();
	}

}
