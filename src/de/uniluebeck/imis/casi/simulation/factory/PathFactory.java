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
package de.uniluebeck.imis.casi.simulation.factory;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.IPosition;
import de.uniluebeck.imis.casi.simulation.model.Path;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.utils.pathfinding.GraphPathSolver;
import de.uniluebeck.imis.casi.utils.pathfinding.InRoomPathSolver;

/**
 * The Path Factory is able to calculate paths by using some path tracking
 * algorithms
 * 
 * @author Tobias Mende
 * 
 */
public class PathFactory {
	/** The development logger */
	private static final Logger log = Logger.getLogger(PathFactory.class
			.getName());

	/**
	 * Calculates a path from one position to another. This method uses the
	 * central point of each position as concrete position.
	 * 
	 * @param start
	 *            the start position
	 * @param end
	 *            the end position
	 * @return a path that describes the w or <code>null</code> if no path was
	 *         found.
	 */
	public static Path findPath(IPosition start, IPosition end) {
		if ((start instanceof Door) && (end instanceof Door)) {
			// start and end are doors
			return findDoorToDoorPath((Door) start, (Door) end);
		}
		Point2D startPoint = start.getCentralPoint();
		Point2D endPoint = end.getCentralPoint();
		Room room = WorldFactory.getRoomWithPoints(startPoint, endPoint);
		if (room != null) {
			// Start and end are in the same room
			return findPathInRoom(startPoint, endPoint, room);
		}

		// Last case: different rooms
		Room startRoom = WorldFactory.getRoomsWithPoint(startPoint).getFirst();
		Room endRoom = WorldFactory.getRoomsWithPoint(endPoint).getFirst();
		Set<Door> startDoors = startRoom.getDoors();
		Set<Door> endDoors = endRoom.getDoors();
		Path doorToDoor = findRoomToRoomPath(startDoors, endDoors);
		if(doorToDoor == null) {
			return null;
		}
		Point2D startDoorPoint = doorToDoor.getFirst();
		Point2D endDoorPoint = doorToDoor.getLast();
		startRoom = WorldFactory.getRoomWithPoints(startPoint, startDoorPoint);
		endRoom = WorldFactory.getRoomWithPoints(endPoint, endDoorPoint);
		if (startRoom == null || endRoom == null) {
			log.warning("Can't find a start or end room! startRoom = "+startRoom+", endRoom = "+endRoom);
			return null;
		}

		Path startPath = findPathInRoom(startPoint, startDoorPoint, startRoom);
		Path endPath = findPathInRoom(endDoorPoint, endPoint, endRoom);
		startPath.removeLast();
		endPath.removeFirst();
		Path totalPath = new Path(startPoint, endPoint);
		totalPath.addAll(startPath);
		totalPath.addAll(doorToDoor);
		totalPath.addAll(endPath);
		return totalPath;

	}

	/**
	 * Method for finding the path from one room to another by using a set of
	 * doors of the start room and a set of doors of the end room
	 * 
	 * @param startDoors
	 *            the doors of the start room
	 * @param endDoors
	 *            the doors of the end room
	 * @return a path from one start door to one door of the end room.
	 */
	public static Path findRoomToRoomPath(Set<Door> startDoors,
			Set<Door> endDoors) {
		// Get identifiers for end doors:
		if(endDoors.isEmpty() || startDoors.isEmpty()) {
			CASi.SIM_LOG.warning("No doors in start or end room");
			return null;
		}
		Set<Integer> identifiers = new HashSet<Integer>();
		for (Door d : endDoors) {
			identifiers.add(d.getIntIdentifier());
		}
		GraphPathSolver solver = null;
		try {
			solver = new GraphPathSolver(identifiers, SimulationEngine
					.getInstance().getWorld().getDoorGraph());
		} catch (IllegalAccessException e) {
			log.severe("This shouldn't happen. Don't call this method at this time! "
					+ e.fillInStackTrace());
		}
		// Find path for each start but take only the one with a minimum amount
		// of doors:
		List<Integer> minimumPath = new LinkedList<Integer>();
		for (Door d : startDoors) {
			List<Integer> path = solver.compute(d.getIntIdentifier());
			if (minimumPath == null || path.size() < minimumPath.size()
					|| minimumPath.isEmpty()) {
				minimumPath = path;
			}
		}
		// Find door to door path on the shortest path
		Door startDoor = WorldFactory.findDoorForIdentifier(minimumPath.get(0));
		Door endDoor = WorldFactory.findDoorForIdentifier(minimumPath
				.get(minimumPath.size() - 1));
		Path doorToDoorPath = findDoorToDoorPath(startDoor, endDoor);
		return doorToDoorPath;
	}

