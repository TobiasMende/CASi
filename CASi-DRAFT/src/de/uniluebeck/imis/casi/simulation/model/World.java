package de.uniluebeck.imis.casi.simulation.model;

import java.util.Collection;

public class World {

	private Collection<Room> rooms;
	private Collection<Agent> agents;
	private Collection<AbstractActuator> actuators;
	private Collection<AbstractSensor> sensors;
	
	/** Collection of components that are whether agents, actuators nor sensors*/
	private Collection<AbstractComponent> components;
	private SimulationTime startTime;
	private SimulationTime currentTime;
	
	private boolean sealed;
	
	public void seal() {
		sealed = true;
	}

	public Collection<Room> getRooms() throws IllegalAccessException {
		if(!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return rooms;
	}

	public Collection<Agent> getAgents() throws IllegalAccessException {
		if(!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return agents;
	}

	public Collection<AbstractActuator> getActuators() throws IllegalAccessException {
		if(!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return actuators;
	}

	public Collection<AbstractSensor> getSensors() throws IllegalAccessException {
		if(!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return sensors;
	}

	public Collection<AbstractComponent> getComponents() throws IllegalAccessException {
		if(!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return components;
	}

	public SimulationTime getStartTime() {
		return startTime;
	}

	public boolean isSealed() {
		return sealed;
	}

	public void setRooms(Collection<Room> rooms) throws IllegalAccessException {
		if(sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.rooms = rooms;
	}

	public void setAgents(Collection<Agent> agents) throws IllegalAccessException {
		if(sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.agents = agents;
	}

	public void setActuators(Collection<AbstractActuator> actuators) throws IllegalAccessException {
		if(sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.actuators = actuators;
	}

	public void setSensors(Collection<AbstractSensor> sensors) throws IllegalAccessException {
		if(sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.sensors = sensors;
	}

	public void setComponents(Collection<AbstractComponent> components) throws IllegalAccessException {
		if(sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.components = components;
	}

	public void setStartTime(SimulationTime startTime) throws IllegalAccessException {
		if(sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.startTime = startTime;
		this.currentTime = startTime;
	}
	
	public void setCurrentTime(SimulationTime currentTime) {
		this.currentTime = currentTime;
	}
	
	public SimulationTime getCurrentTime() {
		return currentTime;
	}
	
	
}
