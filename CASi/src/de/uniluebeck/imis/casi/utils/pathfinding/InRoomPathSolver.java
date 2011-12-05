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
import java.awt.geom.Point2D;
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
public class InRoomPathSolver extends AStar<Point2D> {
	private Shape room;
	private Point2D destination;

	/**
	 * Constructor for a solver that solves path finding problems in rooms
	 * 
	 * @param room
	 *            the room to search path in
	 * @param this.destination
	 *            the destination point
	 * @throws IllegalArgumentException
	 *             if the destination is not in the room.
	 */
	public InRoomPathSolver(Room room, Point2D end)
			throws IllegalArgumentException {
		this.room = room.getShapeRepresentation();
		this.destination = end;
		if (!this.room.contains(this.destination)) {
			throw new IllegalArgumentException(
					"The destination is not in the room. Can't get path for it.");
		}
	}

	@Override
	protected boolean isDestination(Point2D node) {
		return node.equals(destination);
	}

	@Override
	protected double costs(Point2D from, Point2D to) {
		return from.distance(to);
	}

	@Override
	protected double heuristic(Point2D from, Point2D to) {
		return from.distance(destination);
	}

	@Override
	protected List<Point2D> calculateSuccessors(Point2D point) {
		Point node = new Point((int)Math.round(point.getX()), (int)Math.round(point.getY()));
		List<Point2D> successors = new LinkedList<Point2D>();
		List<Point2D> temp = new ArrayList<Point2D>(8);
		/*
		 * Generating points so that (- marks the node): + + + + - + + + +
		 */
		temp.add((Point2D)new Point(node.x - 1, node.y - 1));
		temp.add((Point2D)new Point(node.x + 1, node.y - 1));
		temp.add((Point2D)new Point(node.x, node.y - 1));
		temp.add((Point2D)new Point(node.x - 1, node.y + 1));
		temp.add((Point2D)new Point(node.x + 1, node.y + 1));
		temp.add((Point2D)new Point(node.x, node.y + 1));
		temp.add((Point2D)new Point(node.x - 1, node.y));
		temp.add((Point2D)new Point(node.x + 1, node.y));
		// Add only points, that are in the room.
		for (Point2D p : temp) {
			if (room.contains(p)) {
				successors.add(p);
			}
		}
		return successors;
	}

	@Override
	public List<Point2D> compute(Point2D start) {
		Point2D temp = new Point((int)Math.round(start.getX()), (int)Math.round(start.getY()));
		if (start.equals(destination)) {
			List<Point2D> path = new LinkedList<Point2D>();
			path.add(temp);
			return path;
		}
		return super.compute(temp);
	}

}
