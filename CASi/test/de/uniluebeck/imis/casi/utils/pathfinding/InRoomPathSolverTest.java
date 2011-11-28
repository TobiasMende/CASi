package de.uniluebeck.imis.casi.utils.pathfinding;

import java.awt.Point;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;

public class InRoomPathSolverTest {
	private static final Logger log = Logger.getLogger(InRoomPathSolverTest.class.getName());
	private Room room;
	@Before
	public void setup() {
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

	@Test
	public void testComputeWithValidDestination() {
		
		
		Point destination = new Point(700,100);
		
		InRoomPathSolver solver = new InRoomPathSolver(room, destination);
		List<Point> path = solver.compute(new Point(10, 10));
		
		assertNotNull("No path found", path);
		log.info(path.toString());
		log.info("Path length ="+path.size());
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
			solver = new InRoomPathSolver(room, new Point(10000,10000));
			fail("Expected exception");
		} catch (IllegalArgumentException e) {
			// Nothing to do here.
		}
	}

}
