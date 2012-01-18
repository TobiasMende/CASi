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
package de.uniluebeck.imis.casi.simulation.model.mackComponents;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.communication.mack.MACKInformation;
import de.uniluebeck.imis.casi.communication.mack.MACKNetworkHandler;
import de.uniluebeck.imis.casi.communication.mack.MACKProtocolFactory;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Agent.STATE;
import de.uniluebeck.imis.casi.simulation.model.ConfigMap;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.ComplexAction;
import de.uniluebeck.imis.casi.simulation.model.mackActions.TurnCube;
import de.uniluebeck.imis.casi.simulation.model.mackActions.WorkOnDesktop;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Desktop.Frequency;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Desktop.Program;

/**
 * This is an implementation of a actuator/ sensor component that represents the
 * MATe Cube. This component is able to deal with the {@link MACKNetworkHandler}
 * 
 * @author Tobias Mende
 * 
 */
public class Cube extends AbstractInteractionComponent {
	/** the development logger */
	private static final Logger log = Logger.getLogger(Cube.class.getName());
	/**
	 * serialization id
	 */
	private static final long serialVersionUID = -3061958723193321546L;

	/** The states which can be represented by this cube */
	public enum State {
		break_long(0), break_short(1), unknown(2), reading(3), writing(4), meeting(
				5);
		/** int value for the state */
		private int intValue;

		private State(int intValue) {
			this.intValue = intValue;
		}

		/** Getter for the int value */
		public int intValue() {
			return intValue;
		}
	}

	/**
	 * The default probability for triggering a turn cube action if the current
	 * state seems not to be correct and the agents configMap doesn't contain a
	 * value for {@link ConfigMap#TURN_CUBE_PROBABILITY}
	 */
	public static final double DEFAULT_TURN_CUBE_PROBABILITY = 0.15;

	/** Counter for the cube instances */
	private static int idCounter;
	/** The current state of this cube */
	private State currentState;
	/** The message which is send as pull request */
	private String pullMessage;

	/**
	 * constructs a Cube.
	 * 
	 * @param coordinates
	 *            the position of this cube
	 */
	public Cube(Point2D coordinates, Agent owner) {
		super("cube-" + owner.getIdentifier() + "-" + idCounter++, coordinates);
		SimulationClock.getInstance().addListener(this);
		currentState = State.unknown;
		radius = 50;
		type = Type.MIXED;
		agent = owner;
		pullMessage = MACKProtocolFactory.generatePullRequest(owner, "cubus",
				"activity", "interruptibility");
	}

	@Override
	protected boolean handleInternal(AbstractAction action, Agent agent) {
		if (!checkInterest(action, agent)) {
			return true;
		}
		AbstractAction tempAction = action;
		if (action instanceof ComplexAction) {
			tempAction = ((ComplexAction) action).getCurrentAction();
		}
		if (tempAction instanceof WorkOnDesktop) {
			handleWorkOnDesktopAction((WorkOnDesktop) tempAction, agent);
		}

		return true;
	}

	/**
	 * Makes an educated guess about whether this cube should schedule a turn
	 * cube action for the provided agent or not.
	 * 
	 * This method uses the value for {@link ConfigMap#TURN_CUBE_PROBABILITY} of
	 * the agent, if it was set, else it uses the
	 * {@link Cube#DEFAULT_TURN_CUBE_PROBABILITY}.
	 * 
	 * @param agent
	 *            the agent
	 * @return the decission
	 */

	private boolean shouldScheduleTurnCubeAction(Agent agent) {
		// FIXME get probability from agent config
		double probability = DEFAULT_TURN_CUBE_PROBABILITY;
		Object configValue = agent
				.getConfiguration(ConfigMap.TURN_CUBE_PROBABILITY);
		if (configValue != null) {
			probability = (Double) configValue;
		}
		Random rand = new Random(System.currentTimeMillis());
		double value = rand.nextDouble();
		return value <= probability;
	}

	/**
	 * Tries to establish the correct state of this tube depending on the
	 * current work action of an agent. With a probability calculated by
	 * {@link Cube#shouldScheduleTurnCubeAction(Agent)}, this method interrupts
	 * the agent with a turn cube action if the state seems not to be correct.
	 * 
	 * @param action
	 *            the work on desktop action
	 * @param agent
	 *            the performer of this action
	 */
	private void handleWorkOnDesktopAction(WorkOnDesktop action, Agent agent) {

		if (!shouldScheduleTurnCubeAction(agent)) {
			// don't handle, not in probability range
			return;
		}
		Program program = action.getProgram();
		Frequency freq = action.getFrequency();
		TurnCube turn = null;
		ArrayList<State> possibleInactiveStates = new ArrayList<Cube.State>();
		possibleInactiveStates.add(State.reading);
		possibleInactiveStates.add(State.break_long);
		possibleInactiveStates.add(State.break_short);
		if (freq.equals(Frequency.inactive)) {
			if (!program.equals(Program.unknown)
					&& !possibleInactiveStates.contains(currentState)) {
				turn = new TurnCube(this,
						getRandomState(possibleInactiveStates));
			}
		} else if ((freq.equals(Frequency.active) || freq
				.equals(Frequency.very_active)) && program.equals(Program.text)) {
			if (!currentState.equals(State.writing)) {
				turn = new TurnCube(this, State.writing);
			}
		} else if (program.equals(Program.browser)) {
			possibleInactiveStates.remove(State.reading);
			if (freq.equals(Frequency.active)
					&& !possibleInactiveStates.contains(currentState)) {
				turn = new TurnCube(this,
						getRandomState(possibleInactiveStates));
			} else if (freq.equals(Frequency.very_active)
					&& !currentState.equals(State.writing)) {
				turn = new TurnCube(this, State.writing);
			}
		}

		if (turn != null) {
			CASi.SIM_LOG.info(this + ": I'm telling " + agent
					+ " to turn me to " + turn.getCubeState());
			CASi.SIM_LOG.fine(this + ": deciding to schedule " + turn
					+ " after looking at " + action);
			agent.interrupt(turn);
		}
	}

