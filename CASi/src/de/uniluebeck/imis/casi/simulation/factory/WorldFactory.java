package de.uniluebeck.imis.casi.simulation.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
	private static Map<Integer,Door> doors = new HashMap<Integer, Door>();
	
	/** A map of walls, that are used in the simulation */
	private static Set<Wall> walls = new HashSet<Wall>();
	
	/**
	 * Method for searching a door with a given identifier
	 * @param identifier the identifier
	 * @return the door, if it was found, <code>null</code> otherwise.
	 */
	public static Door findDoorForIdentifier(String identifier) {
		for(Door door : doors.values()) {
			if(door.getIdentifier().equals(identifier)) {
				return door;
			}
		}
		return null;
	}
	
	/**
	 * Method for searching a door with a given int identifier
	 * @param identifier the identifier
	 * @return the door or <code>null</code> if no door was found
	 */
	public static Door findDoorForIdentifier(int identifier) {
		return doors.get(identifier);
	}
	
	/**
	 * Method for adding a door
	 * @param door the door
	 */
	public static void addDoor(Door door) {
		doors.put(door.getIntIdentifier(), door);
	}
	

}
