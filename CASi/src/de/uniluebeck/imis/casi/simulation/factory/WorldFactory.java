/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
 */
package de.uniluebeck.imis.casi.simulation.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;

/**
 * The WorldFactory holds world objects and is able generate new objects
 * 
 * @author Tobias Mende
 * 
 */
public class WorldFactory {
	/** The development logger */
	private static final Logger log = Logger.getLogger(WorldFactory.class
			.getName());
	/** A set of doors, that are used in the simulation */
	private static Map<Integer, Door> doors = new HashMap<Integer, Door>();

	/**
	 * Method for searching a door with a given identifier
	 * 
	 * @param identifier
	 *            the identifier
	 * @return the door, if it was found, <code>null</code> otherwise.
	 */
	public static Door findDoorForIdentifier(String identifier) {
		for (Door door : doors.values()) {
			if (door.getIdentifier().equals(identifier)) {
				return door;
			}
		}
		return null;
	}

	/**
	 * Method for searching a door with a given int identifier
	 * 
	 * @param identifier
	 *            the identifier
	 * @return the door or <code>null</code> if no door was found
	 */
	public static Door findDoorForIdentifier(int identifier) {
		return doors.get(identifier);
	}

	/**
	 * Method for adding a door
	 * 
	 * @param door
	 *            the door
	 */
	public static void addDoor(Door door) {
		doors.put(door.getIntIdentifier(), door);
	}

	/**
	 * Method for getting a room which have exactly the provided doors
	 * 
	 * @param doors
	 *            a set of doors
	 * @return the room or <code>null</code> if no room was found
	 */
	public static Room getRoomWithDoors(Set<Door> doors) {
		try {
			Set<Room> rooms = SimulationEngine.getInstance().getWorld()
					.getRooms();
			for (Room room : rooms) {
				if (room.getWalls().equals(doors)) {
					return room;
				}
			}
		} catch (IllegalAccessException e) {
			log.severe("World isn't sealed. This should not happen! "
					+ e.fillInStackTrace());
		}
		return null;
	}

	/**
	 * Method for getting a set of rooms which contain the provided doors.
	 * 
	 * @param doors
	 *            the doors to search rooms for
	 * @return a set of rooms containing the doors or an empty set, if no room
	 *         was found.
	 */
	public static Set<Room> getRoomsWithDoors(Set<Door> doors) {
		Set<Room> results = new HashSet<Room>();
		try {
			Set<Room> rooms = SimulationEngine.getInstance().getWorld()
					.getRooms();
			for (Room room : rooms) {
				if (room.getDoors().containsAll(doors)) {
					results.add(room);
				}
			}
		} catch (IllegalAccessException e) {
			log.severe("World isn't sealed. This should not happen! "
					+ e.fillInStackTrace());
		}
		return results;
	}

}
