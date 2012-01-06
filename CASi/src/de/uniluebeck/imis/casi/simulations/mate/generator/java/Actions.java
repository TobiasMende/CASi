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
package de.uniluebeck.imis.casi.simulations.mate.generator.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.uniluebeck.imis.casi.generator.ActionCollector;
import de.uniluebeck.imis.casi.generator.AgentCollector;
import de.uniluebeck.imis.casi.generator.ComponentCollector;
import de.uniluebeck.imis.casi.generator.RoomCollector;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction.TYPE;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;
import de.uniluebeck.imis.casi.simulation.model.actions.GoAndSpeakTo;
import de.uniluebeck.imis.casi.simulation.model.actions.Move;
import de.uniluebeck.imis.casi.simulation.model.mackActions.WorkOnDesktop;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Desktop;

/**
 * Action generator file with static methods that generate all the actions for
 * the MATe simulation environment World.
 * 
 * Put all your actions in here!
 * 
 * @author Marvin Frick
 * 
 */
public final class Actions {

	/**
	 * Fills the ActionGenerator singleton object with all the actions.
	 * 
	 * Put all your actions here!
	 */
	public static void generateActions(SimulationTime simulationStartTime) {
		ActionCollector ag = ActionCollector.getInstance();
		ComponentCollector cc = ComponentCollector.getInstance();
		
		
		for (Agent a : AgentCollector.getInstance().getAll()) {
			AbstractComponent ac;
			String ident = "Desktop-"+a.getIdentifier();
			
			ac = cc.findComponentByIdentifier(ident);
			Desktop.Program randomProgramm = Desktop.Program.values()[(int)(Math.random()*Desktop.Program.values().length)];
			Desktop.Frequency randomFrequency = Desktop.Frequency.values()[(int)(Math.random()*Desktop.Frequency.values().length)];
			AbstractAction workOnDesktop = new WorkOnDesktop((Desktop) ac, randomProgramm, randomFrequency, 60);
			
			// the actionCollector key has to start with the Agents identifier!
			// Otherwise is would not be added to his todoList automatically!
			ag.newAction(a.getIdentifier()+"action_"+ident+"_1",workOnDesktop);
		}

	}

	/**
	 * Generate all
	 * 
	 * @param startTime
	 */
	public static void generateActionsPools(SimulationTime startTime) {
		// TODO FILL ME!

	}

	/**
	 * Adds a given action to every already created agent. Clones the action so
	 * that everyone has its own.
	 * 
	 * @param action
	 *            the action that everyone should do
	 */
	private static void forAllAgentsDoThis(AbstractAction action) {
		for (Agent a : AgentCollector.getInstance().getAll()) {
			AbstractAction clonedAction = action.clone();
			a.addActionToList(clonedAction);
		}
	}
}
