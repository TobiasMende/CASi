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

import java.util.concurrent.ConcurrentHashMap;

import de.uniluebeck.imis.casi.generator.AgentCollector;
import de.uniluebeck.imis.casi.generator.RoomCollector;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.ConfigMap;

/**
 * Agent generator file with static methods that generate all the agents for
 * MATe simulation environment World.
 * 
 * Put all your agents in here!
 * 
 * @author Marvin Frick
 * 
 */
public class Agents {

	/**
	 * Fills the AgentsGenerator singleton object with all the agents.
	 * 
	 * Put all your Agents here!
	 */
	public static void generateAgents() {

		AgentCollector agentC = AgentCollector.getInstance();
		RoomCollector rooms = RoomCollector.getInstance();
		Agent tempAgent = null;

		// if we need a lot of agents...
		for (int i = 0; i < 0; i++) {
			tempAgent = new Agent("agent_" + i + "_smith", "A. Smith the " + i,
					"crowd");
			agentC.newAgent(tempAgent);
		}

		// ##########
		// Hermann Matsumbishi
		// ##########
		tempAgent = new Agent("casi_hermann_matsumbishi",
				"Hermann Matsumbishi", "teamleader");
		tempAgent.setDefaultPosition(rooms
				.findRoomByIdentifier("officeHermann"));
		agentC.newAgent(tempAgent);

		// ##########
		// Zwotah Zwiebel
		// ##########
		agentC.newAgent(new Agent("casi_zwotah_zwiebel", "Zwotah Zwiebel",
				"coworker"));
		Agent zwotah = agentC.findAgentByName("Zwotah Zwiebel");
		zwotah.setDefaultPosition(rooms.findRoomByIdentifier("officeZwotah"));
		// zwotah is very attentive, he turns his cube more nearly every time
		zwotah.addConfiguration(ConfigMap.TURN_CUBE_PROBABILITY, 0.9);
		
		// ##########
		// Dagobert Dreieck
		// ##########
		tempAgent = new Agent("casi_dagobert_dreieck", "Dagobert Dreieck",
				"coworker");
		tempAgent.setDefaultPosition(rooms
				.findRoomByIdentifier("officeDagobert"));
		agentC.newAgent(tempAgent);

		// ##########
		// Felix Freudentanz
		// ##########
		tempAgent = new Agent("casi_felix_freudentanz", "Felix Freudentanz",
				"teamLeader");
		tempAgent.setDefaultPosition(rooms.findRoomByIdentifier("officeFelix"));
		// Felix moves very fast!
		tempAgent.addConfiguration(ConfigMap.MOVE_SPEED, 1.5);
		agentC.newAgent(tempAgent);

		// ##########
		// Susi Sekretärin
		// ##########
		tempAgent = new Agent("casi_susi_sekretärin", "Susi Sekretärin",
				"secretary");
		tempAgent.setDefaultPosition(rooms.findRoomByIdentifier("officeSusi"));
		agentC.newAgent(tempAgent);

		// ##########
		// Rudi Random
		// ##########
		tempAgent = new Agent("casi_rudi_random", "Rudi Random",
				"coworker");
		tempAgent.setDefaultPosition(rooms.findRoomByIdentifier("officeRudi"));
		agentC.newAgent(tempAgent);
	}
}
