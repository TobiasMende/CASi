package de.uniluebeck.imis.casi.controller;
/**
 * Interface for ui controllers. Implement it to provide a ui/ gui controller
 * @author Tobias Mende
 *
 */
public interface IUIController {
	public void startSimulation();
	public void stopSimulation();
	public void pauseSimulation();
	
	public boolean setClockScale(double scale);
	
	public void terminate();
}
