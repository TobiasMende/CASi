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
import de.uniluebeck.imis.casi.generator.AgentCollector;
import de.uniluebeck.imis.casi.generator.ComponentCollector;
import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.generator.RoomCollector;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.World;

/**
 * Generates the specific MATe simulation environment World. All sub actions are
 * delegated to their Generators. I should be not necessary to change this class
 * for adding new aspects of this world. Put your changes in the specific
 * Classes! Do not remove the implementation of {@link IWorldGenerator}, this is
 * needed. The simulator expects {@link IWorldGenerator#generateWorld()}.
 * 
 * @author Marvin Frick
 * 
 */
public class WorldGenerator implements IWorldGenerator {
	
	private static String SIMULATION_NAME = "MATe Demo Simulation";

	private static final Logger log = Logger.getLogger(WorldGenerator.class
			.getName());

	World tempWorld = new World();
	RoomCollector rooms = RoomCollector.getInstance();
	AgentCollector agents = AgentCollector.getInstance();

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
			tempWorld.setStartTime(new SimulationTime("4/23/2012 09:00:42"));
			tempWorld.setBackgroundImage(image);

			log.info(SIMULATION_NAME+": generating rooms");
			Rooms.generateRooms();

			log.info(SIMULATION_NAME+": generating agents");
			Agents.generateAgents();

			log.info(SIMULATION_NAME+": generating actions");
			Actions.generateActions(tempWorld.getStartTime());
			Actions.generateActionsPools(tempWorld.getStartTime());

			log.info(SIMULATION_NAME+": generating components");
			Components.fillOfficesWithDesktopThings();
			Components.generateActuators();
			Components.generateSensors();

			log.info(SIMULATION_NAME+": linking new world");
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
		AgentCollector.getInstance().fillAllAgentsWithDefaultRoom();

		try {
			tempWorld.setRooms(RoomCollector.getInstance().getAll());
			tempWorld.setAgents(AgentCollector.getInstance().getAll());
			tempWorld.setInteractionComponents(ComponentCollector.getInstance()
					.getAll());
		} catch (IllegalAccessException e) {
			// World seems to be already sealed!
			log.severe("could not set the world. It is already sealed!");
		}

	}
}
