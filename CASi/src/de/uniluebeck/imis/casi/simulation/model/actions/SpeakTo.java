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
package de.uniluebeck.imis.casi.simulation.model.actions;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;

/**
 * This action can be set to speak with an agent.
 * @author Tobias Mende
 *
 */
public class SpeakTo extends AtomicAction {
	
	/** the serialization id */
	private static final long serialVersionUID = 1L;
	/** The agent to speak with */
	private Agent agent;
	/** The task that is set in the other agents interrupt list */
	private Follow task;
	/**
	 * Creates a speak action
	 * @param agent the agent to speak with
	 * @param duration the duration in minutes
	 */
	public SpeakTo(Agent agent, int duration) {
		super();
		setDuration(duration);
		this.agent = agent;
	}

	@Override
	protected boolean internalPerform(AbstractComponent performer) {
		return false;
	}

	@Override
	protected boolean preActionTask(AbstractComponent performer) {
		if(performer.getCurrentPosition().contains(agent)) {
			task = new Follow(performer);
			return agent.interrupt(task);
		} else {
			// Performer and agent are not at the same position
			CASi.SIM_LOG.severe("Can't speak to an agent at different position");
			return false;
		}
	}

	@Override
	protected void postActionTask(AbstractComponent performer) {
		task.complete();
	}

}
