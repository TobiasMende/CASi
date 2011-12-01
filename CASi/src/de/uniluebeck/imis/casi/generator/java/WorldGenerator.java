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
package de.uniluebeck.imis.casi.generator.java;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.simulation.model.AbstractActuator;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.AbstractSensor;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.Wall;
import de.uniluebeck.imis.casi.simulation.model.World;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;


public class WorldGenerator implements IWorldGenerator {

	private static final Logger log = Logger.getLogger(WorldGenerator.class.getName());  
	
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
		// TODO Auto-generated method stub

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

		World tempWorld = new World();

		// backroundimage
		BufferedImage image = null;
		try {
			image = ImageIO
					.read(new File("sims/dev_office_java/backround.png"));
		} catch (IOException e) {
			// there could be something wrong
			// TODO: logg this!
		}
		
		// rooms
		HashSet<Room> rooms = generateRooms();
		
		// generate the agents
		HashSet<Agent> agents = generateAgents();

		// sensors
		HashSet<AbstractSensor> sensors = generateSensors();
		
		// actuators
		HashSet<AbstractActuator> actuators = generateActuators();
		
		// giant try block around everything that actually sets things to the
		// world 
		try {

			tempWorld.setBackgroundImage(image);
			tempWorld.setRooms(rooms);
			tempWorld.setAgents(agents);
			tempWorld.setSensors(sensors);
			tempWorld.setActuators(actuators);
			tempWorld.setComponents(new HashSet<AbstractComponent>());
			tempWorld.setStartTime(new SimulationTime("12/24/2011 02:03:42"));
			tempWorld.seal();

		} catch (Exception e) {
			// catches every Exception that may arise from sealed World
			log.severe("Generator made a mistake! \n"+e.fillInStackTrace().toString());
		}
		return tempWorld;
	}

	/**
	 * @return
	 */
	private HashSet<AbstractActuator> generateActuators() {
		// TODO generate actuators!
		return new HashSet<AbstractActuator>();
	}

	/**
	 * @return
	 */
	private HashSet<AbstractSensor> generateSensors() {
		// TODO generator sensors!
		return new HashSet<AbstractSensor>();
	}

	/**
	 * Only creates some agents. Testing stuff
	 * 
	 * @return {@link HashSet} of {@link Agent}
	 */
	private HashSet<Agent> generateAgents() {
		log.info("generating agents");
		
		HashSet<Agent> agents = new HashSet<Agent>();
		Agent tempAgent = null;

		// if we need a lot of agents...
		for (int i = 0; i < 42; i++) {
			tempAgent = new Agent("agent_" + i + "_smith", "A. Smith the " + i,
					"crowd");
			agents.add(tempAgent);
		}

		tempAgent = new Agent("agent_00", "Father Moneymaker", "candidates");
		agents.add(tempAgent);
		tempAgent = new Agent("agent_01", "Tim", "candidates");
		agents.add(tempAgent);
		tempAgent = new Agent("agent_02", "And I", "candidates");
		agents.add(tempAgent);

		for (int i = 0; i < 3; i++) {
			tempAgent = new Agent("agent_" + i + "_lady", "Tentlady" + i,
					"tendladies");
			agents.add(tempAgent);
		}

		return agents;

	}

	/**
	 * Creates some rooms. Testing stuff.
	 * 
	 * @return {@link HashSet} of {@link Room}
	 */
	private HashSet<Room> generateRooms() {
		
		log.info("generating rooms");
		
		HashSet<Room> rooms = new HashSet<Room>();
		Wall theNewWall;
		Door theNewDoor;
		Room theNewRoom = new Room();

		// Room #1 (top left)
		theNewRoom.addWall(new Wall(new Point(20, 20), new Point(20, 100)));
		theNewRoom.addWall(new Wall(new Point(20, 100), new Point(100, 100)));
		theNewDoor = new Door(20, 20);
		theNewWall = new Wall(new Point(100, 100), new Point(100, 20));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(new Wall(new Point(100, 20), new Point(20, 20)));
		rooms.add(theNewRoom);

		// Room #2 (second top left)
		theNewRoom.addWall(new Wall(new Point(20, 100), new Point(20, 160)));
		theNewRoom.addWall(new Wall(new Point(20, 160), new Point(100, 160)));
		theNewDoor = new Door(20, 20);
		theNewWall = new Wall(new Point(100, 160), new Point(100, 100));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(new Wall(new Point(100, 100), new Point(20, 100)));
		rooms.add(theNewRoom);

		// Room #3 (third top left)
		theNewRoom.addWall(new Wall(new Point(20, 160), new Point(20, 220)));
		theNewRoom.addWall(new Wall(new Point(20, 220), new Point(100, 220)));
		theNewDoor = new Door(20, 20);
		theNewWall = new Wall(new Point(100, 220), new Point(100, 160));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(new Wall(new Point(100, 160), new Point(20, 160)));
		rooms.add(theNewRoom);

		// Room #4 small kitchen
		theNewRoom.addWall(new Wall(new Point(20, 220), new Point(20, 260)));
		theNewRoom.addWall(new Wall(new Point(20, 260), new Point(100, 260)));
		theNewDoor = new Door();
		theNewWall = new Wall(new Point(100, 260), new Point(100, 220));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(new Wall(new Point(100, 220), new Point(20, 220)));
		rooms.add(theNewRoom);

		// this are just a few rooms. Not a whole building for now.
		// lets check first in the gui if the points are chosen correctly
		return rooms;

	}

	private HashSet<AbstractAction> generateActionPools() {

		return null;
	}

}
