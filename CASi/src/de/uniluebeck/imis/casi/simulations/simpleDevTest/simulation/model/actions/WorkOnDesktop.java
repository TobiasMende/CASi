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
package de.uniluebeck.imis.casi.simulations.simpleDevTest.simulation.model.actions;

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;
import de.uniluebeck.imis.casi.simulations.simpleDevTest.simulation.model.Desktop;
import de.uniluebeck.imis.casi.simulations.simpleDevTest.simulation.model.Desktop.Frequency;
import de.uniluebeck.imis.casi.simulations.simpleDevTest.simulation.model.Desktop.Program;

/**
 * @author Tobias Mende
 *
 */
public class WorkOnDesktop extends AtomicAction {

	private static final long serialVersionUID = 551293372023968564L;
	private Desktop desktop;
	private Desktop.Program program;
	private Desktop.Frequency frequency;
	
	public WorkOnDesktop(Desktop desktop, Desktop.Program program, Desktop.Frequency frequency, int duration) {
		this.desktop = desktop;
		this.frequency = frequency;
		this.program = program;
		setDuration(duration);
	}

	@Override
	protected boolean preActionTask(AbstractComponent performer) {
		// Check position and set Freq and Prog at Desktop.
		desktop.work(program, frequency);
		return super.preActionTask(performer);
	}
	
	@Override
	protected boolean internalPerform(AbstractComponent performer) {
		// wait only until the time elapsed
		return false;
	}
	
	@Override
	protected void postActionTask(AbstractComponent performer) {
		desktop.work(Program.unknown, Frequency.inactive);
		super.postActionTask(performer);
	}

}
