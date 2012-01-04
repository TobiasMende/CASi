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

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;

/**
 * @author Marvin Frick
 * 
 *         The Angent stays at his current position and does _nothing_ for the given time.
 */
public class StayHere extends AtomicAction {

	/**
	 * Creates a new boring StayHere Action.
	 * @param duration how long should the agent do nothing
	 * @param prio at what priority should the agent to nothing
	 */
	public StayHere(int duration, int prio){
		this.setPriority(prio);
		this.setDuration(duration);
	}

	/* (non-Javadoc)
	 * @see de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction#internalPerform(de.uniluebeck.imis.casi.simulation.model.AbstractComponent)
	 */
	@Override
	protected boolean internalPerform(AbstractComponent performer) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
