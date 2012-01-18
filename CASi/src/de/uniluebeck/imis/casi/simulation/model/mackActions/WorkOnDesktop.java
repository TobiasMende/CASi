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

import java.util.Random;

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Desktop;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Desktop.Frequency;

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
	
	private int iterationCounter;

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
		// Check position
		if (!desktop.contains(performer)) {
			// cant work on Desktop when not at Desktop!
			return false;
		} else {
			// set Freq and Prog at Desktop.
			desktop.work(program, frequency);
			return super.preActionTask(performer);
		}
	}

	@Override
	protected boolean internalPerform(AbstractComponent performer) {
		// wait only until the time elapsed
		if((iterationCounter++)%10 == 0) {
			Frequency newFrequency = generateRandomFrequency(frequency);
			if(!newFrequency.equals(frequency)) {
				desktop.work(program, newFrequency);
				frequency = newFrequency;
			}
		}
		return false;
	}
	
	/**
	 * Generates a random new frequency depending on a given frequency. The
	 * current frequency is more possible than others.
	 * 
	 * @param lastFrequency
	 *            the last frequency
	 * @return the new frequency
	 */
	private Frequency generateRandomFrequency(Frequency lastFrequency) {
		Frequency[] possibilities = new Frequency[4];
		switch (lastFrequency) {
		case very_active:
			// Very active is more possible than active
			possibilities[0] = Frequency.very_active;
			possibilities[1] = Frequency.very_active;
			possibilities[2] = Frequency.very_active;
			possibilities[3] = Frequency.active;
			break;
		case inactive:
			possibilities[0] = Frequency.inactive;
			possibilities[1] = Frequency.inactive;
			possibilities[2] = Frequency.inactive;
			possibilities[3] = Frequency.active;
			break;
		default:
			// Everything is possible
			possibilities[0] = Frequency.active;
			possibilities[1] = Frequency.active;
			possibilities[2] = Frequency.very_active;
			possibilities[3] = Frequency.inactive;
			break;

		}

		Random rand = new Random(System.currentTimeMillis());
		int index = rand.nextInt(4);
		return possibilities[index];
	}

	@Override
	protected void postActionTask(AbstractComponent performer) {
		desktop.finishPerformingAction(this, (Agent) performer);
	}

	@Override
	public String getInformationDescription() {
		return "use " + program + " with " + frequency;
	}
	
	/**
	 * getter for the current program
	 * @return the program the program class which is in foreground
	 */
	public Desktop.Program getProgram() {
		return program;
	}
	
	/**
	 * Getter for the current frequency
	 * @return the frequency the current frequency
	 */
	public Desktop.Frequency getFrequency() {
		return frequency;
	}

}
