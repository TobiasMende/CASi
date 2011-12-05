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
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.simulation.factory.WallFactory;
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
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction.TYPE;
import de.uniluebeck.imis.casi.simulation.model.actions.Move;

public class WorldGenerator implements IWorldGenerator {

	private static final Logger log = Logger.getLogger(WorldGeneratorTest.class
			.getName());

	World tempWorld = new World();
	Room timsRoom, mainFloor;

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

		// backroundimage
		BufferedImage image = null;
		try {
			image = ImageIO
					.read(new File("sims/dev_office_java/backround.png"));
		} catch (IOException e) {
			// there could be something wrong
			// TODO: logg this!
		}

		// giant try block around everything that actually sets things to the
		// world
		try {

			tempWorld.setStartTime(new SimulationTime("12/24/2011 02:03:42"));
			tempWorld.setBackgroundImage(image);

			// rooms
			HashSet<Room> rooms = generateRooms();
			tempWorld.setRooms(rooms);

			// generate the agents
			HashSet<Agent> agents = generateAgents();
			tempWorld.setAgents(agents);

			// sensors
			HashSet<AbstractSensor> sensors = generateSensors();
			tempWorld.setSensors(sensors);

			// actuators
			HashSet<AbstractActuator> actuators = generateActuators();
			tempWorld.setActuators(actuators);

			tempWorld.setComponents(new HashSet<AbstractComponent>());
			tempWorld.seal();

		} catch (Exception e) {
			// catches every Exception that may arise from sealed World
			log.severe("Generator made a mistake! \n"
					+ e.fillInStackTrace().toString());
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
		for (int i = 0; i < 0; i++) {
			tempAgent = new Agent("agent_" + i + "_smith", "A. Smith the " + i,
					"crowd");
			agents.add(tempAgent);
		}

		tempAgent = new Agent("agent_00", "Father Moneymaker", "candidates");
		agents.add(tempAgent);
		Agent tim = new Agent("agent_01", "Tim", "candidates");
		tim.setCurrentPosition(timsRoom);
		tim.setDefaultPosition(timsRoom);
		agents.add(tim);
		tempAgent = new Agent("agent_02", "And I", "candidates");
		agents.add(tempAgent);

		for (int i = 0; i < 3; i++) {
			tempAgent = new Agent("agent_" + i + "_lady", "Tentlady" + i,
					"tendladies");
			tempAgent.setDefaultPosition(mainFloor);
			tempAgent = generateActionsForTentLady(tim, tempAgent);

			agents.add(tempAgent);
		}

		return agents;

	}

