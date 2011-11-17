package de.uniluebeck.imis.casi.simulation.model;

import java.util.Collection;

public class World {

	private Collection<Room> rooms;
	private Collection<Agent> agents;
	private Collection<AbstractActuator> actuators;
	private Collection<AbstractSensor> sensors;
	private Time startTime;
}
