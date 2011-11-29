/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende
 *
 *  This program is free software. It is licensed under the
 *  GNU Lesser General Public License with one clarification.
 *  
 *  You should have received a copy of the 
 *  GNU Lesser General Public License along with this program. 
 *  See the LICENSE.txt file in this projects root folder or visit
 *  <http://www.gnu.org/licenses/lgpl.html> for more details.
 */
package de.uniluebeck.imis.casi.utils.pathfinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The GraphPathSolver is able to find paths in a graph, based on an adjacency
 * matrix.
 * 
 * @author Tobias Mende
 * 
 */
public class GraphPathSolver extends AStar<Integer> {
	/** A set of door identifiers that identify destination doors */
	private Set<Integer> destinationIdentifiers;
	/** The adjacency matrix for the graph */
	private double[][] adjacency;
	private double[] heuristics;

	/**
	 * Constructor for a new Graph path finder
	 * 
	 * @param destinations
	 *            a set of destination node identifiers
	 * @param adjacency
	 *            the adjacency matrix
	 */
	public GraphPathSolver(Set<Integer> destinations, double[][] adjacency) {
		super();
		this.destinationIdentifiers = destinations;
		this.adjacency = adjacency;
		preCalculateHeuristics();
	}

	/**
	 * Constructor for a new Graph path finder
	 * 
	 * @param destination
	 *            a destination node identifier
	 * @param adjacency
	 *            the adjacency matrix
	 */
	public GraphPathSolver(int destination, double[][] adjacency) {
		super();
		destinationIdentifiers = new HashSet<Integer>();
		destinationIdentifiers.add(destination);
		this.adjacency = adjacency;
		preCalculateHeuristics();
	}

	private void preCalculateHeuristics() {
		heuristics = new double[adjacency.length];
		for (int i = 0; i < heuristics.length; i++) {
			double minimum = Double.MAX_VALUE;
			for (int j = 0; j < adjacency[i].length; j++) {
				if (i != j && adjacency[i][j] >= 0 && adjacency[i][j] < minimum) {
					minimum = adjacency[i][j];
				}
			}
			heuristics[i] = minimum;
		}
	}

	@Override
	protected boolean isDestination(Integer node) {
		return destinationIdentifiers.contains(node);
	}

	@Override
	protected double costs(Integer from, Integer to) {
		return adjacency[from.intValue()][to.intValue()];
	}

	@Override
	protected double heuristic(Integer from, Integer to) {
		// TODO: add better heuristic if possible
		// IDEA: take the minimum costs over all possible next steps as
		// heuristic
		return heuristics[from.intValue()];
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
		if (destinationIdentifiers.contains(start)) {
			List<Integer> path = new LinkedList<Integer>();
			path.add(start);
			return path;
		}
		return super.compute(start);
	}
}
