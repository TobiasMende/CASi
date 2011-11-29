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

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.uniluebeck.imis.casi.simulation.model.Room;

/**
 * This class provides a solver using A<sup>*</sup> which is able to find a path
 * in a given room.
 * 
 * @author Tobias Mende
 * 
 */
public class InRoomPathSolver extends AStar<Point> {
	private Shape room;
	private Point destination;

	/**
	 * Constructor for a solver that solves path finding problems in rooms
	 * 
	 * @param room
	 *            the room to search path in
	 * @param destination
	 *            the destination point
	 * @throws IllegalArgumentException
	 *             if the destination is not in the room.
	 */
	public InRoomPathSolver(Room room, Point destination)
			throws IllegalArgumentException {
		this.room = room.getShapeRepresentation();
		this.destination = destination;
		if (!this.room.contains(destination)) {
			throw new IllegalArgumentException(
					"The destination is not in the room. Can't get path for it.");
		}
	}

	@Override
	protected boolean isDestination(Point node) {
		return node.equals(destination);
	}

	@Override
	protected double costs(Point from, Point to) {
		return from.distance(to);
	}

	@Override
	protected double heuristic(Point from, Point to) {
		return from.distance(destination);
	}

	@Override
	protected List<Point> calculateSuccessors(Point node) {
		List<Point> successors = new LinkedList<Point>();
		List<Point> temp = new ArrayList<Point>(8);
		/*
		 * Generating points so that (- marks the node): + + + + - + + + +
		 */
		temp.add(new Point(node.x - 1, node.y - 1));
		temp.add(new Point(node.x + 1, node.y - 1));
		temp.add(new Point(node.x, node.y - 1));
		temp.add(new Point(node.x - 1, node.y + 1));
		temp.add(new Point(node.x + 1, node.y + 1));
		temp.add(new Point(node.x, node.y + 1));
		temp.add(new Point(node.x - 1, node.y));
		temp.add(new Point(node.x + 1, node.y));
		// Add only points, that are in the room.
		for (Point p : temp) {
			if (room.contains(p)) {
				successors.add(p);
			}
		}
		return successors;
	}

	@Override
	public List<Point> compute(Point start) {
		if (start.equals(destination)) {
			List<Point> path = new LinkedList<Point>();
			path.add(start);
			return path;
		}
		return super.compute(start);
	}

}
