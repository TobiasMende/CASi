package de.uniluebeck.imis.casi.utils.pathfinding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Logger;
/**
 * This class implements the behavior of the A<sup>*</sup> algorithm. Subclasses can provide a customize behavior.
 * @author Tobias Mende
 *
 * @param <T> the type of nodes that are handled by this class
 */
public abstract class AStar<T> {
	/** The development logger */
	protected static final Logger log = Logger.getLogger(AStar.class.getName());
	
	/** A queue of paths that are use to solve the problem */
	private PriorityQueue<SolvePath> paths = new PriorityQueue<SolvePath>();
	/** A map that saves the minimum distances to each node */
	private HashMap<T, Double> minimumDistance = new HashMap<T, Double>();
	/** The costs of the last iteration */
	private double lastCosts = 0.0;
	/** Counter for the expandation of the path*/
	private int expandationCounter = 0;
	
	/**
	 * Checks whether a given node is the destination or not.
	 * @param node the node to check
	 * @return <code>true</code> if the node is destination, <code>false</code> otherwise.
	 */
	protected abstract boolean isDestination(T node);
	
	/**
	 * Calculates the costs for getting from the node <code>from</code> to the node <code>to</code>
	 * @param from the previous node
	 * @param to the next node
	 * @return the costs
	 */
	protected abstract double costs(T from, T to);
	
	/**
	 * Calculates an optimistic heuristic which should never be bigger than the real costs. In graphic cases linear distance is an acceptable heuristic.
	 * @param from the previous node
	 * @param to the next node
	 * @return the estimated costs as heuristic
	 */
	protected abstract double heuristic(T from, T to);
	
	/**
	 * Calculates a list of successors for a given node
	 * @param node the node to get successors for
	 * @return a list of successors (meaning possible steps)
	 */
	protected abstract List<T> calculateSuccessors(T node);
	
	/**
	 * Getter for the expandation, meaning how many times a node was expanded.
	 * @return the amount of expandations.
	 */
	public int getExpandationCounter() {
		return expandationCounter;
	}
	
	/**
	 * Calculates <code>f(x) = g(x) + h(x)</code> which represents the minimum total costs.
	 * @param path the path we use
	 * @param from the start node
	 * @param to the end node
	 * @return the total costs
	 */
	protected double totalCosts(SolvePath path, T from, T to) {
		double gOfParent = 0.0;
		if(path.parent != null) {
			gOfParent = path.parent.g;
		} 
		double g = costs(from, to) + gOfParent;
		double h = heuristic(from, to);
		
		path.g = g;
		path.f = g+h;
		return path.f;
	}
	
	/**
	 * Method for expanding a path
	 * @param path the path to expand
	 */
	private void expand(SolvePath path) {
		T point = path.getPoint();
		Double min = minimumDistance.get(path.getPoint());
		/*
		 * If a better path already exists, don't expand
		 */
		if(min == null || min.doubleValue() > path.f) {
			minimumDistance.put(path.getPoint(), path.f);
		} else {
			return;
		}
		List<T> successors = calculateSuccessors(point);
		for(T node : successors) {
			SolvePath newPath = new SolvePath(path);
			newPath.setPoint(node);
			totalCosts(newPath, path.getPoint(), node);
			paths.offer(newPath);
		}
		expandationCounter++;
	}
	
	/**
	 * Getter for the costs for reaching the last node on the path.
	 * @return the costs for the found path.
	 */
	public double getLastCosts() {
		return lastCosts;
	}
	
	/**
	 * Computes the shortest path to the destination from the <code>start</code> node
	 * @param start the start node
	 * @return a list of nodes
	 */
	public List<T> compute(T start) {
		cleanUp();
		SolvePath root = new SolvePath();
		root.setPoint(start);
		totalCosts(root, start, start);
		expand(root);
		while(true) {
			SolvePath path = paths.poll();
			if(path == null) {
				lastCosts = Double.MAX_VALUE;
				return null;
			}
			
			T last = path.getPoint();
			lastCosts = path.g;
			if(isDestination(last)) {
				LinkedList<T> finalPath = new LinkedList<T>();
				for(SolvePath current = path; current != null; current = current.parent) {
					finalPath.addFirst(current.point);
				}
				return finalPath;
			}
			expand(path);
			
		}
	}
	/**
	 * Method for cleaning up. Fixes issues when calling compute multiple times.
	 */
	private void cleanUp() {
		paths = new PriorityQueue<SolvePath>();
		minimumDistance = new HashMap<T, Double>();
		lastCosts = 0.0;
		expandationCounter = 0;
	}
	
	/**
	 * The solve path represents paths in the graph, used to solve the problem
	 * @author Tobias Mende
	 *
	 */
	private class SolvePath implements Comparable<SolvePath> {
		/** The current point on the path */
		private T point;
		/** the total coasts */
		private double f;
		/** The costs for this path*/
		private double g;
		/** The parent path*/
		private SolvePath parent;
		
		/**
		 * Default constructor
		 */
		public SolvePath() {
			parent = null;
			point = null;
			g = 0.0;
			f = 0.0;
		}
		
		/**
		 * Constructor for getting a new path and adding a parent path
		 * @param parent the parent path to add
		 */
		public SolvePath(SolvePath parent) {
			this();
			this.parent = parent;
			g = parent.g;
			f = parent.f;
		}

		@Override
		public int compareTo(SolvePath other) {
			return (int)(this.f - other.f);
		}
		/**
		 * Getter for the point
		 * @return the node
		 */
		public T getPoint() {
			return point;
		}
		
		/**
		 * Setter for the point
		 * @param point a node to set as point
		 */
		public void setPoint(T point) {
			this.point = point;
		}
		
	}
}
