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
package de.uniluebeck.imis.casi.generator.java.mate;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.simulation.factory.WallFactory;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.Wall;
import de.uniluebeck.imis.casi.simulation.model.World;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction.TYPE;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;
import de.uniluebeck.imis.casi.simulation.model.actions.GoAndSpeakTo;
import de.uniluebeck.imis.casi.simulation.model.actions.Move;
import de.uniluebeck.imis.casi.simulation.model.actions.SpeakTo;
import de.uniluebeck.imis.casi.simulation.model.mateComponents.Cube;
import de.uniluebeck.imis.casi.simulation.model.mateComponents.Desktop;
import de.uniluebeck.imis.casi.simulation.model.mateComponents.DoorLight;
import de.uniluebeck.imis.casi.simulation.model.mateComponents.DoorSensor;
import de.uniluebeck.imis.casi.simulation.model.mateComponents.Mike;

public class WorldGenerator implements IWorldGenerator {

	private static final Logger log = Logger.getLogger(WorldGenerator.class
			.getName());

	World tempWorld = new World();
	Room timsRoom, mainFloor, crazyRoom, randomRoom, womensRoom, kitchen;
	HashSet<Room> rooms = new HashSet<Room>();
	HashSet<Agent> agents;
	Agent tim, crazyGuy;

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

			// rooms
			rooms = generateRooms();
			tempWorld.setRooms(rooms);

			// generate the agents
			agents = generateAgents();
			tempWorld.setAgents(agents);

			// actuators & sensors
			HashSet<AbstractInteractionComponent> interactionComps = generateActuators();
			tempWorld.setInteractionComponents(interactionComps);

