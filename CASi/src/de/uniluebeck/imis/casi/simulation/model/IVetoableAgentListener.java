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
package de.uniluebeck.imis.casi.simulation.model;

import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

/**
 * Vetoable agent listeners are agent listeners that have a veto right if an
 * agent tries to perform something.
 * 
 * @author Tobias Mende
 * 
 */
public interface IVetoableAgentListener extends IAgentListener {
	/**
	 * Informs the listener who should handle the provided action
	 * 
	 * @param action
	 *            the action to handle
	 * @param agent
	 *            the performer
	 * @return <code>true</code> if the agent is allowed to continue,
	 *         <code>false</code> otherwise.
	 */
	public boolean handle(AbstractAction action, Agent agent);

}
