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
	public void testClone() {
		Agent a = new Agent("testID", "Name");
		Agent b = a.clone();
		assertFalse("Agents are same object", a == b);
		assertTrue("Agents are not equal", a.equals(b));
	}
}
