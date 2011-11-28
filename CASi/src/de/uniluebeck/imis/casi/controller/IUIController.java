package de.uniluebeck.imis.casi.controller;

import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;

/**
 * Interface for ui controllers. Implement it to provide a ui/ gui controller
 * 
 * @author Tobias Mende
 * 
 *         TODO do we need this interface? All functionalities are provided by
 *         the {@link SimulationEngine}, the {@link SimulationClock} or the
 *         {@link MainController}. But perhaps it's good to encapsulate these
 *         functionalities
 */
public interface IUIController {
	/**
	 * Method for starting the simulation
	 */
	public void startSimulation();

	/**
	 * Method for finally stopping the simulation
	 */
	public void stopSimulation();

	/**
	 * Method for pausing and continuing a simulation
	 */
	public void pauseSimulation();

	/**
	 * Method for setting the clock scale
	 * 
	 * @param scale
	 *            the factor to set
	 * @return <code>true</code> if the factor was set successful,
	 *         <code>false</code> otherwise.
	 */
	public boolean setClockScale(int scale);

	/**
	 * Method for terminating the simulation
	 */
	public void terminate();
}
