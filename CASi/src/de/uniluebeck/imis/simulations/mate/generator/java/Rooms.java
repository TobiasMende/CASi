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
package de.uniluebeck.imis.simulations.mate.generator.java;

import java.awt.Point;

import de.uniluebeck.imis.casi.generator.RoomGenerator;
import de.uniluebeck.imis.casi.simulation.factory.WallFactory;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * @author Marvin Frick
 *
 */
public class Rooms {

	/**
	 * Fills the RoomGenerator singleton object with all the rooms.
	 * 
	 * Put all your rooms here!
	 */
	public static void generateRooms(){

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
		theNewRoom.setIdentifier("mainFloor");
		RoomGenerator.getInstance().newRoom(theNewRoom);

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
		theNewRoom.setIdentifier("tim'sRoom");
		RoomGenerator.getInstance().newRoom(theNewRoom);

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
		RoomGenerator.getInstance().newRoom(theNewRoom);

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
		RoomGenerator.getInstance().newRoom(theNewRoom);

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
		theNewRoom.setIdentifier("kitchen");
		RoomGenerator.getInstance().newRoom(theNewRoom);

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
		theNewRoom.setIdentifier("womensRestroom");
		RoomGenerator.getInstance().newRoom(theNewRoom);
		
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
		theNewRoom.setIdentifier("mensRestroom");
		RoomGenerator.getInstance().newRoom(theNewRoom);
		
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
		theNewRoom.setIdentifier("secretRoom");
		RoomGenerator.getInstance().newRoom(theNewRoom);

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
		theNewRoom.setIdentifier("crazyRoom");
		RoomGenerator.getInstance().newRoom(theNewRoom);

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
		RoomGenerator.getInstance().newRoom(theNewRoom);

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
		RoomGenerator.getInstance().newRoom(theNewRoom);

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
		RoomGenerator.getInstance().newRoom(theNewRoom);

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
		theNewRoom.setIdentifier("theRandomRoom");

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
		RoomGenerator.getInstance().newRoom(theNewRoom);

	}
}
