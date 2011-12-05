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

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;

/**
 * This action can be set as blocker action for an agent that should follow a given agent.
 * 
 * @author Tobias Mende
 *
 */
public class Follow extends AtomicAction {
	private AbstractComponent obj;
	private boolean completed;

	/**
	 * the serialization id
	 */
	private static final long serialVersionUID = -7918438413251112680L;

	/**
	 * Creates a new follow action with a component that should be followed by the performer
	 * @param obj the component to follow.
	 */
	public Follow(AbstractComponent obj) {
		super();
		this.obj = obj;
	}
	
	@Override
	protected boolean internalPerform(AbstractComponent performer) {
		if(completed) {
			return true;
		}
		performer.setCoordinates(obj.getCoordinates());
		return false;
	}
	
	/**
	 * Completes this follow action.
	 */
	public void complete() {
		completed = true;
	}

}
