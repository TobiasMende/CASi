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

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;

/**
 * An abstract class that represents atomic actions. Inherit from it to provide
 * an atomic action (an action without subactions)
 * 
 * @author Tobias Mende
 * 
 */
public abstract class AtomicAction extends AbstractAction {

	private static final long serialVersionUID = -2572027179785943085L;

	@Override
	public final boolean perform(AbstractComponent performer)
			throws IllegalAccessException {
		if (!isPerformable()) {
			throw new IllegalAccessException("Can't perform an abstract action");
		}
		if (isCompleted()) {
			// stop performing if completed
			return true;
		}
		if(!initialized) {
			if(!preActionTask(performer)) {
				setState(STATE.INTERRUPTED);
				return false;
			}
			initialized = true;
		}
		setState(STATE.ONGOING);

		if (internalPerform(performer)) {
			setState(STATE.COMPLETED);
			postActionTask(performer);
			return true;
		}
		if (getDurationSeconds() > 0) {
			// if duration was set for this action. decrement.
			super.decrementDurationTime();
			if (getDurationSeconds() == 0) {
				// if time elapsed, set state to completed
				setState(STATE.COMPLETED);
				postActionTask(performer);
				return true;
			}
		}
		return false;
	}

	@Override
	public final int getDuration() {
		return (int) Math.round(getDurationSeconds() / 60);
	}

	@Override
	public final int getDurationSeconds() {
		return super.getDurationSeconds();
	}

	@Override
	protected final void decrementDurationTime() throws IllegalAccessException {
		throw new IllegalAccessException(
				"Don't decrement the duration! It's decremented automatically");
	}
	
	

}
