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
import java.awt.geom.Point2D;
import java.util.HashMap;

import de.uniluebeck.imis.casi.communication.mack.MACKProtocolFactory;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actions.SpeakTo;

/**
 * This class represents the MATe component "Mike" which is used to detect
 * currently speaking agents.
 * 
 * @author Tobias Mende
 * 
 */
public class Mike extends AbstractInteractionComponent {
	private static final long serialVersionUID = -2951196032563233461L;
	/** The last speakers that where detected by this component */
	private HashMap<String, String> values = new HashMap<String, String>();

	/**
	 * Constructor for a new mike which is positioned at the central point of
	 * the observed room
	 * 
	 * @param room
	 *            the room to observe
	 */
	public Mike(Room room) {
		this(room, room.getCentralPoint());
	}

	/**
	 * Constructor for a mike which observes a given room and is positioned at
	 * an explicit point.
	 * 
	 * @param room
	 * @param position
	 */
	public Mike(Room room, Point position) {
		this(room, (Point2D) position);
	}

	/**
	 * Private Constructor (just to prevent others from using point2d as
	 * position)
	 * 
	 * @param room
	 *            the room to observe
	 * @param position
	 *            the coordinates of this component.
	 */
	private Mike(Room room, Point2D position) {
		super(position);
		setShapeRepresentation(room.getShapeRepresentation());
		type = Type.SENSOR;
	}

	@Override
	protected boolean handleInternal(AbstractAction action, Agent agent) {
		if (!(action instanceof SpeakTo)) {
			// not interested in this action
			return true;
		}
		values = new HashMap<String, String>();
		values.put("speaker1", agent.toString());
		values.put("speaker2", ((SpeakTo) action).getAgent().toString());
		String message = MACKProtocolFactory.generatePushMessage(agent, "mike",
				values);
		SimulationEngine.getInstance().getCommunicationHandler()
				.send(this, message);
		return true;
	}

	@Override
	public String getHumanReadableValue() {
		StringBuffer buf = new StringBuffer();
		buf.append("(");
		for (String name : values.values()) {
			buf.append(name + ", ");
		}
		if (!values.isEmpty()) {
			buf.delete(buf.length() - 2, buf.length() - 1);	
		}
		buf.append(")");
		return buf.toString();
	}

}
