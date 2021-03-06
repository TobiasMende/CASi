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
package de.uniluebeck.imis.casi.simulation.model.mackComponents;

import java.awt.geom.Point2D;
import java.util.HashMap;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.communication.mack.MACKProtocolFactory;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

/**
 * This class represents the desktop activity analyzer which is a sensor that is
 * used in the MATe implementation to get information about the agents state.
 * 
 * @author Tobias Mende
 * 
 */
public class Desktop extends AbstractInteractionComponent {
	/** Program classes that can be in foreground on this desktop */
	public enum Program {
		text, browser, unknown;
	}

	/** The possible frequencies */
	public enum Frequency {
		active, inactive, very_active;
	}
	
	/** serialization identifier */
	private static final long serialVersionUID = 8750391465421352206L;

	/** counter for unique identifiers */
	private static int idCounter;

	/** The frequency with which the program is used */
	private Frequency currentFrequency = Frequency.inactive;
	/** The program currently in foreground */
	private Program currentProgram = Program.unknown;

	/**
	 * Constructor
	 * 
	 * @param coordinates
	 *            the exact position of this daa.
	 */
	public Desktop(Point2D coordinates, Agent agent) {
		super("Desktop-" + agent.getIdentifier() + "-" + idCounter++,
				coordinates);
		radius = 5;
		type = Type.SENSOR;
		this.agent = agent;
	}

	@Override
	protected boolean handleInternal(AbstractAction action, Agent agent) {
		return false;
	}

	@Override
	public String getHumanReadableValue() {
		return "P: " + currentProgram + ", F: " + currentFrequency;
	}

	/**
	 * Starts working with the provided program and frequency
	 * 
	 * @param program
	 *            the program to work with
	 * @param frequency
	 *            the initial frequency
	 */
	public void work(Program program, Frequency frequency) {
		CASi.SIM_LOG.info(String.format("%s now working %s with %s",this.toString(),frequency,program));
		this.currentFrequency = frequency;
		this.currentProgram = program;
		send();
	}
	
	
	@Override
	public void finishPerformingAction(AbstractAction action, Agent agent) {
		work(Program.unknown, Frequency.inactive);
	}

	/**
	 * Finally sends a message to the communication handler.
	 */
	private void send() {
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("frequency", currentFrequency.toString());
		values.put("program", currentProgram.toString());
		String message = MACKProtocolFactory.generatePushMessage(agent, "daa",
				values);
		SimulationEngine.getInstance().getCommunicationHandler()
				.send(this, message);
	}

	@Override
	public String getType() {
		return "daa";
	}


}
