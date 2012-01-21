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
package de.uniluebeck.imis.casi.simulations.simpleDevTest.generator.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.uniluebeck.imis.casi.generator.ActionCollector;
import de.uniluebeck.imis.casi.generator.AgentCollector;
import de.uniluebeck.imis.casi.generator.RoomCollector;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction.TYPE;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;
import de.uniluebeck.imis.casi.simulation.model.actions.GoAndSpeakTo;
import de.uniluebeck.imis.casi.simulation.model.actions.Move;

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

		ComplexAction runCrazy = new ComplexAction();
		runCrazy.addSubAction(new Move(RoomCollector.getInstance()
				.findRoomByIdentifier("crazyRoom")));
		runCrazy.addSubAction(new Move(RoomCollector.getInstance()
				.findRoomByIdentifier("theRandomRoom")));
		runCrazy.addSubAction(new Move(RoomCollector.getInstance()
				.findRoomByIdentifier("womensRestroom")));
		runCrazy.addSubAction(new Move(RoomCollector.getInstance()
				.findRoomByIdentifier("kitchen")));
		runCrazy.setEarliestStartTime(simulationStartTime.plus(15));
		for (Room r : RoomCollector.getInstance().getAll()) {
			runCrazy.addSubAction(new Move(r));
		}
		List<Room> temp = new ArrayList<Room>(RoomCollector.getInstance()
				.getAll());
		Collections.shuffle(temp);
		for (Room r : temp) {
			runCrazy.addSubAction(new Move(r));
		}
		ag.newAction("casi_crazy_guy_01", runCrazy);

		// ##########
		// And I Actions
		// ##########
		AbstractAction goHome = new Move(RoomCollector.getInstance()
				.findRoomByIdentifier("crazyRoom"));
		goHome.setEarliestStartTime(simulationStartTime.plus(42 + 23));
		ag.newAction(AgentCollector.getInstance().findAgentByName("And I")
				.getIdentifier()
				+ "_goHome", goHome);

		// ##########
		// For all tentLadys actions
		for (Agent a : AgentCollector.getInstance().findAgentByType("tentLady")) {
			generateActionsForTentLady(a, simulationStartTime);
		}

	}

	/**
	 * Generates the action the supplied tentLady is supposed to do
	 * 
	 * @param tentLady
	 * @param simulationStartTime
	 */
	public static void generateActionsForTentLady(Agent tentLady,
			SimulationTime simulationStartTime) {

		ActionCollector actions = ActionCollector.getInstance();
		Agent tim = AgentCollector.getInstance().findAgentByName("Tim");
		Room womensRoom = RoomCollector.getInstance().findRoomByIdentifier(
				"womensRestroom");

		AbstractAction tempAction = new Move(womensRoom);

		tempAction.setType(TYPE.NORMAL);
		tempAction.setPriority(5);
		tempAction.setEarliestStartTime(simulationStartTime
				.plus(1 + new Random().nextInt(10)));

		actions.newAction(tentLady.getIdentifier() + "_01", tempAction);

		AbstractAction speak = new GoAndSpeakTo(tim, 1);

		speak.setType(TYPE.NORMAL);
		speak.setEarliestStartTime(simulationStartTime.plus(20));

		actions.newAction(tentLady.getIdentifier() + "_02", speak);

		AbstractAction goBackToMainFloor = new Move(RoomCollector.getInstance()
				.findRoomByIdentifier("mainFloor"));
		goBackToMainFloor.setEarliestStartTime(speak.getEarliestStartTime()
				.plus(10));

		actions.newAction(tentLady.getIdentifier() + "_04", goBackToMainFloor);
	}

	/**
	 * Generate all
	 * 
	 * @param startTime
	 */
	public static void generateActionsPools(SimulationTime startTime) {

	}
}
