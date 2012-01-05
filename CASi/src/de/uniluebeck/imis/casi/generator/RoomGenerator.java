/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012 2012  Moritz Bürger, Marvin Frick, Tobias Mende
 *
 *  This program is free software. It is licensed under the
 *  GNU Lesser General Public License with one clarification.
 *  
 *  You should have received a copy of the 
 *  GNU Lesser General Public License along with this program. 
 *  See the LICENSE.txt file in this projects root folder or visit
 *  <http://www.gnu.org/licenses/lgpl.html> for more details.
 */
package de.uniluebeck.imis.casi.generator;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.simulation.model.Room;

/**
 * @author Marvin Frick
 * 
 *         This class creates a singleton object that can be used to create
 *         Agents. It stores a list of already created agents and offers access
 *         to them through either name or id or whatever.
 * 
 */
public class RoomGenerator {
	private static final Logger log = Logger.getLogger(RoomGenerator.class
			.getName());
	/** The instance of this singleton */
	private static RoomGenerator instance;

	/**
	 * The List of already created agents.
	 */
	// TODO: have we made a general decision on what list type to use?!
	private Set<Room> alreadyCreatedRooms = new HashSet<Room>();

	/**
	 * The private constructor of this singleton
	 */
	private RoomGenerator() {
		// just here for prohibiting external access
	}

	/**
	 * Getter for the instance of this singleton
	 * 
	 * @return the instance
	 */
	public static RoomGenerator getInstance() {
		if (instance == null) {
			instance = new RoomGenerator();
		}
		return instance;
	}

	/**
	 * Add a new room to this collection
	 * 
	 * @param newRoom
	 *            the new Room
	 */
	public void newRoom(Room newRoom) {
		alreadyCreatedRooms.add(newRoom);
	}

	/**
	 * Returns an Room with a given identifier
	 * 
	 * @param identifierToLookFor
	 * @return the Room with this name or (CAUTION) null if this room cannot be
	 *         found!
	 */
	public Room findRoomByIdentifier(String identifierToLookFor) {
		for (Room room : alreadyCreatedRooms) {
			if (room.getIdentifier().equals(identifierToLookFor)) {
				return room;
			}
		}
		log.warning(String.format("couldn't find room %s, mispelled it?", identifierToLookFor));
		return null;
	}


	/**
	 * Getter for the list of Rooms
	 * 
	 * @return the Vector with all the Rooms
	 */
	public Set<Room> getAll() {
		return alreadyCreatedRooms;
	}

}
