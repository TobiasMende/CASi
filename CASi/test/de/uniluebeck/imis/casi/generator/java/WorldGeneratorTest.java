/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2011 2012  Moritz Bürger, Marvin Frick, Tobias Mende
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

import static org.junit.Assert.*;

import org.junit.Test;

import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.simulation.model.World;

/**
 * @author Marvin Frick
 * 
 */
public class WorldGeneratorTest {

	@Test
	public void testGenerateAllSets() {
		IWorldGenerator gen = new de.uniluebeck.imis.casi.generator.java.WorldGenerator();

		World generatedWorld = gen.generateWorld();

		assertNotNull("World Object is null!", generatedWorld);

		try {
			assertNotNull("Backroundimage is null!",
					generatedWorld.getBackgroundImage());
			assertNotNull("Actuators or Sensors are null!", generatedWorld.getInteractionComponents());
			assertNotNull("Agents are null!", generatedWorld.getAgents());
			assertNotNull("Rooms are null!", generatedWorld.getRooms());

			assertNotNull("Componets are null!", generatedWorld.getComponents());

			assertNotNull("The doorgraph is null!", generatedWorld.getDoorGraph());
			assertNotNull("Starttime is null!", generatedWorld.getStartTime());

		} catch (IllegalAccessException e) {
			fail("World was not sealed!");
		}
	}

}
