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
 * The Agent stays at his current position and does _nothing_ for the given
 * time.
 * 
 * @author Marvin Frick
 */
public class StayHere extends AtomicAction {

	/**
	 * serialization identifier
	 */
	private static final long serialVersionUID = 8981370022973052710L;

	/**
	 * Creates a new boring StayHere Action.
	 * 
	 * @param duration
	 *            how long should the agent do nothing
	 * @param prio
	 *            at what priority should the agent to nothing
	 */
	public StayHere(int duration, int prio) {
		this.setPriority(prio);
		this.setDuration(duration);
	}

	@Override
	protected boolean internalPerform(AbstractComponent performer) {
		// nothing to do here
		return false;
	}

	@Override
	public String getInformationDescription() {
		return "stay here and do nothing";
	}

}
