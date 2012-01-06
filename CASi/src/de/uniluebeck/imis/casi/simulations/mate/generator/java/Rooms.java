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

import java.awt.Point;

import de.uniluebeck.imis.casi.generator.RoomCollector;
import de.uniluebeck.imis.casi.simulation.factory.WallFactory;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * Room generator file with static methods that generate all the rooms for the
 * MATe simulation environment World.
 * 
 * Put all your rooms in here!
 * 
 * @author Marvin Frick
 * 
 */
public class Rooms {

	/**
	 * Fills the RoomGenerator singleton object with all the rooms.
	 * 
	 * Put all your rooms here!
	 */
	public static void generateRooms() {

		Wall theNewWall;
		Door theNewDoor;
		Room theNewRoom = new Room();

		// main floor
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(80, 0),
				new Point(80, 80)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(80, 80),
				new Point(80, 140)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(80, 140),
				new Point(80, 200)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(80, 200),
				new Point(80, 240)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(80, 240),
				new Point(100, 240)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 240),
				new Point(130, 240)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(160, 240),
				new Point(130, 240)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(160, 240),
				new Point(160, 290)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(160, 290),
				new Point(190, 320)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(190, 320),
				new Point(220, 320)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(220, 320),
				new Point(250, 290)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 290),
				new Point(250, 260)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 190),
				new Point(250, 260)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 190),
				new Point(250, 130)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 130),
				new Point(250, 100)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 100),
				new Point(250, 80)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 80),
				new Point(250, 0)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 0),
				new Point(80, 0)));
		theNewRoom.setIdentifier("mainFloor");
		RoomCollector.getInstance().newRoom(theNewRoom);

		// Room #1 timsRoom (top left)
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(0, 0),
				new Point(0, 80)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(0, 80),
				new Point(80, 80)));
		theNewDoor = new Door(20, 5);
		theNewWall = WallFactory.getWallWithPoints(new Point(80, 80),
				new Point(80, 0));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(80, 0),
				new Point(0, 0)));
		theNewRoom.setIdentifier("tim'sRoom");
		RoomCollector.getInstance().newRoom(theNewRoom);

		// Room #2 (second top left)
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(0, 80),
				new Point(0, 140)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(0, 140),
				new Point(80, 140)));
		theNewDoor = new Door(20, 5);
		theNewWall = WallFactory.getWallWithPoints(new Point(80, 140),
				new Point(80, 80));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(80, 80),
				new Point(0, 80)));
		RoomCollector.getInstance().newRoom(theNewRoom);

		// Room #3 (third top left)
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(0, 140),
				new Point(0, 200)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(0, 200),
				new Point(80, 200)));
		theNewDoor = new Door(20, 5);
		theNewWall = WallFactory.getWallWithPoints(new Point(80, 200),
				new Point(80, 140));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(80, 140),
				new Point(0, 140)));
		RoomCollector.getInstance().newRoom(theNewRoom);

		// Room #4 small kitchen
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(0, 200),
				new Point(0, 240)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(0, 240),
				new Point(80, 240)));
		theNewDoor = new Door();
		theNewWall = WallFactory.getWallWithPoints(new Point(80, 240),
				new Point(80, 200));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(80, 200),
				new Point(0, 200)));
		theNewRoom.setIdentifier("kitchen");
		RoomCollector.getInstance().newRoom(theNewRoom);

		// Room #5 womens restroom
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 240),
				new Point(100, 290)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(100, 290),
				new Point(130, 290)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(130, 290),
				new Point(130, 240)));
		theNewDoor = new Door();
		theNewWall = WallFactory.getWallWithPoints(new Point(130, 240),
				new Point(100, 240));
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.setIdentifier("womensRestroom");
		RoomCollector.getInstance().newRoom(theNewRoom);

		// Room #6 mens restroom
		theNewRoom = new Room();
		theNewWall = WallFactory.getWallWithPoints(new Point(160, 240),
				new Point(130, 240));
		theNewDoor = new Door();
		theNewWall.addDoor(theNewDoor);
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(130, 240),
				new Point(130, 290)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(130, 290),
				new Point(160, 290)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(160, 290),
				new Point(160, 240)));
		theNewRoom.setIdentifier("mensRestroom");
		RoomCollector.getInstance().newRoom(theNewRoom);

		// Room #7 secret room
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 290),
				new Point(340, 290)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(340, 290),
				new Point(340, 260)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 260),
				new Point(340, 260)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 260),
				new Point(250, 290)));
		theNewRoom.setIdentifier("secretRoom");
		RoomCollector.getInstance().newRoom(theNewRoom);

		// crazy rooms
		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(340, 260),
				new Point(250, 260)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(340, 190),
				new Point(340, 260)));
		theNewWall = WallFactory.getWallWithPoints(new Point(250, 190),
				new Point(340, 190));
		theNewWall.addDoor(new Door());
		theNewRoom.addWall(theNewWall);
		theNewWall = WallFactory.getWallWithPoints(new Point(250, 260),
				new Point(250, 190));
		theNewRoom.setIdentifier("crazyRoom");
		RoomCollector.getInstance().newRoom(theNewRoom);

		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(340, 190),
				new Point(250, 190)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(340, 190),
				new Point(340, 130)));
		theNewWall = WallFactory.getWallWithPoints(new Point(340, 130),
				new Point(250, 130));
		theNewWall.addDoor(new Door(80, 5));
		theNewRoom.addWall(theNewWall);
		theNewWall = WallFactory.getWallWithPoints(new Point(250, 130),
				new Point(250, 190));
		theNewWall.addDoor(new Door());
		theNewRoom.addWall(theNewWall);
		theNewRoom.setIdentifier("roomAfterCrazyRoom");
		RoomCollector.getInstance().newRoom(theNewRoom);

		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(340, 130),
				new Point(340, 100)));
		theNewWall = WallFactory.getWallWithPoints(new Point(340, 100),
				new Point(250, 100));
		theNewWall.addDoor(new Door(10, 5));
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 130),
				new Point(250, 100)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 130),
				new Point(340, 130)));
		RoomCollector.getInstance().newRoom(theNewRoom);

		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(340, 100),
				new Point(340, 80)));
		theNewWall = WallFactory.getWallWithPoints(new Point(340, 80),
				new Point(250, 80));
		theNewWall.addDoor(new Door(80, 5));
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 100),
				new Point(250, 80)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 100),
				new Point(340, 100)));
		RoomCollector.getInstance().newRoom(theNewRoom);

		theNewRoom = new Room();
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(340, 80),
				new Point(340, 0)));
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(340, 0),
				new Point(250, 0)));
		theNewWall = WallFactory.getWallWithPoints(new Point(250, 0),
				new Point(250, 80));
		theNewWall.addDoor(new Door(10, 5));
		theNewRoom.addWall(theNewWall);
		theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(250, 80),
				new Point(340, 80)));
		theNewRoom.setIdentifier("theRandomRoom");

		// this is the pillar like walls in the center of the room, seems we
		// need a better idea for that
		// theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(290, 30),
		// new Point(290, 50)));
		// theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(290, 50),
		// new Point(310, 50)));
		// theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(310, 50),
		// new Point(310, 30)));
		// theNewRoom.addWall(WallFactory.getWallWithPoints(new Point(310, 30),
		// new Point(200, 30)));
		RoomCollector.getInstance().newRoom(theNewRoom);

	}
}
