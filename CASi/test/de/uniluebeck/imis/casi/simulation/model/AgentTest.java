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

import org.junit.Test;

public class AgentTest {

	@Test
	public void testContainsIPosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainsPoint2D() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShapeRepresentation() {
		fail("Not yet implemented");
	}

	@Test
	public void testAgentStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testAgentStringStringStringIPosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testAgentStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetState() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsTemplate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTodoList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActionPool() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTodoList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddActionToPool() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddActionToList() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetActionPool() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddListener() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveListener() {
		fail("Not yet implemented");
	}

	@Test
	public void testTimeChanged() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		Agent a = new Agent("testID", "Name");
		Agent b = a.clone();
		assertFalse("Agents are same object", a == b);
		assertTrue("Agents are not equal", a.equals(b));
	}

	@Test
	public void testSimulationPaused() {
		fail("Not yet implemented");
	}

	@Test
	public void testSimulationStopped() {
		fail("Not yet implemented");
	}

	@Test
	public void testSimulationStarted() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCentralPoint() {
		fail("Not yet implemented");
	}

}
