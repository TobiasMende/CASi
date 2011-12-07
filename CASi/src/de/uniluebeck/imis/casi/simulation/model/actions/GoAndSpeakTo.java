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
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;

/**
 * This action could be used to go and speak to a given agent. It's just a simple example which has to be enhanced
 * @author Tobias Mende
 *
 */
public class GoAndSpeakTo extends ComplexAction {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new speak action for a meeting with the given agent
	 * @param agent the agent to speak with
	 * @param duration the duration
	 */
	public GoAndSpeakTo(Agent agent, int duration) {
		super();
		addSubAction(new Move(agent.getDefaultPosition()));
		addSubAction(new SpeakTo(agent, duration));
		
	}
	
	@Override
	protected void postActionTask(AbstractComponent performer) {
		CASi.SIM_LOG.info(performer +" completes go and speak to action!");
		super.postActionTask(performer);
	}

}
