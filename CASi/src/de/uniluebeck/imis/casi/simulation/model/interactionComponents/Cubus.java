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
package de.uniluebeck.imis.casi.simulation.model.interactionComponents;

import java.awt.Point;

import de.uniluebeck.imis.casi.simulation.model.AbstractActuator;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Agent.STATE;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

/**
 * This is an implementation of a actuator/ sensor component that represents the MATe Cubus
 * @author Tobias Mende
 *
 */
public class Cubus extends AbstractActuator {

	/**
	 * serialization id
	 */
	private static final long serialVersionUID = -3061958723193321546L;

	/**
	 * @param coordinates the position of this cubus
	 */
	public Cubus(Point coordinates) {
		super(coordinates);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean handleInternal(AbstractAction action, Agent agent) {
		if(!checkInterest(action, agent)) {
			return true;
		}
		//TODO
		return false;
	}

	@Override
	public String getHumanReadableValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receive(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChanged(STATE newState, Agent agent) {
		if(!checkInterest(agent)) {
			return;
		}
		//TODO
	}

	@Override
	public void interruptibilityChanged(INTERRUPTIBILITY interruptibility,
			Agent agent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startPerformingAction(AbstractAction action, Agent agent) {
		if(!checkInterest(action, agent)) {
			return;
		}
		//TODO

	}

	@Override
	public void finishPerformingAction(AbstractAction action, Agent agent) {
		if(!checkInterest(action, agent)) {
			return;
		}
		//TODO
	}
	
	protected boolean checkInterest(AbstractAction action, Agent agent) {
		if(!checkInterest(agent)) {
			return false;
		}
		//TODO
		return true;
	}
	
	protected boolean checkInterest(Agent agent) {
		if(!agent.equals(this.agent)) {
			agent.removeVetoableListener(this);
			return false;
		}
		if(!this.contains(agent)) {
			return false;
		}
		return true;
	}
}