			tempWorld.setComponents(new HashSet<AbstractComponent>());
			tempWorld.seal();
		} catch (IllegalAccessException e) {
			log.severe("Illegal Access:" + e.fillInStackTrace());
		} catch (ParseException e) {
			log.severe("Parse Exception:" + e.fillInStackTrace());
		} catch (Exception e) {
			log.severe("Unknown Exception: " + e.fillInStackTrace());
		}

		return tempWorld;
	}

	/**
	 * @return
	 */
	private HashSet<AbstractInteractionComponent> generateActuators() {
		HashSet<AbstractInteractionComponent> res = new HashSet<AbstractInteractionComponent>();
		// TODO generate more actuators!
		
		res.addAll(addThingsToRoomWithOwner(timsRoom, tim));
		res.addAll(addThingsToRoomWithOwner(crazyRoom, crazyGuy));
		
		return res;
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

		//##########
		// Father Moneymaker
		//##########
		tempAgent = new Agent("casi_father_moneymaker", "Father Moneymaker",
				"candidates");
		agents.add(tempAgent);
		
		//##########
		// Tim
		//##########
		tim = new Agent("casi_tim", "Tim", "candidates");
		tim.setCurrentPosition(timsRoom);
		tim.setDefaultPosition(timsRoom);
		agents.add(tim);
		
		//##########
		// And I
		//##########
		tempAgent = new Agent("casi_and_i", "And I", "candidates");
		tempAgent.setDefaultPosition(crazyRoom);
		tempAgent.setCurrentPosition(timsRoom);
		Agent andI = tempAgent;
		AbstractAction goHome = new Move(crazyRoom);
		goHome.setEarliestStartTime(tempWorld.getStartTime().plus(50));
		tempAgent.addActionToList(goHome);
		agents.add(tempAgent);

		//##########
		// Tentladies
		//##########
		for (int i = 0; i < 3; i++) {
			tempAgent = new Agent("casi_tendlady_" + i, "Tentlady" + i,
					"tendladies");
			tempAgent.setDefaultPosition(mainFloor);
			tempAgent = generateActionsForTentLady(andI, tempAgent);

			agents.add(tempAgent);
		}
		
		//##########
		// Crazy-Guy
		//##########
		crazyGuy = new Agent("casi_crazy_guy", "Crazy Guy", "randomGuy");
		ComplexAction runCrazy = new ComplexAction();
		runCrazy.addSubAction(new Move(crazyRoom));
		runCrazy.addSubAction(new Move(randomRoom));
		runCrazy.addSubAction(new Move(womensRoom));
		runCrazy.addSubAction(new Move(kitchen));
		runCrazy.setEarliestStartTime(tempWorld.getStartTime().plus(15));
		for (Room r : rooms) {
			runCrazy.addSubAction(new Move(r));
		}
		List<Room> temp = new ArrayList<Room>(rooms);
		Collections.shuffle(temp);
		for (Room r : temp) {
			runCrazy.addSubAction(new Move(r));
		}
		tempAgent.addActionToList(runCrazy);
		agents.add(crazyGuy);

		// If agents have no default positions: choose a random one
		for (Agent a : agents) {
			if (a.getDefaultPosition() == null) {
				List<Room> r = new ArrayList<Room>(rooms);
				Collections.shuffle(r);
				a.setDefaultPosition(r.get(0));
			}
		}
		return agents;

	}


	/**
	 * Adds things to given room with a given owner. Used for doorSensors and DoorPlates...
	 * 
	 * @param room TODO
	 * @param owner TODO
	 * 
	 */
	private HashSet<AbstractInteractionComponent> addThingsToRoomWithOwner(Room room, Agent owner) {
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

	private Agent generateActionsForAgentSmith(Agent smith, Point2D center) {

		return smith;
	}

	private Agent generateActionsForTentLady(Agent tim, Agent tentLady) {

		AbstractAction tempAction = new Move(timsRoom);
		AbstractAction move2 = tempAction.clone();
		tempAction.setType(TYPE.NORMAL);
		tempAction.setPriority(5);
		tempAction.setEarliestStartTime(tempWorld.getStartTime().plus(
				1 + new Random().nextInt(10)));
		tentLady.addActionToList(tempAction);

		AbstractAction speak = new GoAndSpeakTo(tim, 10);
		speak.setType(TYPE.NORMAL);
		speak.setEarliestStartTime(tempWorld.getStartTime().plus(20));
		tentLady.addActionToList(speak);
		ComplexAction speakInTimsRoom = new ComplexAction();
		speakInTimsRoom.addSubAction((AtomicAction) move2);
		speakInTimsRoom.addSubAction(new SpeakTo(tim, 10));
		speakInTimsRoom.setEarliestStartTime(tempWorld.getStartTime().plus(30));
		AbstractAction testMove = new Move(mainFloor);
		testMove.setEarliestStartTime(speakInTimsRoom.getEarliestStartTime()
				.plus(10));
		tentLady.addActionToList(speakInTimsRoom);
		tentLady.addActionToList(testMove);
		return tentLady;
	}

	/**
	 * Creates some rooms. Testing stuff.
	 * 
	 * @return {@link HashSet} of {@link Room}
	 */
	private HashSet<Room> generateRooms() {

		log.info("generating rooms");

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
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(180, 260),
				new Point(150, 260)));
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
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 210),
				new Point(270, 280)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 210),
				new Point(270, 150)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 150),
				new Point(270, 120)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 120),
				new Point(270, 100)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 100),
				new Point(270, 20)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 20),
				new Point(100, 20)));
		mainFloor = theNewRoom;
		mainFloor.setIdentifier("Main Floor");
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
		timsRoom.setIdentifier("Tim's Room");
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
		kitchen = theNewRoom;
		rooms.add(theNewRoom);
		theNewRoom.setIdentifier("Kitchen");

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
		womensRoom = theNewRoom;
		theNewRoom.setIdentifier("Womens restroom");

		// Room #6 mens restroom
		theNewRoom = new Room();
		theNewWall = WallFactory.getWallWithPoints(new Point(180, 260),
				new Point(150, 260));
		theNewDoor = new Door();
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(150, 260),
				new Point(150, 310)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(150, 310),
				new Point(180, 310)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(180, 310),
				new Point(180, 260)));

		rooms.add(theNewRoom);
		theNewRoom.setIdentifier("Mens restroom");

		// Room #7 secret room
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 310),
				new Point(360, 310)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 310),
				new Point(360, 280)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 280),
				new Point(360, 280)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(270, 280),
				new Point(270, 310)));
		rooms.add(theNewRoom);
		theNewRoom.setIdentifier("Secret Room");

		// crazy rooms
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 280),
				new Point(270, 280)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(360, 210),
				new Point(360, 280)));
		theNewWall = WallFactory.getWallWithPoints(new Point(270, 210),
				new Point(360, 210));
		theNewWall.addDoor(new Door());
		theNewRoom.addWall(theNewWall);
		theNewWall = WallFactory.getWallWithPoints(new Point(270, 280),
				new Point(270, 210));
		rooms.add(theNewRoom);
		crazyRoom = theNewRoom;
		theNewRoom.setIdentifier("Crazy Room");

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
		randomRoom = theNewRoom;

		// this is the pillar like walls in the center of the room, seems we
		// need a better idea for that
		// theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(310, 50),
		// new Point(310, 70)));
		// theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(310, 70),
		// new Point(330, 70)));
		// theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(330, 70),
		// new Point(330, 50)));
		// theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(330, 50),
		// new Point(310, 50)));
		rooms.add(theNewRoom);

		return rooms;

	}

	private HashSet<AbstractAction> generateActionPools() {

		return null;
	}

}
