package de.uniluebeck.imis.casi.simulation.factory;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.IPosition;
import de.uniluebeck.imis.casi.simulation.model.Path;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;

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
	 * Calculates a fine grained path between two points. This means, that the linear path is cut into further steps. This is useful if you need subpaths for each second.
	 * @param start the start point
	 * @param end the end point
	 * @param maximumPathLength the maximum distance between interpoints
	 * @return a new fine grained path
	 */
	public static Path getFineGrainedPath(Point2D start,
			Point2D end, double maximumPathLength) {
		Point2D directionVector = GraphicFactory.getDirectionVector(start, end);
		double length = GraphicFactory.calculateVectorLength(directionVector);
		Point2D normalizedDirectionVector = GraphicFactory
				.getNormalizedVector(directionVector);
		Path path = new Path(start, end);
		path.add(start);
		Point2D lastPoint = start;
		// Calculating interpoints while the reamining length is bigger than the maximum path length
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
