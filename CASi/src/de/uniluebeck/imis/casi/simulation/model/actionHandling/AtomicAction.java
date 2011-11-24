package de.uniluebeck.imis.casi.simulation.model.actionHandling;

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;


/**
 * An abstract class that represents atomic actions. Inherit from it to
 * provide an atomic action (an action without subactions)
 * 
 * @author Tobias Mende
 * 
 */
public abstract class AtomicAction extends AbstractAction {

	private static final long serialVersionUID = -2572027179785943085L;

	@Override
	public final boolean perform(AbstractComponent performer) throws IllegalAccessException {
		if (!isPerformable()) {
			throw new IllegalAccessException("Can't perform an abstract action");
		}
		if (isCompleted()) {
			// stop performing if completed
			return true;
		}
		/*
		 * XXX we need some good ideas how to handle the perform and continue
		 * actions.
		 * 
		 * Current Problem:
		 * 
		 * - Should we call the internalPerform()-Method in every tick?
		 * 
		 * What must this method do then?
		 */
		setState(STATE.ONGOING);

		if (internalPerform(null)) {
			setState(STATE.COMPLETED);
			return true;
		}
		if (getDurationSeconds() > 0) {
			// if duration was set for this action. decrement.
			super.decrementDurationTime();
			if (getDurationSeconds() == 0) {
				// if time elapsed, set state to completed
				setState(STATE.COMPLETED);
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
	protected final void decrementDurationTime() throws IllegalAccessException{
		throw new IllegalAccessException("Don't decrement the duration! It's decremented automatically");
	}
	

}
