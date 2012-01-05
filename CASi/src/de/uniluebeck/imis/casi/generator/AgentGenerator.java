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
package de.uniluebeck.imis.casi.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulations.mate.generator.java.WorldGenerator;

/**
 * @author Marvin Frick
 * 
 *         This class creates a singleton object that can be used to create
 *         Agents. It stores a list of already created agents and offers access
 *         to them through either name or id or whatever.
 * 
 */
public class AgentGenerator {
	private static final Logger log = Logger.getLogger(AgentGenerator.class
			.getName());
	
	/** The instance of this singleton */
	private static AgentGenerator instance;

	/**
	 * The List of already created agents.
	 */
	private Set<Agent> alreadyCreatedAgents = new HashSet<Agent>();

	/**
	 * The private constructor of this singleton
	 */
	private AgentGenerator() {
		// just here for prohibiting external access
	}

	/**
	 * Getter for the instance of this singleton
	 * 
	 * @return the instance
	 */
	public static AgentGenerator getInstance() {
		if (instance == null) {
			instance = new AgentGenerator();
		}
		return instance;
	}

	/**
	 * Add a new agent to this collection
	 * 
	 * @param newAgent
	 *            the new Agent
	 */
	public void newAgent(Agent newAgent) {
		alreadyCreatedAgents.add(newAgent);
	}

	/**
	 * Returns an Agent with a given name
	 * 
	 * @param nameToLookFor
	 * @return the Agent with this name or (CAUTION) null if this agent cannot
	 *         be found!
	 */
	public Agent findAgentByName(String nameToLookFor) {
		for (Agent agent : alreadyCreatedAgents) {
			if (agent.getName().equals(nameToLookFor)) {
				return agent;
			}
		}
		log.warning(String.format("couldn't find agent %s, mispelled it?", nameToLookFor));
		return null;
	}

	/**
	 * Returns an Agent with a given identifier
	 * 
	 * @param identifierToLookFor
	 * @return the Agent with this name or (CAUTION) null if this agent cannot
	 *         be found!
	 */
	public Agent findAgentByIdentifier(String identifierToLookFor) {
		for (Agent agent : alreadyCreatedAgents) {
			if (agent.getIdentifier().equals(identifierToLookFor)) {
				return agent;
			}
		}
		log.warning(String.format("couldn't find agent %s, mispelled it?", identifierToLookFor));
		return null;
	}

	/**
	 * Returns all Agents with a given type
	 * 
	 * @param typeToLookFor
	 *            for what type should be looked
	 * @return all Agents with this type or an empty Vector if not a single one
	 *         was found.
	 */
	public Vector<Agent> findAgentByType(String typeToLookFor) {
		Vector<Agent> res = new Vector<Agent>();
		for (Agent agent : alreadyCreatedAgents) {
			if (agent.getType().equals(typeToLookFor)) {
				res.add(agent);
			}
		}
		if(res.isEmpty()){
			log.warning(String.format("couldn't find any agent with type %s, mispelled it?", typeToLookFor));	
		}
		return res;
	}

	/**
	 * Takes every Agent in this List and assure it has a default room. Default
	 * rooms are associated randomly.
	 */
	public void fillAllAgentsWithDefaultRoom() {
		// If agents have no default positions: choose a random one for
		for (Agent a : AgentGenerator.getInstance().getAll()) {
			if (a.getDefaultPosition() == null) {
				List<Room> r = new ArrayList<Room>(RoomGenerator.getInstance()
						.getAll());
				Collections.shuffle(r);
				a.setDefaultPosition(r.get(0));
			}
		}
	}

	/**
	 * Getter for the list of agents
	 * 
	 * @return the Vector with all the agents
	 */
	public Set<Agent> getAll() {
		return alreadyCreatedAgents;
	}

}
