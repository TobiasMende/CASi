package de.uniluebeck.imis.casi.simulation.factory;

import java.util.ArrayList;
import java.util.HashSet;
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
		if(doors.size() == 0) {
			return null;
		}
		Set<Path> paths = new HashSet<Path>();
		// Calculate paths from each door to each other
		for(Door start : doors) {
			for(Door end : doors) {
				if(start.equals(end)) {
					continue;
				}
				// TODO implement, THINK better way? if "a->b" is known, we don't need to calculate "b->a"
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
	public static Path getFineGrainedPath(Path path, int maximumPathLength) {
		// TODO implement
		return null;
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
}
