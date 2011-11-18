package de.uniluebeck.imis.casi.simulation.engine;

import de.uniluebeck.imis.casi.simulation.model.SimulationTime;

public interface ISimulationClockListener {
	public void timeChanged(SimulationTime newTime);
}
