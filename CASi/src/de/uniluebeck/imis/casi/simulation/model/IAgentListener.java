/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende
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

import java.awt.geom.Point2D;

import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

/**
 * Implement this interface to listen to agents
 * 
 * @author Tobias Mende
 * 
 */
public interface IAgentListener {
	/**
	 * Informs the listener about a state change of the agent
	 * 
	 * @param newState
	 *            the new state
	 * @param agent
	 *            the agent that changes the state
	 */
	public void stateChanged(Agent.STATE newState, Agent agent);

	/**
	 * Informs listeners about a change of the current interruptibility
	 * 
	 * @param interruptibility
	 *            the new state
	 * @param agent
	 *            the agent
	 */
	public void interruptibilityChanged(
			AbstractComponent.INTERRUPTIBILITY interruptibility, Agent agent);

	/**
	 * Informs listeners about a new position of the agent
	 * 
	 * @param oldPosition
	 *            the position before change
	 * @param newPosition
	 *            the position after change
	 * @param agent
	 *            the agent who changes position
	 */
	public void positionChanged(Point2D oldPosition, Point2D newPosition,
			Agent agent);

	/**
	 * Informs listeners if the agent starts performing a new action
	 * 
	 * @param action
	 *            the action that will be performed
	 * @param agent
	 *            the agent that wants to perform the action
	 */
	public void startPerformingAction(AbstractAction action, Agent agent);

	/**
	 * Informs listeners about a completed or interuppted action
	 * 
	 * @param action
	 *            the action
	 * @param agent
	 *            the agent that finished performing the action
	 */
	public void finishPerformingAction(AbstractAction action, Agent agent);

}