	/**
	 * Method finds a path between doors
	 * 
	 * @return the path between two doors or <code>null</code> if no path was
	 *         found.
	 */
	public static Path findDoorToDoorPath(Door start, Door end) {
		double[][] adjacency;
		if (start.equals(end)) {
			Path result = new Path(start.getCentralPoint(),
					end.getCentralPoint());
			result.add(start.getCentralPoint());
			return result;
		}
		try {
			adjacency = SimulationEngine.getInstance().getWorld()
					.getDoorGraph();
			if (adjacency[start.getIntIdentifier()][end.getIntIdentifier()] > 0) {
				Path test = SimulationEngine.getInstance().getWorld()
						.getDoorPath(start, end);
				if (test != null) {
					// Path was calculated yet
					return test;
				}
				// doors are adjacent but there is no calculated path:
				Set<Door> doorSet = new HashSet<Door>();
				doorSet.add(start);
				doorSet.add(end);
				// two doors can belong to multiple rooms (in most cases they do
				// not):
				Room fittingRoom = WorldFactory.getRoomWithPoints(start.getCentralPoint(), end.getCentralPoint());
				if (fittingRoom == null) {
					log.severe("No room was found. This shouldn't happen!");
					return null;
				}
				// Take the first room and calculate a path:
				InRoomPathSolver solver = new InRoomPathSolver(fittingRoom, end.getCentralPoint());
				List<Point2D> tempPath = solver
						.compute(start.getCentralPoint());
				Path finalPath = new Path(start.getCentralPoint(),
						end.getCentralPoint());
				finalPath.addAll(tempPath);
				return finalPath;
			}
		} catch (IllegalAccessException e) {
			log.severe("This shouldn't happen. Don't call this method at this time! "
					+ e.fillInStackTrace());
		}
		// start and end are adjacent:
		// Last case: doors arn't adjacent:
		return findDoorToDoorPathOfNonAdjacentDoors(start, end);
	}

	/**
	 * Method for finding a path between two non adjacent doors
	 * 
	 * @param start
	 *            the start door
	 * @param end
	 *            the end door
	 * @return the path between the doors
	 */
	private static Path findDoorToDoorPathOfNonAdjacentDoors(Door start,
			Door end) {
		// Doors arn't adjacent, so search in door graph.
		Set<Integer> temp = new HashSet<Integer>();
		temp.add(end.getIntIdentifier());
		GraphPathSolver solver = null;
		try {
			solver = new GraphPathSolver(temp, SimulationEngine.getInstance()
					.getWorld().getDoorGraph());
		} catch (IllegalAccessException e) {
			log.severe("This shouldn't happen. Don't call this method at this time! "
					+ e.fillInStackTrace());
		}
		// Computes a list of identifiers for door nodes.
		List<Integer> nodes = solver.compute(start.getIntIdentifier());
		Path path = new Path(start.getCentralPoint(), end.getCentralPoint());
		Iterator<Integer> iter = nodes.iterator();
		Door first;
		if (iter.hasNext()) {
			first = WorldFactory.findDoorForIdentifier(iter.next());
		} else {
			return null;
		}
		while (true) {
			Door second;
			if (iter.hasNext()) {
				second = WorldFactory.findDoorForIdentifier(iter.next());
			} else {
				break;
			}
			// This path should be in the door path matrix or at least it should
			// be a path between adjacent doors:
			path.addAll(findDoorToDoorPath(first, second));
			// Save the second door as first door to start here:
			first = second;
		}
		return path;
	}


	/**
	 * Method for calculating a path in a given room
	 * 
	 * @param start
	 *            the start point
	 * @param end
	 *            the end point
	 * @param room
	 *            the room to search in
	 * @return a path if one was found or <code>null</code> otherwise.
	 */
	private static Path findPathInRoom(Point2D start, Point2D end, Room room) {
		InRoomPathSolver solver = new InRoomPathSolver(room, end);
		List<Point2D> path = solver.compute(start);
		Path finalPath = new Path(start, end);
		finalPath.addAll(path);
		return finalPath;
	}

	/**
	 * Method for finding a path in a given set. A path is found, if there
	 * exists a path so that one of the following conditions is true:
	 * <ul>
	 * <li> <code>a.startPoint.equals(start) && a.endPoint.equals(end)</code>
	 * <li> <code>a.startPoint.equals(end) && a.endPoint.equals(start)</code>, in
	 * this case, the path is returned in reversed order.
	 * </ul>
	 * 
	 * @param paths
	 *            the set to search in
	 * @param start
	 *            the start point
	 * @param end
	 *            the end point
	 * @return the path or <code>null</code> if no matching path was found
	 */
	public static Path findPathInSet(Set<Path> paths, Point2D start, Point2D end) {
		Path p = new Path(start, end);
		boolean reversed = false;
		if (!paths.contains(p)) {
			p = new Path(end, start);
			if (!paths.contains(p)) {
				return null;
			}
			reversed = true;
		}
		for (Path path : paths) {
			if (!path.equals(p)) {
				continue;
			}
			if (reversed) {
				return path.reversed();
			} else {
				return path;
			}
		}
		return null;
	}
}
