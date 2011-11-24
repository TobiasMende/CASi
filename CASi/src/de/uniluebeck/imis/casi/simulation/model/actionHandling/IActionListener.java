package de.uniluebeck.imis.casi.simulation.model.actionHandling;
/**
 * Interfaces for objects that listen to events on actions
 * @author Tobias Mende
 *
 */
public interface IActionListener {
	/**
	 * Method is called if a new state was set.
	 * @param newState the new state
	 */
	public void stateChanged(AbstractAction.STATE newState);
}
