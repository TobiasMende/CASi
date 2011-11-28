package de.uniluebeck.imis.casi.utils.pathfinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.factory.WorldFactory;
import de.uniluebeck.imis.casi.simulation.model.Door;

/**
 * The DoorGraphSolver is able to find paths in the door graph, Meaning a path
 * from one room to another. Internal the door identifiers are used for better performance when searching 
 * 
 * @author Tobias Mende
 * 
 */
public class DoorGraphSolver extends AStar<Integer> {
	/** A set of door identifiers that identify destination doors */
	private Set<Integer> destinationIdentifiers;
	/** A set of destination doors */
	private Set<Door> destinations;

	/**
	 * Constructor for a new DoorGraphSolver
	 * @param destinations a set of destination doors
	 */
	public DoorGraphSolver(Set<Door> destinations) {
		super();
		this.destinations = destinations;
		this.destinationIdentifiers = new HashSet<Integer>();
		for (Door end : destinations) {
			this.destinationIdentifiers.add(end.getIntIdentifier());
		}
	}

	@Override
	protected boolean isDestination(Integer node) {
		return destinationIdentifiers.contains(node);
	}

	@Override
	protected double g(Integer from, Integer to) {
		double[][] doorGraph = SimulationEngine.getInstance().getWorld()
				.getDoorGraph();
		return doorGraph[from.intValue()][to.intValue()];
	}

	@Override
	protected double h(Integer from, Integer to) {
		Door fromDoor = WorldFactory.findDoorForIdentifier(from);
		TreeSet<Double> costs = new TreeSet<Double>();
		for(Door dest : destinations) {
			double cost = fromDoor.getCentralPoint().distance(dest.getCentralPoint());
			costs.add(cost);
		}
		
		return costs.first();
	}

	@Override
	protected List<Integer> calculateSuccessors(Integer node) {
		List<Integer> successors = new ArrayList<Integer>();
		double[][] doorGraph = SimulationEngine.getInstance().getWorld()
				.getDoorGraph();
		for (int i = 0; i < doorGraph.length; i++) {
			if (doorGraph[node.intValue()][i] > 0) {
				successors.add(i);
			}
		}
		return successors;
	}
}
