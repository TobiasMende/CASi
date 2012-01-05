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
package de.uniluebeck.imis.simulations.mate.generator.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.uniluebeck.imis.casi.generator.ActionGenerator;
import de.uniluebeck.imis.casi.generator.AgentGenerator;
import de.uniluebeck.imis.casi.generator.RoomGenerator;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction.TYPE;
import de.uniluebeck.imis.casi.simulation.model.actions.GoAndSpeakTo;
import de.uniluebeck.imis.casi.simulation.model.actions.Move;
import de.uniluebeck.imis.casi.simulation.model.actions.SpeakTo;

/**
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

		ComplexAction runCrazy = new ComplexAction();
		runCrazy.addSubAction(new Move(RoomGenerator.getInstance()
				.findRoomByIdentifier("crazyRoom")));
		runCrazy.addSubAction(new Move(RoomGenerator.getInstance()
				.findRoomByIdentifier("theRandomRoom")));
		runCrazy.addSubAction(new Move(RoomGenerator.getInstance()
				.findRoomByIdentifier("womensRestroom")));
		runCrazy.addSubAction(new Move(RoomGenerator.getInstance()
				.findRoomByIdentifier("kitchen")));
		runCrazy.setEarliestStartTime(simulationStartTime.plus(15));
		for (Room r : RoomGenerator.getInstance().getAll()) {
			runCrazy.addSubAction(new Move(r));
		}
		List<Room> temp = new ArrayList<Room>(RoomGenerator.getInstance()
				.getAll());
		Collections.shuffle(temp);
		for (Room r : temp) {
			runCrazy.addSubAction(new Move(r));
		}
		ActionGenerator.getInstance().newAction("casi_crazy_guy_01", runCrazy);

	}

	/**
	 * Generates the action the supplied tentLady is supposed to do
	 * 
	 * @param tentLady
	 * @param simulationStartTime
	 */
	public static void generateActionsForTentLady(Agent tentLady,
			SimulationTime simulationStartTime) {

		ActionGenerator actions = ActionGenerator.getInstance();
		Agent tim = AgentGenerator.getInstance().findAgentByName("Tim");
		Room timsRoom = RoomGenerator.getInstance().findRoomByIdentifier(
				"tim'sRoom");

		AbstractAction tempAction = new Move(timsRoom);
		AbstractAction move2 = tempAction.clone();
		tempAction.setType(TYPE.NORMAL);
		tempAction.setPriority(5);
		tempAction.setEarliestStartTime(simulationStartTime
				.plus(1 + new Random().nextInt(10)));
		
		actions.newAction(tentLady.getIdentifier()+"_01", tempAction);

		
		AbstractAction speak = new GoAndSpeakTo(tim, 10);

		speak.setType(TYPE.NORMAL);
		speak.setEarliestStartTime(simulationStartTime.plus(20));
		
		actions.newAction(tentLady.getIdentifier()+"_02", speak);
		
		ComplexAction speakInTimsRoom = new ComplexAction();
		speakInTimsRoom.addSubAction((AtomicAction) move2);
		speakInTimsRoom.addSubAction(new SpeakTo(tim, 10));
		speakInTimsRoom.setEarliestStartTime(simulationStartTime.plus(30));
		AbstractAction testMove = new Move(RoomGenerator.getInstance()
				.findRoomByIdentifier("mainFloor"));
		testMove.setEarliestStartTime(speakInTimsRoom.getEarliestStartTime()
				.plus(10));

		actions.newAction(tentLady.getIdentifier()+"_03", speakInTimsRoom);
		actions.newAction(tentLady.getIdentifier()+"_04", testMove);
	}

	/**
	 * Generate all
	 * @param startTime
	 */
	public static void generateActionsPools(SimulationTime startTime) {
		// TODO FILL ME!

	}
}
