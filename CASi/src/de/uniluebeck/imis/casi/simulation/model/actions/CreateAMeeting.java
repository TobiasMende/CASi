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
import java.util.Set;

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.IPosition;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;
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
public class CreateAMeeting extends AtomicAction {

	private Agent creator;
	private Set<Agent> set;
	private IPosition where;
	private SimulationTime wishedStartTime;
	private int expectedDuration;
	private int priotiy;

	/**
	 * Creates a CreateAMeeting Action which will, whenever this get performed,
	 * create a new HaveAMeeting Action for all attendees.
	 * 
	 * @param timeToCreateTheMeeting
	 *            the time (tbh. the earliestStartTime of this Action) at which
	 *            the Meeting will be announced (eg. created on the attending
	 *            Agents)
	 * @param creator
	 *            the meeting organizer
	 * @param set
	 *            a list of all attending Agents
	 * @param where
	 *            the IPosition (most likely a Room or an Agent) where this
	 *            meeting is about to take place
	 * @param whishedStartTime
	 *            the time at which this meeting should start
	 * @param expectedDuration
	 *            the duration of this meeting
	 * @param priotiy
	 *            at what priority this meeting should be added to the agents
	 */
	public CreateAMeeting(SimulationTime timeToCreateTheMeeting, Agent creator,
			Set<Agent> set, IPosition where, SimulationTime wishedStartTime,
			int expectedDuration, int priotiy) {
		super();
		this.setEarliestStartTime(timeToCreateTheMeeting);
		this.creator = creator;
		this.set = set;
		this.where = where;
		this.wishedStartTime = wishedStartTime;
		this.expectedDuration = expectedDuration;
		this.priority = priotiy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction
	 * #internalPerform
	 * (de.uniluebeck.imis.casi.simulation.model.AbstractComponent)
	 */
	@Override
	protected boolean internalPerform(AbstractComponent performer) {

		// makes sure the creator of the meeting is attending his meeting
		set.add(creator);

		// create a prototype of this Meeting
		AbstractAction meeting = new HaveAMeeting(where, wishedStartTime,
				expectedDuration, priotiy);

		// and set its clone to every agent
		for (Agent agent : set) {
			agent.addActionToList(meeting.clone());
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction
	 * #getInformationDescription()
	 */
	@Override
	public String getInformationDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}