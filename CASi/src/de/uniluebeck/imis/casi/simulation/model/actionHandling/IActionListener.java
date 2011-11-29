/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
 */
package de.uniluebeck.imis.casi.simulation.model.actionHandling;

/**
 * Interfaces for objects that listen to events on actions
 * 
 * @author Tobias Mende
 * 
 */
public interface IActionListener {
	/**
	 * Method is called if a new state was set.
	 * 
	 * @param newState
	 *            the new state
	 */
	public void stateChanged(AbstractAction.STATE newState);
}
