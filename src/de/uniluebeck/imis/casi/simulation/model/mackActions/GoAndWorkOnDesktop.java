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
package de.uniluebeck.imis.casi.simulation.model.mackActions;

import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;
import de.uniluebeck.imis.casi.simulation.model.actions.Move;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Desktop;

/**
 * This action lets an agent run to his desktop and work there for a given time
 * with given parameters
 * 
 * @author Marvin Frick
 * 
 */
public class GoAndWorkOnDesktop extends ComplexAction {

	/**
	 * the serialization id
	 */
	private static final long serialVersionUID = 3732350423378040656L;

	/**
	 * Creates a new workOnDesktop action after going to the agents desktop
	 * 
	 * @param desktop
	 *            the desktop to go to
	 * @param program
	 *            what program to work with
	 * @param frequency
	 *            what frequency to work at
	 * @param duration
	 *            the duration how long to work there
	 */
	public GoAndWorkOnDesktop(Desktop desktop, Desktop.Program program,
			Desktop.Frequency frequency, int duration) {
		super();
		addSubAction(new Move(desktop));
		addSubAction(new WorkOnDesktop(desktop, program, frequency, duration));

	}

}
