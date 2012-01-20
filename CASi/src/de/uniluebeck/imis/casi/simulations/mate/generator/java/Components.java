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

import de.uniluebeck.imis.casi.generator.AgentCollector;
import de.uniluebeck.imis.casi.generator.ComponentCollector;
import de.uniluebeck.imis.casi.generator.RoomCollector;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent.Face;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Cube;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Desktop;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.DoorLight;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.DoorSensor;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Mike;

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
//		ComponentCollector ac = ComponentCollector.getInstance();
//		RoomCollector rooms = RoomCollector.getInstance();
//		AgentCollector agents = AgentCollector.getInstance();

	}

	/**
	 * Fills all offices with a Desktop, a Cube and a Mike. Watch out that the
	 * Agents have their offices (a Room) as defaultPosition
	 */
	public static void fillOfficesWithDesktopThings() {
		ComponentCollector c = ComponentCollector.getInstance();

		for (Agent ag : AgentCollector.getInstance().getAll()) {
			Room r = (Room) ag.getDefaultPosition();
			
			c.newComponent(new Desktop(r.getCentralPoint(), ag));
			c.newComponent(new Cube(r.getCentralPoint(), ag));
			c.newComponent(new Mike((Room) r, ag));
		}

	}

	/**
	 * Adds things to given room with a given owner. Used for doorSensors and
	 * DoorLights...
	 * 
	 */
	public static void addLightsAndSensorsToDoors() {
		ComponentCollector cc = ComponentCollector.getInstance();
		AgentCollector ac = AgentCollector.getInstance();
		Agent a;

		// Hermann Matsumbishi
		a = ac.findAgentByName("Hermann Matsumbishi");
		for (Door d : ((Room) a.getDefaultPosition()).getDoors()) {
			DoorLight dl = new DoorLight(d, (Room) a.getDefaultPosition(), a);
			dl.setMonitoredArea(Face.EAST, 20, 180);
			cc.newComponent(dl);
			cc.newComponent(new DoorSensor(d, a));
		}

		// ##########
		// Zwotah Zwiebel
		// ##########
		a = ac.findAgentByName("Zwotah Zwiebel");
		for (Door d : ((Room) a.getDefaultPosition()).getDoors()) {
			DoorLight dl = new DoorLight(d, (Room) a.getDefaultPosition(), a);
			dl.setMonitoredArea(Face.EAST, 20, 180);
			cc.newComponent(dl);
			cc.newComponent(new DoorSensor(d, a));
		}

		// ##########
		// Dagobert Dreieck
		// ##########
		a = ac.findAgentByName("Dagobert Dreieck");
		for (Door d : ((Room) a.getDefaultPosition()).getDoors()) {
			DoorLight dl = new DoorLight(d, (Room) a.getDefaultPosition(), a);
			dl.setMonitoredArea(Face.EAST, 20, 180);
			cc.newComponent(dl);
			cc.newComponent(new DoorSensor(d, a));
		}

		// ##########
		// Felix Freudentanz
		// ##########
		a = ac.findAgentByName("Felix Freudentanz");
		for (Door d : ((Room) a.getDefaultPosition()).getDoors()) {
			DoorLight dl = new DoorLight(d, (Room) a.getDefaultPosition(), a);
			dl.setMonitoredArea(Face.NORTH, 20, 180);
			cc.newComponent(dl);
			cc.newComponent(new DoorSensor(d, a));
		}

		// ##########
		// Rudi Random
		// ##########
		a = ac.findAgentByName("Rudi Random");
		Door door8 = RoomCollector.getInstance().findDoorByIdentifier("rudis-south-soor");
		DoorLight doorlightRudi = new DoorLight(door8, (Room) a.getDefaultPosition(), a);
		doorlightRudi.setMonitoredArea(Face.SOUTH, 20, 180);
		cc.newComponent(doorlightRudi);
		cc.newComponent(new DoorSensor(door8, a));
		
		Door door9 = RoomCollector.getInstance().findDoorByIdentifier("rudis-west-soor");
		doorlightRudi = new DoorLight(door9, (Room) a.getDefaultPosition(), a);
		doorlightRudi.setMonitoredArea(Face.WEST, 20, 180);
		cc.newComponent(doorlightRudi);
		cc.newComponent(new DoorSensor(door9, a));
		
		// ##########
		// Susi Sekretärin
		// ##########
		Door door = RoomCollector.getInstance().findDoorByIdentifier(
				"susis-outer-door");
		a = ac.findAgentByName("Susi Sekretärin");
		DoorLight dl = new DoorLight(door, (Room) a.getDefaultPosition(), a);
		dl.setMonitoredArea(Face.WEST, 20, 180);
		cc.newComponent(dl);
		cc.newComponent(new DoorSensor(door, a));

	}

	/**
	 * Fills the ComponentCollector with sensors.
	 * 
	 * Put all your sensors here!
	 */
	public static void generateSensors() {

	}
}
