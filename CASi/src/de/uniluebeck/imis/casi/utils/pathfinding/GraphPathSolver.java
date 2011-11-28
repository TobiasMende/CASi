package de.uniluebeck.imis.casi.utils.pathfinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The GraphPathSolver is able to find paths in a graph, based on an adjacency matrix.
 * 
 * @author Tobias Mende
 * 
 */
public class GraphPathSolver extends AStar<Integer> {
	/** A set of door identifiers that identify destination doors */
	private Set<Integer> destinationIdentifiers;
	/** The adjacency matrix for the graph */
	private double[][] adjacency;

	/**
	 * Constructor for a new Graph path finder
	 * @param destinations a set of destination node identifiers
	 * @param adjacency the adjacency matrix
	 */
	public GraphPathSolver(Set<Integer> destinations, double[][] adjacency) {
		super();
		this.destinationIdentifiers = destinations;
		this.adjacency = adjacency;
	}
	
	/**
	 * Constructor for a new Graph path finder
	 * @param destination a destination node identifier
	 * @param adjacency the adjacency matrix
	 */
	public GraphPathSolver(int destination, double[][] adjacency) {
		super();
		destinationIdentifiers = new HashSet<Integer>();
		destinationIdentifiers.add(destination);
		this.adjacency = adjacency;
	}
	

	@Override
	protected boolean isDestination(Integer node) {
		return destinationIdentifiers.contains(node);
	}

	@Override
	protected double g(Integer from, Integer to) {
		return adjacency[from.intValue()][to.intValue()];
	}

	@Override
	protected double h(Integer from, Integer to) {
		// TODO add better heuristic if possible
		return 1;
	}

	@Override
	protected List<Integer> calculateSuccessors(Integer node) {
		List<Integer> successors = new ArrayList<Integer>();
		for (int i = 0; i < adjacency[node.intValue()].length; i++) {
			if (adjacency[node.intValue()][i] >= 0) {
				successors.add(i);
			}
		}
		return successors;
	}
	
	@Override
	public List<Integer> compute(Integer start) {
		if(destinationIdentifiers.contains(start)) {
			List<Integer> path= new LinkedList<Integer>();
			path.add(start);
			return path;
		}
		return super.compute(start);
	}
}
