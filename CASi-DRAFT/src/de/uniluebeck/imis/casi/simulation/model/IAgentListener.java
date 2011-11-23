package de.uniluebeck.imis.casi.simulation.model;

public interface IAgentListener {
	public void stateChanged(Agent.STATE newState);
	public void positionChanged(IPosition oldPosition, IPosition newPosition);
}
