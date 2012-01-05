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
package de.uniluebeck.imis.casi.simulations.mate.generator.java;

import java.util.HashSet;

import de.uniluebeck.imis.casi.generator.AgentCollector;
import de.uniluebeck.imis.casi.generator.ComponentCollector;
import de.uniluebeck.imis.casi.generator.RoomCollector;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulations.mate.simulation.model.Cube;
import de.uniluebeck.imis.casi.simulations.mate.simulation.model.Desktop;
import de.uniluebeck.imis.casi.simulations.mate.simulation.model.DoorLight;
import de.uniluebeck.imis.casi.simulations.mate.simulation.model.DoorSensor;
import de.uniluebeck.imis.casi.simulations.mate.simulation.model.Mike;

/**
 * Fills the ComponentCollector with Components. Both Actuators and Sensors.
 * 
 * Put all your Components here!
 * 
 * @author Marvin Frick
 * 
 */
public class Components {

	/**
	 * Fills the ComponentCollector with actuators.
	 * 
	 * Put all your actuators here!
	 */
	public static void generateActuators() {
		ComponentCollector ac = ComponentCollector.getInstance();
		RoomCollector rooms = RoomCollector.getInstance();
		AgentCollector agents = AgentCollector.getInstance();

		ac.manyNewComponents(thigsToAddToRoomWithOwner(
				rooms.findRoomByIdentifier("tim'sRoom"),
				agents.findAgentByName("Tim")));
		ac.manyNewComponents(thigsToAddToRoomWithOwner(
				rooms.findRoomByIdentifier("crazyRoom"),
				agents.findAgentByName("Crazy Guy")));

	}

	/**
	 * Adds things to given room with a given owner. Used for doorSensors and
	 * DoorLights...
	 * 
	 * @param room
	 *            the room the things should be added to
	 * @param owner
	 *            the owner of this room and therefore the owner of this new
	 *            components
	 * 
	 */
	private static HashSet<AbstractInteractionComponent> thigsToAddToRoomWithOwner(
			Room room, Agent owner) {
		HashSet<AbstractInteractionComponent> res = new HashSet<AbstractInteractionComponent>();

		// adding door related things to each door
		for (Door door : room.getDoors()) {
			res.add(new DoorLight(door, room, owner));
			res.add(new DoorSensor(door, owner));
		}

		// adding desk related things
		res.add(new Desktop(room.getCentralPoint(), owner));
		res.add(new Cube(room.getCentralPoint(), owner));
		res.add(new Mike(room, owner));
		return res;

	}

	/**
	 * Fills the ComponentCollector with sensors.
	 * 
	 * Put all your sensors here!
	 */
	public static void generateSensors() {

	}
}
