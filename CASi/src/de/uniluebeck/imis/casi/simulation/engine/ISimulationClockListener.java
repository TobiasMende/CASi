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

package de.uniluebeck.imis.casi.simulation.engine;

import de.uniluebeck.imis.casi.simulation.model.SimulationTime;

/**
 * Interface for classes that listen on clock events
 * 
 * @author Tobias Mende
 * 
 */
public interface ISimulationClockListener {

	/**
	 * Listener Event: The time has changed (in most cases: incremented by one
	 * second)
	 * 
	 * @param newTime
	 *            the new time
	 */
	public void timeChanged(SimulationTime newTime);

	/**
	 * Listener Event: Pause state has changed
	 * 
	 * @param pause
	 *            if <code>true</code> the simulation is paused, if
	 *            <code>false</code> the simulation continues
	 */
	public void simulationPaused(boolean pause);

	/**
	 * Listener Event: Simulation was stopped (finished)
	 */
	public void simulationStopped();

	/**
	 * Listener Event: Simulation was started
	 */
	public void simulationStarted();
}
