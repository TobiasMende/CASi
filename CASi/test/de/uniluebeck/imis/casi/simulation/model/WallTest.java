/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
 */
package de.uniluebeck.imis.casi.simulation.model;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.Test;

public class WallTest {

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testWall() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddDoor() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStartPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEndPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCoordinates() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainsIPosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainsPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShapeRepresentation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCentralPoint() {
		int counter = 0;
		// Testing 1000 random walls
		for (int i = 0; i < 1000; i++) {
			// Declaring a Random wall
			int x = (int) Math.round(Math.random() * 1000);
			int y = (int) Math.round(Math.random() * 1000);
			Point start = new Point(x, y);
			x = (int) Math.round(Math.random() * 1000);
			y = (int) Math.round(Math.random() * 1000);
			Point end = new Point(x, y);
			Wall w = new Wall(start, end);
			Point2D p = w.getCentralPoint();
			if (p != null) {
				counter++;
			}
		}
		assertEquals("Not all calculated central points lay on the wall", 1000,
				counter);
	}

}
