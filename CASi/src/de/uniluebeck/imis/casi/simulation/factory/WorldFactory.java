package de.uniluebeck.imis.casi.simulation.factory;

import java.util.HashSet;
import java.util.Set;

import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * The WorldFactory holds world objects and is able generate new objects
 * @author Tobias Mende
 *
 */
public class WorldFactory {
	/** A set of doors, that are used in the simulation */
	private static Set<Door> doors = new HashSet<Door>();
	
	/** A set of walls, that are used in the simulation */
	private static Set<Wall> walls = new HashSet<Wall>();
	
	/**
	 * Method for searching a door with a given identifier
	 * @param identifier the identifier
	 * @return the door, if it was found, <code>null</code> otherwise.
	 */
	public static Door findDoorForIdentifier(String identifier) {
		for(Door door : doors) {
			if(door.getIdentifier().equals(identifier)) {
				return door;
			}
		}
		return null;
	}
	
	/**
	 * Method for adding a door
	 * @param door the door
	 */
	public static void addDoor(Door door) {
		doors.add(door);
	}

}
