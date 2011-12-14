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

import java.awt.geom.Point2D;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;
import de.uniluebeck.imis.casi.simulation.model.actions.Move;
import de.uniluebeck.imis.casi.simulation.model.mateComponents.Cube.State;

/**
 * This is an implementation of the MATe DoorLight which is an actuator.
 * 
 * @author Tobias Mende
 * 
 */
public class DoorLight extends AbstractInteractionComponent {
	private static final long serialVersionUID = 8568457332564604488L;
	/** the development logger */
	private static final Logger log = Logger.getLogger(DoorLight.class
			.getName());

	/**
	 * Possible states for door lights
	 */
	public enum State {
		/** The agents in this room are interruptible */
		interruptible,
		/** The system doesn't know the state */
		maybeInterruptible,
		/** The agents in the room arn't interruptible */
		uninterruptible;
	}

	/** The current state of this door light */
	private State currentState = State.maybeInterruptible;
	/** The agent which state is represented by this door light */
	private Agent agent;
	/** The door to which this actuator is attached  */
	private Door door;
	/** The room to which the access is restricted by this light */
	private Room room;
	
	/** The message which is send as pull request */
	private String pullMessage;

	/**
	 * Creates a new DoorLight which is connected to the provided door. The area
	 * affected by this actuator is automatically set to the shape of the door.
	 * 
	 * @param door
	 *            the door
	 * @param room
	 *            the room to which access should be restricted by this sensor
	 * @param agent
	 *            the agent whos room it is.
	 */
	public DoorLight(Door door, Room room, Agent agent) {
		super("DoorLight-" + door.getIntIdentifier(), door.getCentralPoint());
		setShapeRepresentation(door.getShapeRepresentation());
		type = Type.ACTUATOR;
		this.door = door;
		this.room = room;
		this.agent = agent;
		pullMessage = createPullMessage(agent);
	}

	@Override
	public void receive(Object message) {
		if (!(message instanceof String)) {
			log.severe("Unknown message format. Can't receive information");
			return;
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
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
									"doorstate")) {
						int doorState = -1;
						try {
							doorState = Integer.parseInt(node.getNodeValue());
						} catch (NumberFormatException e) {
							log.warning("Exception whil parsing door state");
							setCurrentState(State.maybeInterruptible);
						}
							switch (doorState) {
							case 0:
								setCurrentState(State.uninterruptible);
								break;
							case 1:
								setCurrentState(State.interruptible);
							default:
								setCurrentState(State.maybeInterruptible);
								break;
							}
					}
				}
			}
		} catch (Exception e) {
			log.severe("Can't parse XML: " + e.fillInStackTrace());
		}
	}
	
	/**
	 * Sets the current state which is represented by this door light
	 * @param currentState the currentState to set
	 */
	private void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	@Override
	protected boolean handleInternal(AbstractAction action, Agent agent) {
		if (!checkInterest(action, agent)) {
			return true;
		}
		Move move = getMoveAction(action);
		if (action == null) {
			return true;
		}
		boolean allowAccess = true;
		Iterator<Point2D> iter = move.getPathIterator();
		if (iter == null) {
			log.severe("No path iterator. Wrong??? Allowing acces now.");
			return true;
		}
		switch (currentState) {
		case interruptible:
			allowAccess = true;
			break;
		case maybeInterruptible:
			allowAccess = handleYellow(action, agent, iter);
			break;
		case uninterruptible:
			allowAccess = handleRed(action, agent, iter);
			break;
		}

		return allowAccess;
	}

	/**
	 * Handles the case in which the door light doesn't know the state. In this
	 * case. Agent that want access with a low priority (lower than 3) arn't
	 * allowed. For others, access is allowed and prohibited randomly.
	 * 
	 * @param action
	 *            the action
	 * @param agent
	 *            the agent
	 * @param iter
	 *            the path iterator
	 * @return <code>true</code> if access is allowed, <code>false</code>
	 *         otherwise.
	 */
	private boolean handleYellow(AbstractAction action, Agent agent,
			Iterator<Point2D> iter) {
		if (!agentWantsInRoom(iter)) {
			return true;
		}
		if (action.getPriority() < 3) {
			return false;
		}
		Random random = new Random(System.currentTimeMillis());
		double value = random.nextDouble() + 0.25;
		return value >= 0.5;
	}

	/**
	 * Handles the case that the door light shows red and the move goes in the
	 * room. Lets the agent in the room if the actions priority is higher than
	 * 8.
	 * 
	 * @param action
	 *            the action
	 * @param agent
	 *            the performer
	 * @param iter
	 *            the path iterator
	 * @return <code>true</code> if access is allowed, <code>false</code>
	 *         otherwise.
	 */
	private boolean handleRed(AbstractAction action, Agent agent,
			Iterator<Point2D> iter) {
		return action.getPriority() > 8 || !agentWantsInRoom(iter);
	}

	/**
	 * Checks whether the move goes into the room
	 * 
	 * @param iter
	 *            the path iterator
	 * @return <code>true</code> if the agent tries to go in the room,
	 *         <code>false</code> otherwise.
	 */
	private boolean agentWantsInRoom(Iterator<Point2D> iter) {
		return iter.hasNext() && room.contains(iter.next());
	}

	/**
	 * Gets the move out of this action ore <code>null</code> if no {@link Move}
	 * action was found.
	 * 
	 * @param action
	 *            the action to extract the move from
	 * @return the move out of this action ore <code>null</code> if no
	 *         {@link Move} action was found
	 */
	private Move getMoveAction(AbstractAction action) {
		if (action instanceof Move) {
			return (Move) action;
		}
		if (action instanceof ComplexAction) {
			if (((ComplexAction) action).getCurrentAction() instanceof Move) {
				return (Move) ((ComplexAction) action).getCurrentAction();
			}
		}
		return null;
	}

	@Override
	protected boolean checkInterest(AbstractAction action, Agent agent) {
		if (!checkInterest(agent)) {
			return false;
		}
		if (action instanceof ComplexAction) {
			return (((ComplexAction) action).getCurrentAction() instanceof Move);
		}
		return action instanceof Move;
	}

	@Override
	protected boolean checkInterest(Agent agent) {
		if (agent == this.agent) {
			agent.removeVetoableListener(this);
			return false;
		}
		return true;
	}

	@Override
	public String getHumanReadableValue() {
		return currentState.toString();
	}

	@Override
	protected void makePullRequest(SimulationTime newTime) {
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
		buffer.append("\t<subject>doorlight</subject>\n");
		buffer.append("\t<request type=\"data\" object=\"" + agent + "\">\n");
		buffer.append("\t\t<entity name=\"interruptibility\"></entity>\n");
//		buffer.append("\t\t<entity name=\"activity\"></entity>\n");
		buffer.append("\t</request>\n");
		buffer.append("</message>");
		return buffer.toString();
	}

}
