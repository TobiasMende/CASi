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

import de.uniluebeck.imis.casi.simulation.model.IPosition;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;

/**
 * Very simple but ComplexAction that lets an Agent move to a given position and
 * stays there for a given time.
 * 
 * @author Marvin Frick
 * 
 */
public class GoAndStayThere extends ComplexAction {

	/**
	 * Yep, its a serial ID for serialisation.
	 */
	private static final long serialVersionUID = -9177707412265777453L;

	/**
	 * Creates this simple action that lets the Agent move to pos and stay there
	 * for duration.
	 * 
	 * @param pos
	 *            where should the agent go
	 * @param duration
	 *            how long should the agent stay there
	 */
	public GoAndStayThere(IPosition pos, int duration) {
		addSubAction(new Move(pos));
		addSubAction(new StayHere(duration, 5));
	}

}
