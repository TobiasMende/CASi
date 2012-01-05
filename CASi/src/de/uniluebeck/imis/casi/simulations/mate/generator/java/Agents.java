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

import de.uniluebeck.imis.casi.generator.AgentGenerator;
import de.uniluebeck.imis.casi.generator.RoomGenerator;
import de.uniluebeck.imis.casi.simulation.factory.WallFactory;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * @author Marvin Frick
 *
 */
public class Agents {

	/**
	 * Fills the AgentsGenerator singleton object with all the agents.
	 * 
	 * Put all your Agents here!
	 */
	public static void generateAgents(){
		
		AgentGenerator agents = AgentGenerator.getInstance();
		RoomGenerator rooms = RoomGenerator.getInstance();
		Agent tempAgent = null;

		// if we need a lot of agents...
		for (int i = 0; i < 0; i++) {
			tempAgent = new Agent("agent_" + i + "_smith", "A. Smith the " + i,
					"crowd");
			agents.newAgent(tempAgent);
		}

		// ##########
		// Father Moneymaker
		// ##########
		tempAgent = new Agent("casi_father_moneymaker", "Father Moneymaker",
				"candidates");
		agents.newAgent(tempAgent);

		// ##########
		// Tim
		// ##########
		agents.newAgent(new Agent("casi_tim", "Tim", "candidates"));
		Agent tim = agents.findAgentByName("Tim");
		tim.setCurrentPosition(rooms.findRoomByIdentifier("tim'sRoom"));
		tim.setDefaultPosition(rooms.findRoomByIdentifier("tim'sRoom"));

		// ##########
		// And I
		// ##########
		tempAgent = new Agent("casi_and_i", "And I", "candidates");
		agents.newAgent(tempAgent);
		tempAgent.setDefaultPosition(RoomGenerator.getInstance().findRoomByIdentifier("crazyRoom"));
		tempAgent.setCurrentPosition(RoomGenerator.getInstance().findRoomByIdentifier("tim'sRoom"));

		// ##########
		// Tentladies
		// ##########
		for (int i = 0; i < 3; i++) {

			agents.newAgent(new Agent("casi_tendlady_" + i, "Tentlady" + i,
					"tentLady"));
			AgentGenerator
					.getInstance()
					.findAgentByIdentifier("casi_tendlady_" + i)
					.setDefaultPosition(
							RoomGenerator.getInstance().findRoomByIdentifier(
									"mainFloor"));
		}

		// ##########
		// Crazy-Guy
		// ##########
		agents.newAgent(new Agent("casi_crazy_guy", "Crazy Guy", "randomGuy"));

		
	}
}
