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

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Desktop;

/**
 * This action lets an agent work on a given desktop.
 * 
 * @author Tobias Mende
 * 
 */
public class WorkOnDesktop extends AtomicAction {
	/** serialization identifier */
	private static final long serialVersionUID = 551293372023968564L;
	/** the desktop to work with */
	private Desktop desktop;
	/** the programm class */
	private Desktop.Program program;
	/** the initial frequency */
	private Desktop.Frequency frequency;

	/**
	 * Constructor for new work action
	 * 
	 * @param desktop
	 *            the desktop (daa) to work with
	 * @param program
	 *            the programm class
	 * @param frequency
	 *            the initial frequency (may change during performing this
	 *            action)
	 * @param duration
	 *            the duration of this action
	 */
	public WorkOnDesktop(Desktop desktop, Desktop.Program program,
			Desktop.Frequency frequency, int duration) {
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
		desktop.finishPerformingAction(this, (Agent) performer);
	}

	@Override
	public String getInformationDescription() {
		return "use " + program + " with " + frequency;
	}

}
