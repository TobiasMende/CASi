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
package de.uniluebeck.imis.casi.simulation.model;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.Test;

public class WallTest {

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
