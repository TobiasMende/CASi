package de.uniluebeck.imis.casi.simulation.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * World Class that is kind of a root object for a Model tree
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

	/**
	 * Flag for saving whether this world is sealed or not. If sealed, no
	 * changes can be made. Every call to a setter would cause an exception. If
	 * not sealed, the world can not be simulated.
	 */
	private boolean sealed;

	/**
	 * Seals the world
	 */
	public void seal() {
		sealed = true;
		calculateDoorGraph();
	}

	/**
	 * Get all rooms, hold by this world
	 * 
	 * @return a collection of rooms
	 * @throws IllegalAccessException
	 *             if the world isn't sealed
	 */
	public Collection<Room> getRooms() throws IllegalAccessException {
		if (!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return rooms;
	}

	/**
	 * Get all agents that are part of this world.
	 * 
	 * @return a collection of agents
	 * @throws IllegalAccessException
	 *             if the world isn't sealed
	 */
	public Collection<Agent> getAgents() throws IllegalAccessException {
		if (!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return agents;
	}

	/**
	 * Getter for all actuators in this world
	 * 
	 * @return a collection of actuators
	 * @throws IllegalAccessException
	 *             if the world isn't sealed
	 */
	public Collection<AbstractActuator> getActuators()
			throws IllegalAccessException {
		if (!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return actuators;
	}

	/**
	 * Getter for all sensors in this world
	 * 
	 * @return a collection of sensors
	 * @throws IllegalAccessException
	 *             if the world isn't sealed
	 */
	public Collection<AbstractSensor> getSensors()
			throws IllegalAccessException {
		if (!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return sensors;
	}

	/**
	 * Getter for components that are neither actuators nor sensors.
	 * 
	 * @return a collection of unspecified components
	 * @throws IllegalAccessException
	 *             if the world isn't sealed
	 */
	public Collection<AbstractComponent> getComponents()
			throws IllegalAccessException {
		if (!sealed) {
			throw new IllegalAccessException("World isn't sealed!");
		}
		return components;
	}

	/**
	 * Getter for the startTime of this simulation
	 * 
	 * @return the start time
	 */
	public SimulationTime getStartTime() {
		return startTime;
	}

	/**
	 * Method for checking whether this world is sealed or not.
	 * 
	 * @return <code>true</code> if the world is sealed, <code>false</code>
	 *         otherwise.
	 */
	public boolean isSealed() {
		return sealed;
	}

	/**
	 * Setter for the rooms
	 * 
	 * @param rooms
	 *            a collection of rooms to set.
	 * @throws IllegalAccessException
	 *             if the world is sealed.
	 */
	public void setRooms(Collection<Room> rooms) throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.rooms = rooms;
	}

	/**
	 * Setter for agents
	 * 
	 * @param agents
	 *            a collection of agents to set
	 * @throws IllegalAccessException
	 *             if the world is sealed.
	 */
	public void setAgents(Collection<Agent> agents)
			throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.agents = agents;
	}

	/**
	 * Setter for actuators
	 * 
	 * @param actuators
	 *            a collection of actuators
	 * @throws IllegalAccessException
	 *             if the world is sealed.
	 */
	public void setActuators(Collection<AbstractActuator> actuators)
			throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.actuators = actuators;
	}

	/**
	 * Setter for sensors
	 * 
	 * @param sensors
	 *            a collection of sensors to set
	 * @throws IllegalAccessException
	 *             if the world is sealed.
	 */
	public void setSensors(Collection<AbstractSensor> sensors)
			throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.sensors = sensors;
	}

	/**
	 * Setter for unspecified components
	 * 
	 * @param components
	 *            a collection of components that are neither actuators nor
	 *            sensors
	 * @throws IllegalAccessException
	 *             if the world is sealed.
	 */
	public void setComponents(Collection<AbstractComponent> components)
			throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.components = components;
	}

	/**
	 * Setter for the start time in this world
	 * 
	 * @param startTime
	 *            the start time
	 * @throws IllegalAccessException
	 *             if the world is sealed.
	 */
	public void setStartTime(SimulationTime startTime)
			throws IllegalAccessException {
		if (sealed) {
			throw new IllegalAccessException("World is sealed!");
		}
		this.startTime = startTime;
	}

	/**
	 * Getter for the door graph
	 * 
	 * @return an adjacency matrix that holds the adjacencies of doors with the
	 *         specific best case costs (the distance between two doors)
	 */
	public double[][] getDoorGraph() {
		return doorGraph;
	}

	/**
	 * Calculates the adjacency matrix that represents the door graph.
	 */
	private void calculateDoorGraph() {
		int size = Door.getNumberOfDoors();
		doorGraph = new double[size][size];
		initializeDoorGraph();
		for (Room room : rooms) {
			// Get a list of doors for each room
			ArrayList<Door> doors = new ArrayList<Door>();
			for (Wall wall : room.getWalls()) {
				doors.addAll(wall.getDoors());
			}
			// For each door in this room, calculate distances to all other
			// doors of this room.
			calculateAdjacencies(doors);
		}
	}

	/**
	 * Calculates the adjacencies and costs between doors in a given list and
	 * stores them in the doorGraph
	 * 
	 * @param doors
	 *            the list of doors
	 */
	private void calculateAdjacencies(ArrayList<Door> doors) {
		for (Door first : doors) {
			for (Door second : doors) {
				if (first.equals(second)) {
					doorGraph[first.getIntIdentifier()][second
							.getIntIdentifier()] = 0;
					continue;
				}
				double distance = first.getCentralPoint().distance(
						second.getCentralPoint());
				doorGraph[first.getIntIdentifier()][second.getIntIdentifier()] = distance;
			}
		}
	}

	/**
	 * Sets all distances to -1, meaning that the doors arn't adjacent.
	 */
	private void initializeDoorGraph() {
		int size = doorGraph.length;
		double[] init = new double[size];
		for (int i = 0; i < size; i++) {
			init[i] = -1;
		}
		for (int i = 0; i < size; i++) {
			doorGraph[i] = init;
		}
	}
}