	/**
	 * Gets a random state from a provided list
	 * 
	 * @param states
	 *            the list of states
	 * @return the random selection
	 */
	private State getRandomState(List<State> states) {
		Random rand = new Random(System.currentTimeMillis());
		int index = rand.nextInt(states.size());
		return states.get(index);
	}

	/**
	 * Turns the cube to the provided state
	 * 
	 * @param state
	 *            the state to set
	 */
	public void turnCube(State state) {
		if (setCurrentState(state)) {
			HashMap<String, String> values = new HashMap<String, String>();
			values.put("activity", currentState.toString());
			String message = MACKProtocolFactory.generatePushMessage(agent,
					this.getType(), values);
			SimulationEngine.getInstance().getCommunicationHandler()
					.send(this, message);
		}
	}

	@Override
	public String getHumanReadableValue() {
		return "State: " + currentState.toString();
	}

	@Override
	public void receive(Object message) {
		if (!(message instanceof String)) {
			log.severe("Unknown message format. Can't receive information");
			return;
		}
		MACKInformation info = MACKProtocolFactory
				.parseMessage((String) message);
		if (info == null) {
			log.severe("Message was invalid");
			return;
		}
		CASi.SIM_LOG.fine(this + ": Receiving update!");
		String activity = info.getAccessibleEntities().get("activity");
		if (activity != null) {
			for (State s : State.values()) {
				if (s.toString().equalsIgnoreCase(activity)) {
					setCurrentState(s);
					break;
				}
			}
		} else {
			setCurrentState(State.unknown);
		}

	}

	/**
	 * Sets the state which is currently represented by this cube
	 * 
	 * @param currentState
	 *            the currentState to set
	 */
	private boolean setCurrentState(State currentState) {
		if (!currentState.equals(this.currentState)) {
			CASi.SIM_LOG.info(this + ": changing state from "
					+ this.currentState + " to " + currentState);
			this.currentState = currentState;
			return true;
		}
		return false;
	}

	@Override
	public void stateChanged(STATE newState, Agent agent) {
		if (!checkInterest(agent)) {
			return;
		}
		ArrayList<State> possibleStates = new ArrayList<Cube.State>();
		possibleStates.add(State.break_long);
		possibleStates.add(State.break_short);
		if (shouldScheduleTurnCubeAction(agent) && newState.equals(STATE.IDLE)
				&& !possibleStates.contains(currentState)) {
			TurnCube turn = new TurnCube(this, getRandomState(possibleStates));
			CASi.SIM_LOG.info(this + ": I'm telling " + agent
					+ " to turn me to " + turn.getCubeState());
			CASi.SIM_LOG.fine(this + ": deciding to schedule " + turn
					+ " after agents state changed to " + newState);
			agent.interrupt(turn);
		}
	}

	@Override
	public void interruptibilityChanged(INTERRUPTIBILITY interruptibility,
			Agent agent) {
		if (!checkInterest(agent)) {
			return;
		}
		ArrayList<State> possibleStates = new ArrayList<Cube.State>();
		possibleStates.add(State.break_long);
		possibleStates.add(State.break_short);
		if (shouldScheduleTurnCubeAction(agent)
				&& interruptibility.equals(INTERRUPTIBILITY.INTERRUPTIBLE)
				&& !possibleStates.contains(currentState)) {
			TurnCube turn = new TurnCube(this, getRandomState(possibleStates));
			CASi.SIM_LOG.info(this + ": I'm telling " + agent
					+ " to turn me to " + turn.getCubeState());
			CASi.SIM_LOG.fine(this + ": deciding to schedule " + turn
					+ " after agents interruptibility changed to "
					+ interruptibility);
			agent.interrupt(turn);
		}
	}

	@Override
	protected boolean checkInterest(AbstractAction action, Agent agent) {
		if (!checkInterest(agent)) {
			return false;
		}
		if (action instanceof WorkOnDesktop) {
			return true;
		}
		if (action instanceof ComplexAction
				&& ((ComplexAction) action).getCurrentAction() instanceof WorkOnDesktop) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean checkInterest(Agent agent) {
		return agent.equals(this.agent);
	}

	@Override
	public void sendRecurringMessage(SimulationTime newTime) {
		super.sendRecurringMessage(newTime);
		SimulationEngine.getInstance().getCommunicationHandler()
				.send(this, pullMessage);
	}

	@Override
	public String getType() {
		return "cubus";
	}

}