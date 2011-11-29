/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
 */
package de.uniluebeck.imis.casi.utils.pathfinding;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * This test tries to verify the correctness of the A<sup>*</sup> implementation
 * for in-room-purposes
 * 
 * @author Tobias Mende
 * 
 */
public class InRoomPathSolverTest {
	private static final Logger log = Logger
			.getLogger(InRoomPathSolverTest.class.getName());
	private static Room room;

	@BeforeClass
	public static void setup() {
		CASi.setupLogging();

		Wall w1 = new Wall(new Point(0, 0), new Point(200, 0));
		Wall w2 = new Wall(new Point(200, 0), new Point(200, 200));
		Wall w3 = new Wall(new Point(200, 200), new Point(600, 200));
		Wall w4 = new Wall(new Point(600, 200), new Point(600, 0));
		Wall w5 = new Wall(new Point(600, 0), new Point(800, 0));
		Wall w6 = new Wall(new Point(800, 0), new Point(800, 500));
		Wall w7 = new Wall(new Point(800, 500), new Point(0, 500));
		Wall w8 = new Wall(new Point(0, 500), new Point(0, 0));

		room = new Room();
		room.addWall(w1);
		room.addWall(w2);
		room.addWall(w3);
		room.addWall(w4);
		room.addWall(w5);
		room.addWall(w6);
		room.addWall(w7);
		room.addWall(w8);
	}

	/**
	 * The room:
	 * 
	 * -*-- -----*--- | | | | | | | | | |_________| | | | | |
	 * ----------------------
	 */
	@Test
	public void testComputeWithValidDestination() {

		Point destination = new Point(700, 100);

		InRoomPathSolver solver = new InRoomPathSolver(room, destination);
		List<Point> path = solver.compute(new Point(10, 10));

		assertNotNull("No path found", path);
		log.info(path.toString());
		log.info("Path length = " + path.size());
		log.info("Next test! Start not in room");
		path = solver.compute(new Point(0, 10000));
		assertNull("A path was found, but there is none", path);
	}

	@Test
	public void testComputeWithInvalidDestination() {
		log.info("Next test! End not in room");
		@SuppressWarnings("unused")
		InRoomPathSolver solver;
		try {
			solver = new InRoomPathSolver(room, new Point(10000, 10000));
			fail("Expected exception");
		} catch (IllegalArgumentException e) {
			// Nothing to do here.
		}
	}

	/**
	 * This method test finding a way from a point on the wall to another point
	 * on the wall The room:
	 * 
	 * ------ -------- | * | | | | | | * | | |_________| | | | | |
	 * ----------------------
	 * 
	 */
	@Test
	public void testComputeDoorToDoorWay() {
		log.info("Testing door to door way (point on wall to point on wall)");
		InRoomPathSolver solver = new InRoomPathSolver(room, new Point(50, 0));
		List<Point> path = solver.compute(new Point(700, 0));
		assertNotNull("No path found", path);
		log.info(path.toString());
		log.info("Path length = " + path.size());
	}

}
