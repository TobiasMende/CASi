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
package de.uniluebeck.imis.casi.simulation.model.actionHandling;

import java.util.Collection;
import java.util.Vector;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;

/**
 * An abstract class that represents a complex action. Inherit from it to
 * provide a complex action (an action that consists of subactions)
 * 
 * @author Tobias Mende
 * 
 */
public class ComplexAction extends AbstractAction {
	private static final long serialVersionUID = 7868816596381779417L;
	/** a collection of actions this action consists of */
	private Collection<AtomicAction> subActions = new Vector<AtomicAction>();
	/** the current action */
	private AbstractAction currentAction;

	@Override
	public final boolean perform(AbstractComponent performer)
			throws IllegalAccessException {
		if (!isPerformable()) {
			throw new IllegalAccessException("Can't perform an abstract action");
		}
		if (isCompleted()) {
			// stop performing if completed
			postActionTask(performer);
			return true;
		}
		if (!initialized) {
			if (!preActionTask(performer)) {
				setState(STATE.INTERRUPTED);
				return false;
			}
			initialized = true;
		}
		setState(STATE.ONGOING);
		boolean completed = true;
		for (AbstractAction a : subActions) {
			currentAction = a;
			completed &= a.perform(performer);
			setState(a.getState());
			if (!completed) {
				// don't continue if last subaction wasn't completed yet
				break;
			}
		}
		if (completed) {
			// set state to completed if all subactions were completed
			setState(STATE.COMPLETED);
			postActionTask(performer);
			return true;
		}
		return false;
	}

	@Override
	public final int getDuration() {
		return (int) Math.round(getDurationSeconds() / 60);
	}

	/**
	 * Method for adding an atomic subaction. Makes this action complex
	 * 
	 * @param action
	 *            the action to add as subaction
	 * @throws UnsupportedOperationException
	 *             if the provided action isn't atomic or the action to which
	 *             the subaction should be added is a subaction itself.
	 */
	public final synchronized void addSubAction(AtomicAction action) {
		subActions.add(action);
	}

	/**
	 * Removes a subaction from the list. If all subactions are deleted, the
	 * type of this action is atomic
	 * 
	 * @param action
	 *            the action to remove
	 */
	public final synchronized void removeSubAction(AtomicAction action) {
		if (subActions == null || !subActions.remove(action)) {
			return;
		}
	}

	/**
	 * Deletes all subactions and makes this action atomic
	 */
	public final synchronized void clearSubActions() {
		if (subActions == null) {
			return;
		}
		subActions.clear();
	}

	@Override
	public final int getDurationSeconds() {
		int duration = 0;
		for (AtomicAction a : subActions) {
			if (a.getDurationSeconds() > 0) {
				duration += a.getDurationSeconds();
			}
		}
		if (duration == 0) {
			// no duration information provided for all subactions
			duration = -1;
		}
		return duration;
	}

	@Override
	protected final void decrementDurationTime() throws IllegalAccessException {
		CASi.SIM_LOG
				.warning("Decrementing the duration on a complex action has no effect!");
	}

	@Override
	protected final boolean internalPerform(AbstractComponent performer) {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((subActions == null) ? 0 : subActions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexAction other = (ComplexAction) obj;
		if (subActions == null) {
			if (other.subActions != null)
				return false;
		} else if (!subActions.equals(other.subActions))
			return false;
		return true;
	}
	
	@Override
	public void reset() {
		super.reset();
		for(AtomicAction a : subActions) {
			a.reset();
		}
		
		
	}
	
	@Override
	public String toString() {
		return subActions.toString();
	}
	
	/**
	 * Getter for the subaction that is actually performed.
	 * @return the currentAction
	 */
	public AbstractAction getCurrentAction() {
		return currentAction;
	}

}
