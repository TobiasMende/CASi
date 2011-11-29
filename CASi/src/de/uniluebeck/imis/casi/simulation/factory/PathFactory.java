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

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

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
	private static final Logger log = Logger.getLogger(PathFactory.class.getName());
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
		// If both positions are doors, find path between doors
		if ((start instanceof Door) && (end instanceof Door)) {
			return findDoorToDoorPath((Door) start, (Door) end);
		}
		Room startRoom = PositionFactory.getRoomWithPoint(start
				.getCentralPoint());
		Room endRoom = PositionFactory.getRoomWithPoint(end.getCentralPoint());
		// If start and end room are equal, find path in room:
		if (startRoom != null && endRoom != null && startRoom.equals(endRoom)) {
			return findPathInRoom(start, end);
		}
		// Case: Different Rooms:
		Set<Door> startRoomDoors = startRoom.getDoors();
		Set<Door> endRoomDoors = endRoom.getDoors();
		Path totalPath = new Path(start.getCentralPoint(),
				end.getCentralPoint());
		// Get the path between one door of the start room and one door of the end room
		Path doorToDoorPath = findRoomToRoomPath(startRoomDoors, endRoomDoors);
		// Get the path from start point to the start door
		Path startPath = findPathInRoom(start.getCentralPoint(), doorToDoorPath.getStartPoint(), startRoom);
		// Get the path from the end door to the end point
		Path endPath = findPathInRoom(doorToDoorPath.getEndPoint(), end.getCentralPoint(), endRoom);
		// Merging the sub paths together:
		totalPath.addAll(startPath);
		totalPath.addAll(doorToDoorPath);
		totalPath.addAll(endPath);
		return totalPath;
	}

	/**
	 * Method for finding the path from one room to another by using a set of doors of the start room and a set of doors of the end room
	 * @param startDoors the doors of the start room
	 * @param endDoors the doors of the end room
	 * @return a path from one start door to one door of the end room.
	 */
	public static Path findRoomToRoomPath(Set<Door> startDoors,
			Set<Door> endDoors) {
		// Get identifiers for end doors:
		Set<Integer> identifiers = new HashSet<Integer>();
		for (Door d : endDoors) {
			identifiers.add(d.getIntIdentifier());
		}
		GraphPathSolver solver = new GraphPathSolver(identifiers,
				SimulationEngine.getInstance().getWorld().getDoorGraph());
		// Find path for each start but take only the one with a minimum amount of doors:
		List<Integer> minimumPath = new LinkedList<Integer>();
		for (Door d : startDoors) {
			List<Integer> path = solver.compute(d.getIntIdentifier());
			if (path.size() < minimumPath.size() || minimumPath == null || minimumPath.isEmpty()) {
				minimumPath = path;
			}
		}
		// Find door to door path on the shortest path
		Path doorToDoorPath = findDoorToDoorPath(
				WorldFactory.findDoorForIdentifier(minimumPath.get(0)),
				WorldFactory.findDoorForIdentifier(minimumPath.get(minimumPath
						.size() - 1)));
		return doorToDoorPath;
	}

	/**
	 * Method finds a path between doors
	 * 
	 * @return the path between two doors or <code>null</code> if no path was
	 *         found.
	 */
	public static Path findDoorToDoorPath(Door start, Door end) {
		double[][] adjacency = SimulationEngine.getInstance().getWorld()
				.getDoorGraph();
		// start and end are adjacent:
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
			// two doors can belong to multiple rooms (in most cases they do not):
			Set<Room> fittingRooms = WorldFactory.getRoomsWithDoors(doorSet);
			if(fittingRooms.isEmpty()) {
				log.severe("No room was found. This shouldn't happen!");
				return null;
			}
			//Take the first room and calculate a path:
			Room room = fittingRooms.iterator().next();
			InRoomPathSolver solver = new InRoomPathSolver(room,
					(Point) end.getCentralPoint());
			List<Point> tempPath = solver.compute((Point) start
					.getCentralPoint());
			Path finalPath = new Path(start.getCentralPoint(),
					end.getCentralPoint());
			finalPath.addAll(tempPath);
			return finalPath;
		}
		//Last case: doors arn't adjacent:
		return findDoorToDoorPathOfNonAdjacentDoors(start, end);
	}
	

	/**
	 * Method for finding a path between two non adjacent doors
	 * @param start the start door
	 * @param end the end door
	 * @return the path between the doors
	 */
	private static Path findDoorToDoorPathOfNonAdjacentDoors(Door start,
			Door end) {
		// Doors arn't adjacent, so search in door graph.
		Set<Integer> temp = new HashSet<Integer>();
		temp.add(end.getIntIdentifier());
		GraphPathSolver solver = new GraphPathSolver(temp, SimulationEngine
				.getInstance().getWorld().getDoorGraph());
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
	 * Calculates a path if start and end are in the same room
	 * 
	 * @param start
	 *            the start position
	 * @param end
	 *            the end position
	 * @return a path that describes the way
	 */
	private static Path findPathInRoom(IPosition start, IPosition end) {
		Room room = PositionFactory.getRoomWithPoint(start.getCentralPoint());
		InRoomPathSolver solver = new InRoomPathSolver(room, (Point)end.getCentralPoint());
		List<Point> points = solver.compute((Point)start.getCentralPoint());
		Path path = new Path(start.getCentralPoint(), end.getCentralPoint());
		path.addAll(points);
		return path;
	}
	
	/**
	 * Method for calculating a path in a given room
	 * @param start the start point
	 * @param end the end point
	 * @param room the room to search in
	 * @return a path if one was found or <code>null</code> otherwise.
	 */
	private static Path findPathInRoom(Point2D start, Point2D end, Room room) {
		InRoomPathSolver solver = new InRoomPathSolver(room, (Point)end);
		List<Point> path = solver.compute((Point)start);
		Path finalPath =  new Path(start, end);
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
	public static Path findPathInSet(Set<Path> paths, Point start, Point end) {
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

	/**
	 * Calculates a path which's sub paths arn't longer than the give maximum
	 * length. This can be useful, if one need steps that can be performed in
	 * each second.
	 * 
	 * @param path
	 *            the path to split
	 * @param maximumPathLength
	 *            the maximum path length
	 * @return a fine grained path
	 * @deprecated because the algorithm provides path with sub paths of the size <code>1</code> and this method isn't needed.
	 */
	@Deprecated
	public static Path getFineGrainedPath(Path path, double maximumPathLength) {
		Path grainedPath = new Path(path.getStartPoint(), path.getEndPoint());
		for (Iterator<Point2D> iter = path.iterator(); iter.hasNext();) {
			Point2D first = iter.next();
			if (iter.hasNext()) {
				Point2D second = iter.next();
				grainedPath.addAll(getFineGrainedPath(first, second,
						maximumPathLength));
			} else {
				// Reached the last point
				break;
			}
		}
		return grainedPath;
	}

	/**
	 * Calculates a fine grained path between two points. This means, that the
	 * linear path is cut into further steps. This is useful if you need
	 * subpaths for each second.
	 * 
	 * @param start
	 *            the start point
	 * @param end
	 *            the end point
	 * @param maximumPathLength
	 *            the maximum distance between interpoints
	 * @return a new fine grained path
	 * @deprecated because the algorithm provides path with sub paths of the size <code>1</code> and this method isn't needed.
	 */
	@Deprecated
	public static Path getFineGrainedPath(Point2D start, Point2D end,
			double maximumPathLength) {
		Point2D directionVector = GraphicFactory.getDirectionVector(start, end);
		double length = GraphicFactory.calculateVectorLength(directionVector);
		Point2D normalizedDirectionVector = GraphicFactory
				.getNormalizedVector(directionVector);
		Path path = new Path(start, end);
		path.add(start);
		Point2D lastPoint = start;
		// Calculating interpoints while the remaining length is bigger than the
		// maximum path length
		while (length > maximumPathLength) {
			// Calculating interpoint
			double x = lastPoint.getX() + maximumPathLength
					* normalizedDirectionVector.getX();
			double y = lastPoint.getY() + maximumPathLength
					* normalizedDirectionVector.getY();
			lastPoint = new Point2D.Double(x, y);
			// adding interpoint
			path.add(lastPoint);
			// calculating reaming length
			length -= maximumPathLength;
		}
		path.add(end);
	
		return path;
	
	}
}
