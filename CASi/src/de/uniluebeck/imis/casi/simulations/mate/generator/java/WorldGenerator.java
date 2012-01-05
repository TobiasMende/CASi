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
package de.uniluebeck.imis.casi.simulations.mate.generator.java;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.generator.AgentGenerator;
import de.uniluebeck.imis.casi.generator.ComponentCollector;
import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.generator.RoomGenerator;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.World;
import de.uniluebeck.imis.casi.simulations.mate.simulation.model.Cube;
import de.uniluebeck.imis.casi.simulations.mate.simulation.model.Desktop;
import de.uniluebeck.imis.casi.simulations.mate.simulation.model.DoorLight;
import de.uniluebeck.imis.casi.simulations.mate.simulation.model.DoorSensor;
import de.uniluebeck.imis.casi.simulations.mate.simulation.model.Mike;

public class WorldGenerator implements IWorldGenerator {

	private static final Logger log = Logger.getLogger(WorldGenerator.class
			.getName());

	World tempWorld = new World();
	RoomGenerator rooms = RoomGenerator.getInstance();
	AgentGenerator agents = AgentGenerator.getInstance();

	/**
	 * This generator creates an basic, pre coded World object
	 * 
	 * @throws Exception
	 *             In this particular generator the Exception is less useful,
	 *             this comes from {@link IWorldGenerator} which is used by the
	 *             more specialized generators.
	 * @return {@link World}
	 */
	@Override
	public World generateWorld() {

		// Ein Geistlicher und ein australischer Schafhirte treten bei einem
		// Quiz gegeneinander an.
		// Nach Ablauf der regularen Fragerunde steht es unentschieden, und der
		// Moderator der Sendung stellt die Stichfrage, die da lautet:
		// Schaffen Sie es, innerhalb von 2 Minuten einen Vers auf das Wort
		// "Timbuktu" zu reimen?

		// Die beiden Kandidaten ziehen sich zuruck. Nach 1 Minuten tritt der
		// Geistliche vor das Publikum und stellt sein Werk vor:

		// "I was a father all my life,
		// I had no children, had no wife,
		// I read the bible through and through
		// on my way to Timbuktu..."
		//
		// Das Publikum ist begeistert und wähnt den Kirchenmann bereits als den
		// sicheren Sieger. Doch da tritt der australische Schafhirte vor und
		// dichtet:
		//
		// "When Tim and I to Brisbane went,
		// we met three ladies in a tent.
		// They were three and we were two,
		// so I booked one an Tim booked two..."

		// backroundimage
		BufferedImage image = null;
		try {
			image = ImageIO
					.read(new File("sims/dev_office_java/backround.png"));
		} catch (IOException e) {
			CASi.SIM_LOG
					.severe("Can't read the background image for the simulation. Something is wrong!");
			log.severe("Can't read image: " + e.fillInStackTrace());
		}

		// giant try block around everything that actually sets things to the
		// world

		try {
			tempWorld.setStartTime(new SimulationTime("12/24/2011 02:03:42"));
			tempWorld.setBackgroundImage(image);

			log.info("generating rooms");
			Rooms.generateRooms();

			log.info("generating agents");
			Agents.generateAgents();

			log.info("generating actions");
			Actions.generateActions(tempWorld.getStartTime());
			Actions.generateActionsPools(tempWorld.getStartTime());

			log.info("generating components");
			Components.generateActuators();
			Components.generateSensors();
			
			log.info("linking new world");
			Linker linker = new Linker();
			linker.linkAll();

			tempWorld.setComponents(new HashSet<AbstractComponent>());
		} catch (IllegalAccessException e) {
			log.severe("Illegal Access:" + e.fillInStackTrace());
		} catch (ParseException e) {
			log.severe("Parse Exception:" + e.fillInStackTrace());
		} catch (Exception e) {
			log.severe("Unknown Exception: " + e.fillInStackTrace());
		}

		finalizeWorld();
		return tempWorld;
	}


	/**
	 * Finalizes this world.
	 */
	private void finalizeWorld() {
		// assure: no null defaultRooms
		AgentGenerator.getInstance().fillAllAgentsWithDefaultRoom();

		try {
			tempWorld.setRooms(RoomGenerator.getInstance().getAll());
			tempWorld.setAgents(AgentGenerator.getInstance().getAll());
			tempWorld.setInteractionComponents(ComponentCollector.getInstance().getAll());
		} catch (IllegalAccessException e) {
			// World seems to be already sealed!
			log.severe("could not set the world. It is already sealed!");
		}

	}
}
