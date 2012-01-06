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
import de.uniluebeck.imis.casi.simulation.model.IPosition;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent.Face;
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

	}

	/**
	 * Fills all offices with a Desktop, a Cube and a Mike. Watch out that the
	 * Agents have their offices (a Room) as defaultPosition
	 */
	public static void fillOfficesWithDesktopThings() {
		ComponentCollector c = ComponentCollector.getInstance();

		for (Agent ag : AgentCollector.getInstance().getAll()) {
			IPosition r = ag.getDefaultPosition();
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
			cc.newComponent(new DoorLight(d, (Room) a.getDefaultPosition(), a));
			
		}

		// ##########
		// Zwotah Zwiebel
		// ##########
	
		// ##########
		// Dagobert Dreieck
		// ##########
	
		// ##########
		// Felix Freudentanz
		// ##########

		// ##########
		// Susi Sekretärin
		// ##########

		// ##########
		// Rudi Random
		// ##########

	}

	/**
	 * Fills the ComponentCollector with sensors.
	 * 
	 * Put all your sensors here!
	 */
	public static void generateSensors() {

	}
}
