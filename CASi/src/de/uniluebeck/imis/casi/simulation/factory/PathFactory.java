package de.uniluebeck.imis.casi.simulation.factory;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.sun.tools.doclets.internal.toolkit.taglets.InheritDocTaglet;

import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.IPosition;
import de.uniluebeck.imis.casi.simulation.model.Path;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;
import de.uniluebeck.imis.casi.simulation.model.World;
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

	/**
	 * Calculates a matrix of path iterators which show the way from each door
	 * in this room to each other.
	 * 
	 * @param room
	 *            the room to calculate the paths for
	 * @return a set of paths or <code>null</code> if no paths were found
	 */
	public static Set<Path> getPathsForRoom(Room room) {
		List<Door> doors = new ArrayList<Door>();
		// Get a list of all doors
		for (Wall wall : room.getWalls()) {
			doors.addAll(wall.getDoors());
		}
		if (doors.size() == 0) {
			return null;
		}
		Set<Path> paths = new HashSet<Path>();
		// Calculate paths from each door to each other
		for (Door start : doors) {
			for (Door end : doors) {
				if (start.equals(end)) {
					continue;
				}
				// TODO implement, THINK better way? if "a->b" is known, we
				// don't need to calculate "b->a"
			}
		}
		return paths;
	}

	/**
	 * Calculates a path which's subpaths arn't longer than the give maximum
	 * length. This can be useful, if one need steps that can be performed in
	 * each second.
	 * 
	 * @param path
	 *            the path to split
	 * @param maximumPathLength
	 *            the maximum path length
	 * @return a fine grained path
	 */
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
	 */
	public static Path getFineGrainedPath(Point2D start, Point2D end,
			double maximumPathLength) {
		Point2D directionVector = GraphicFactory.getDirectionVector(start, end);
		double length = GraphicFactory.calculateVectorLength(directionVector);
		Point2D normalizedDirectionVector = GraphicFactory
				.getNormalizedVector(directionVector);
		Path path = new Path(start, end);
		path.add(start);
		Point2D lastPoint = start;
		// Calculating interpoints while the reamining length is bigger than the
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
			return findDoorToDoorPath((Door) start, (Door) end);
		}
		Room startRoom = PositionFactory.getRoomWithPoint(start
				.getCentralPoint());
		Room endRoom = PositionFactory.getRoomWithPoint(end.getCentralPoint());
		if (startRoom != null && endRoom != null && startRoom.equals(endRoom)) {
			return findPathInRoom(start, end);
		}
		// TODO implement
		return null;
	}

	/**
	 * Method finds a path between adjacent doors
	 * 
	 * @return the path
	 */
	public static Path findDoorToDoorPath(Door start, Door end) {
		double[][] adjacency = SimulationEngine.getInstance().getWorld()
				.getDoorGraph();
		if (adjacency[start.getIntIdentifier()][end.getIntIdentifier()] > 0) {
			Path test = SimulationEngine.getInstance().getWorld()
					.getDoorPath(start, end);
			if (test != null) {
				// Path was calculated yet
				return test;
			}
			// doors are adjacent
			// TODO get room for the doors
			Room room = null;
			InRoomPathSolver solver = new InRoomPathSolver(room,
					(Point) end.getCentralPoint());
			List<Point> tempPath = solver.compute((Point) start
					.getCentralPoint());
			Path finalPath = new Path(start.getCentralPoint(),
					end.getCentralPoint());
			finalPath.addAll(tempPath);
			return finalPath;
		}

		// Doors arn't adjacent, so search in door graph.
		Set<Integer> temp = new HashSet<Integer>();
		temp.add(end.getIntIdentifier());
		GraphPathSolver solver = new GraphPathSolver(temp, SimulationEngine
				.getInstance().getWorld().getDoorGraph());
		// Computes a list of identifiers for door noodes.
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
		// TODO implement
		return null;
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
}
