/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende
 *
 *  This program is free software. It is licensed under the
 *  GNU Lesser General Public License with one clarification.
 *  
 *  You should have received a copy of the 
 *  GNU Lesser General Public License along with this program. 
 *  See the LICENSE.txt file in this projects root folder or visit
 *  <http://www.gnu.org/licenses/lgpl.html> for more details.
 */
package de.uniluebeck.imis.casi.generator.stub;

import java.text.ParseException;
import java.util.HashSet;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.simulation.model.AbstractActuator;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.AbstractSensor;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.World;

/**
 * The generator stub is just used for testing without a real world
 * 
 * @author Tobias Mende
 * 
 */
public class GeneratorStub implements IWorldGenerator {
	private static final Logger log = Logger.getLogger(GeneratorStub.class
			.getName());
	private World world = new World();

	@Override
	public World generateWorld() {

		try {
			generateRooms();
			generateAgents();
			generateComponents();
			world.setStartTime(new SimulationTime("12/24/2011 23:23:42"));
		} catch (IllegalAccessException e) {
			log.severe("This shouldn't happen " + e.fillInStackTrace());
		} catch (ParseException e) {
			log.severe("Error while parsing time: " + e.fillInStackTrace());
		}
		return world;
	}

	/**
	 * Generates an empty set of rooms
	 * 
	 * @throws IllegalAccessException
	 *             if the world is sealed
	 */
	private void generateRooms() throws IllegalAccessException {
		world.setRooms(new HashSet<Room>());
	}

	/**
	 * Generates an empty set of agents
	 * 
	 * @throws IllegalAccessException
	 *             if the world is sealed
	 */
	private void generateAgents() throws IllegalAccessException {
		world.setAgents(new HashSet<Agent>());
	}

	/**
	 * Generates a set of components, sensors and actuators
	 * 
	 * @throws IllegalAccessException
	 *             if the world is sealed
	 */
	private void generateComponents() throws IllegalAccessException {
		world.setActuators(new HashSet<AbstractActuator>());
		world.setSensors(new HashSet<AbstractSensor>());
		world.setComponents(new HashSet<AbstractComponent>());
	}

}
