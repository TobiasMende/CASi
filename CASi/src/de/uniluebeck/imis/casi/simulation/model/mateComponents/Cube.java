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
package de.uniluebeck.imis.casi.simulation.model.mateComponents;

import java.awt.Point;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import de.uniluebeck.imis.casi.communication.mack.MACKNetworkHandler;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Agent.STATE;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

/**
 * This is an implementation of a actuator/ sensor component that represents the
 * MATe Cube. This component is able to deal with the {@link MACKNetworkHandler}
 * 
 * @author Tobias Mende
 * 
 */
public class Cube extends AbstractInteractionComponent {
	/** the development logger */
	private static final Logger log = Logger.getLogger(Cube.class.getName());

	/** The states which can be represented by this cube */
	public enum State {
		break_long(0), break_short(1), unknown(2), reading(3), writing(4), meeting(
				5);
		/** int value for the state */
		private int intValue;

		private State(int intValue) {
			this.intValue = intValue;
		}

		/** Getter for the int value */
		public int intValue() {
			return intValue;
		}
	}

	/**
	 * serialization id
	 */
	private static final long serialVersionUID = -3061958723193321546L;
	/** The current state of this cube */
	private State currentState;
	/** The message which is send as pull request */
	private String pullMessage;

	/**
	 * @param coordinates
	 *            the position of this cube
	 */
	public Cube(Point coordinates, Agent owner) {
		super(coordinates);
		pullEnabled = true;
		SimulationClock.getInstance().addListener(this);
		currentState = State.unknown;
		type = Type.MIXED;
		agent = owner;
		pullMessage = createPullMessage(owner);
	}

	@Override
	protected boolean handleInternal(AbstractAction action, Agent agent) {
		if (!checkInterest(action, agent)) {
			return true;
		}
		// TODO handle here e.g. if an action doesn't fit to current state, let
		// agent correct the state
		return false;
	}

	/**
	 * Turns the cube to the provided state
	 * 
	 * @param state
	 *            the state to set
	 */
	public void turnCube(State state) {
		if (!state.equals(currentState)) {
			currentState = state;
			SimulationEngine.getInstance().getCommunicationHandler()
					.send(this, "New Cube state: " + state);
		}
	}

	@Override
	public String getHumanReadableValue() {
		return "State: " + currentState.toString();
	}

	@Override
	public void receive(Object message) {
		if (!(message instanceof String)) {
			log.severe("Unknown message format. Can't receive information");
			return;
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader inStream = new StringReader((String) message);
			InputSource is = new InputSource(inStream);
			Document doc = builder.parse(is);
			NodeList nodes = doc.getElementsByTagName("entity");
			inStream.close();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE
						&& node.hasAttributes()) {
					NamedNodeMap attributes = node.getAttributes();
					Node nameNode = attributes.getNamedItem("name");
					if (nameNode != null
							&& nameNode.getNodeValue().equalsIgnoreCase(
									"cubusstate")) {
						String value = node.getNodeValue();
						for (State s : State.values()) {
							if (value.equals(s.toString())) {
								setCurrentState(s);
								return;
							}
						}
					}
				}
			}

		} catch (Exception e) {
			log.severe("Can't parse XML: " + e.fillInStackTrace());
		}

	}
	
	/**
	 * Sets the state which is currently represented by this cube
	 * @param currentState the currentState to set
	 */
	private void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	@Override
	public void stateChanged(STATE newState, Agent agent) {
		if (!checkInterest(agent)) {
			return;
		}
		// TODO perhaps trigger TurnCube Action
	}

	@Override
	public void interruptibilityChanged(INTERRUPTIBILITY interruptibility,
			Agent agent) {
		if (!checkInterest(agent)) {
			return;
		}
		// TODO perhaps trigger TurnCube Action
	}

	@Override
	protected boolean checkInterest(AbstractAction action, Agent agent) {
		if (!checkInterest(agent)) {
			return false;
		}
		// nothing to do here
		return true;
	}

	@Override
	protected boolean checkInterest(Agent agent) {
		if (!agent.equals(this.agent)) {
			agent.removeVetoableListener(this);
			return false;
		}
		if (!this.contains(agent)) {
			return false;
		}
		return true;
	}

	@Override
	public void makePullRequest(SimulationTime newTime) {
		SimulationEngine.getInstance().getCommunicationHandler()
					.send(this, pullMessage);
	}

	/**
	 * Creates the message that is used for pull requests
	 * 
	 * @param agent
	 *            the agent who's state should be pulled
	 * @return the pull message
	 */
	private String createPullMessage(Agent agent) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<message type=\"status\">\n");
		buffer.append("\t<mode>pull</mode>\n");
		buffer.append("\t<subject>cubus</subject>\n");
		buffer.append("\t<request type=\"data\" object=\"" + agent + "\">\n");
		buffer.append("\t\t<entity name=\"interruptibility\"></entity>\n");
		buffer.append("\t\t<entity name=\"activity\"></entity>\n");
		buffer.append("\t</request>\n");
		buffer.append("</message>");
		return buffer.toString();
	}
	
	

}