	private Agent generateActionsForTentLady(Agent tim, Agent tentLady) {

		AbstractAction tempAction = new Move(tim);
		tempAction.setType(TYPE.NORMAL);
		tempAction.setPriority(5);
		tempAction.setEarliestStartTime(tempWorld.getStartTime().plus(
				1 + new Random().nextInt(10)));
		tentLady.addActionToList(tempAction);
		return tentLady;
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

		// main floor
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 20),
				new Point(100, 100)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 100),
				new Point(100, 160)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 160),
				new Point(100, 220)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 220),
				new Point(100, 260)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 260),
				new Point(120, 260)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(120, 260),
				new Point(150, 260)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(150, 260),
				new Point(180, 260)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(180, 260),
				new Point(180, 310)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(180, 310),
				new Point(210, 340)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(210, 340),
				new Point(240, 340)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(240, 340),
				new Point(270, 310)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 310),
				new Point(270, 280)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 280),
				new Point(270, 210)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 210),
				new Point(270, 150)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 150),
				new Point(270, 20)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 20),
				new Point(100, 20)));
		mainFloor = theNewRoom;
		rooms.add(theNewRoom);

		// Room #1 timsRoom (top left)
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(20, 20),
				new Point(20, 100)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(20, 100),
				new Point(100, 100)));
		theNewDoor = new Door(20, 5);
		theNewWall = WallFactory.getWallWithPoints(new Point(100, 100),
				new Point(100, 20));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 20),
				new Point(20, 20)));
		timsRoom = theNewRoom;
		rooms.add(theNewRoom);

		// Room #2 (second top left)
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(20, 100),
				new Point(20, 160)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(20, 160),
				new Point(100, 160)));
		theNewDoor = new Door(20, 5);
		theNewWall = WallFactory.getWallWithPoints(new Point(100, 160),
				new Point(100, 100));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 100),
				new Point(20, 100)));
		rooms.add(theNewRoom);

		// Room #3 (third top left)
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(20, 160),
				new Point(20, 220)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(20, 220),
				new Point(100, 220)));
		theNewDoor = new Door(20, 5);
		theNewWall = WallFactory.getWallWithPoints(new Point(100, 220),
				new Point(100, 160));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 160),
				new Point(20, 160)));
		rooms.add(theNewRoom);

		// Room #4 small kitchen
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(20, 220),
				new Point(20, 260)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(20, 260),
				new Point(100, 260)));
		theNewDoor = new Door();
		theNewWall = WallFactory.getWallWithPoints(new Point(100, 260),
				new Point(100, 220));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 220),
				new Point(20, 220)));
		rooms.add(theNewRoom);

		// Room #5 womens restroom
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(120, 260),
				new Point(120, 310)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(120, 310),
				new Point(150, 310)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(150, 310),
				new Point(150, 260)));
		theNewDoor = new Door();
		theNewWall = WallFactory.getWallWithPoints(new Point(150, 260),
				new Point(120, 260));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		rooms.add(theNewRoom);

		// Room #6 mens restroom
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(150, 260),
				new Point(150, 310)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(150, 310),
				new Point(180, 310)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(180, 310),
				new Point(180, 260)));
		theNewDoor = new Door();
		theNewWall = WallFactory.getWallWithPoints(new Point(180, 260),
				new Point(150, 260));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		rooms.add(theNewRoom);

		// Room #7 secret room
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 310),
				new Point(360, 310)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 310),
				new Point(360, 280)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 280),
				new Point(270, 280)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 280),
				new Point(270, 310)));
		rooms.add(theNewRoom);

		// crazy rooms
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 280),
				new Point(360, 280)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 280),
				new Point(360, 210)));
		theNewWall = WallFactory.getWallWithPoints(new Point(360, 210),
				new Point(270, 210));
		theNewWall.addDoor(new Door());
		theNewRoom.addWall(theNewWall);
		rooms.add(theNewRoom);

		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 210),
				new Point(270, 210)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 210),
				new Point(360, 150)));
		theNewWall = WallFactory.getWallWithPoints(new Point(360, 150),
				new Point(270, 150));
		theNewWall.addDoor(new Door(80, 5));
		theNewRoom.addWall(theNewWall);
		theNewWall = WallFactory.getWallWithPoints(new Point(270, 150),
				new Point(270, 210));
		theNewWall.addDoor(new Door());
		theNewRoom.addWall(theNewWall);
		rooms.add(theNewRoom);

		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 150),
				new Point(360, 120)));
		theNewWall = WallFactory.getWallWithPoints(new Point(360, 120),
				new Point(270, 120));
		theNewWall.addDoor(new Door(10, 5));
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 150),
				new Point(270, 120)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 150),
				new Point(360, 150)));
		rooms.add(theNewRoom);

		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 120),
				new Point(360, 100)));
		theNewWall = WallFactory.getWallWithPoints(new Point(360, 100),
				new Point(270, 100));
		theNewWall.addDoor(new Door(80, 5));
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 120),
				new Point(270, 100)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 120),
				new Point(360, 120)));
		rooms.add(theNewRoom);

		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 100),
				new Point(360, 20)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 20),
				new Point(270, 20)));
		theNewWall = WallFactory.getWallWithPoints(new Point(270, 20),
				new Point(270, 100));
		theNewWall.addDoor(new Door(10, 5));
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 100),
				new Point(360, 100)));

		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(310, 50),
				new Point(310, 70)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(310, 70),
				new Point(330, 70)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(330, 70),
				new Point(330, 50)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(330, 50),
				new Point(310, 50)));
		rooms.add(theNewRoom);

		return rooms;

	}

	private HashSet<AbstractAction> generateActionPools() {

		return null;
	}

}
