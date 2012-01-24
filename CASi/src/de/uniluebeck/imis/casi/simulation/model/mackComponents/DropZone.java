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
package de.uniluebeck.imis.casi.simulation.model.mackComponents;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.communication.ICommunicationHandler;
import de.uniluebeck.imis.casi.communication.mack.MACKProtocolFactory;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

/**
 * This class represents the drop zone which is used by the mate system.
 * 
 * 
 * @author Tobias Mende
 * 
 */
public class DropZone extends AbstractInteractionComponent {

	/**
	 * serialization identifier
	 */
	private static final long serialVersionUID = 5267989276308595425L;

	private ArrayList<Agent> agentKeys = new ArrayList<Agent>();

	/**
	 * Constructor for a new drop zone which recognizes the keys of agents (the
	 * agents themself)
	 * 
	 * @param agent
	 *            the to which this drop zone belongs
	 */
	public DropZone(Agent agent, Point2D coordinates) {
		super(coordinates);
		setAgent(agent);
	}

	@Override
	protected boolean handleInternal(AbstractAction action, Agent agent) {
		// don't care
		return false;
	}

	@Override
	public String getHumanReadableValue() {
		return agentKeys.toString();
	}

	/**
	 * Put the key of an agent in the drop zone
	 * 
	 * @param agent
	 *            the agent
	 */
	public void putKey(Agent agent) {
		if (!agentKeys.contains(agent)) {
			agentKeys.add(agent);
			createAndSendMessage();
		}
		CASi.SIM_LOG.info(String.format("%s put his key to %s", agent.toString(), this.toString()));
	}

	/**
	 * Remove the provided agents key from the drop zone
	 * 
	 * @param agent
	 *            the agent
	 */
	public void removeKey(Agent agent) {
		if (agentKeys.contains(agent)) {
			agentKeys.remove(agent);
			createAndSendMessage();
		}
		CASi.SIM_LOG.info(String.format("%s removed his key from %s", agent.toString(), this.toString()));
	}

	/**
	 * Checks whether a provided agent's key lays in this dropzone
	 * 
	 * @param agent
	 *            the agent
	 * @return {@code true} if the key lays in a dropzone, {@code false}
	 *         otherwise.
	 */
	public boolean contains(Agent agent) {
		return agentKeys.contains(agent);
	}

	/**
	 * Creates a message and sends it to the {@link ICommunicationHandler}
	 */
	private void createAndSendMessage() {
		HashMap<String, String> values = new HashMap<String, String>();
		int i = 1;
		for (Agent a : agentKeys) {
			values.put("user" + i, a.toString());
			i++;
		}
		lastValue = agentKeys;
		String message = MACKProtocolFactory.generatePushMessage(agent,
				"dropzone", values);
		SimulationEngine.getInstance().getCommunicationHandler()
				.send(this, message);
	}

}
