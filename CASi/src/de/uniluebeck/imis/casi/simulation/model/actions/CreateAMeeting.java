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
package de.uniluebeck.imis.casi.simulation.model.actions;

import java.util.List;

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;

/**
 * @author Marvin Frick
 * 
 *         This action class shall be used whenever an Agent schedules a meeting
 *         which has an fixed time (both start time and duration) and multiple
 *         attendees. It will create HaveAMeeting Actions in the attending
 *         Agents, including the creator. So this one is a very short action
 *         most of the time. As it is complicated to find a time slot which fits
 *         all agents, this action has exchangeable scheduling methods.
 * 
 */
public class CreateAMeeting extends ComplexAction {

	/**
	 * 
	 * @param creator
	 *            the meeting organizer
	 * @param attendees
	 *            a list of all attending Agents
	 * @param whishedStartTime
	 * @param expectedDuration
	 */
	public CreateAMeeting(Agent creator, List<Agent> attendees,
			AbstractComponent where, SimulationTime whishedStartTime,
			int expectedDuration) {

		// for now, create the meeting immediately! No time to explain!
		attendees.add(creator);
		AbstractAction meeting = new HaveAMeeting(where, expectedDuration, 10);
		for (Agent agent : attendees) {
			agent.addActionToList(meeting);
		}
	}
}
