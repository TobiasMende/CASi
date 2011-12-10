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
 * extended agent listeners are agent listeners that are informed more often than normal listeners. E.g. when the agent tries to perform an action.
 * {@link AbstractInteractionComponent} are {@link IExtendedAgentListener}s.
 * @author Tobias Mende
 * 
 */
public interface IExtendedAgentListener extends IAgentListener {
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

	/**
	 * Checks if this component has a veto right when an agent tries to perform
	 * an action. (Only actuators and mixed components have a veto right)
	 * 
	 * @return <code>true</code> if this component is allowed to interrupt an
	 *         action, <code>false</code> otherwise.
	 */
	public boolean hasVetoRight();
}
