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
package de.uniluebeck.imis.casi.simulations.mate.simulation.model.actions;

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;
import de.uniluebeck.imis.casi.simulation.model.actions.Move;
import de.uniluebeck.imis.casi.simulation.model.actions.StayHere;

/**
 * @author Marvin Frick
 *
 *
 */
public class HaveAMeeting extends ComplexAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5933636748690158732L;

	
	public HaveAMeeting(AbstractComponent where, int howLong, int prio){
		super();
		addSubAction(new Move(where));
		addSubAction(new StayHere(howLong, prio));
	}
	
	
}
