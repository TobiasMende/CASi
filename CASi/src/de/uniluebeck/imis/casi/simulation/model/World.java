package de.uniluebeck.imis.casi.simulation.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * World Class that is kind of a root object for a Model-tree
 * 
 * @author Marvin Frick, Tobias Mende
 * 
 */
public class World {

	private Collection<Room> rooms;
	private Collection<Agent> agents;
	private Collection<AbstractActuator> actuators;
	private Collection<AbstractSensor> sensors;

	/** Collection of components that are neither agents, actuators nor sensors */
	private Collection<AbstractComponent> components;
	private SimulationTime startTime;
	/**
	 * The door graph is a graph with doors as nodes. In this case its an
	 * adjacency matrix that saves the distance between doors if they are in the
	 * same room or <code>-1</code> otherwise.
	 */
	private double[][] doorGraph;

	private boolean sealed;

	public void seal() {
		sealed = true;
		calculateDoorGraph();
	}

	public Collection<Room> getRooms() throws IllegalAccessException {
		if (!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return rooms;
	}

	public Collection<Agent> getAgents() throws IllegalAccessException {
		if (!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return agents;
	}

	public Collection<AbstractActuator> getActuators()
			throws IllegalAccessException {
		if (!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return actuators;
	}

	public Collection<AbstractSensor> getSensors()
			throws IllegalAccessException {
		if (!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return sensors;
	}

	public Collection<AbstractComponent> getComponents()
			throws IllegalAccessException {
		if (!sealed) {
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
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.rooms = rooms;
	}

	public void setAgents(Collection<Agent> agents)
			throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.agents = agents;
	}

	public void setActuators(Collection<AbstractActuator> actuators)
			throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.actuators = actuators;
	}

	public void setSensors(Collection<AbstractSensor> sensors)
			throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.sensors = sensors;
	}

	public void setComponents(Collection<AbstractComponent> components)
			throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.components = components;
	}

	public void setStartTime(SimulationTime startTime)
			throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.startTime = startTime;
	}

	/**
	 * Calculates the adjacency matrix that represents the door graph.
	 */
	private void calculateDoorGraph() {
		int size = Door.getNumberOfDoors();
		doorGraph = new double[size][size];
		initializeDoorGraph();
		for(Room room : rooms) {
			ArrayList<Door> doors = new ArrayList<Door>();
			for(Wall wall : room.getWalls()) {
				doors.addAll(wall.getDoors());
			}
			for(Door first : doors) {
				for(Door second : doors) {
					if(first.equals(second)) {
						doorGraph[first.getIntIdentifier()][second.getIntIdentifier()] = 0;
						continue;
					}
					double distance = first.getCentralPoint().distance(second.getCentralPoint());
					doorGraph[first.getIntIdentifier()][second.getIntIdentifier()] = distance;
				}
			}
		}
	}
	/**
	 * Sets all distances to -1, meaning that the doors arn't adjacent.
	 */
	private void initializeDoorGraph() {
		int size = doorGraph.length;
		double[] init = new double[size];
		for(int i =0; i < size; i++) {
			init[i] = -1;
		}
		for(int i = 0; i < size; i++) {
			doorGraph[i] = init;
		}
	}
}
