/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende
 *
 *  This program is free software. It is licensed under the
 *  GNU Lesser General Public License with one clarification.
 *  
 *  You should have received a copy of the 
 *  GNU Lesser General Public License along with this program. 
 *  See the LICENSE.txt file in this projects root folder or visit
 *  <http://www.gnu.org/licenses/lgpl.html> for more details.
 */
package de.uniluebeck.imis.casi.simulation.engine;

import de.uniluebeck.imis.casi.communication.ICommunicationHandler;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.World;

/**
 * The engine is the central unit in the simulator that holds and provides the
 * world and other relevant data and information.
 * 
 * The engine is a singleton, so it can be called from everywhere and there
 * exists only one engine in each simulator.
 * 
 * @author Tobias Mende
 * 
 */
public class SimulationEngine implements ISimulationClockListener {
	/** The instance of this singleton */
	private static SimulationEngine instance;
	/**
	 * The handler which handles external communication (e.g. a network
	 * connection)
	 */
	private ICommunicationHandler communicationHandler;

	/**
	 * The world which holds all components and settings of the current
	 * simulation
	 */
	private World world;

	/**
	 * The private constructor of this singleton
	 */
	private SimulationEngine() {
		SimulationClock.getInstance().addListener(this);
		// just here for prohibiting external access
	}

	/**
	 * Getter for the instance of this singleton
	 * 
	 * @return the instance
	 */
	public static SimulationEngine getInstance() {
		if (instance == null) {
			instance = new SimulationEngine();
		}
		return instance;
	}

	/**
	 * Getter for the handler which handles the external communication
	 * 
	 * @return the communication handler
	 */
	public ICommunicationHandler getCommunicationHandler() {
		return communicationHandler;
	}

	/**
	 * Setter for the communication handler
	 * 
	 * @param communicationHandler
	 *            the handler
	 * @throws IllegalAccessException
	 *             if the simulation was started yet.
	 */
	public void setCommunicationHandler(
			ICommunicationHandler communicationHandler)
			throws IllegalAccessException {
		if (SimulationClock.getInstance().isStarted()) {
			throw new IllegalAccessException(
					"Can't change the communication handler after starting the simulation");
		}
		this.communicationHandler = communicationHandler;
	}

	/**
	 * Getter for the world
	 * 
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Setter for the world that is simulated by this engine
	 * 
	 * @param world
	 *            the wolrd
	 * @throws IllegalAccessException
	 *             if the simulation is started yet
	 */
	public void setWorld(World world) throws IllegalAccessException {
		if (SimulationClock.getInstance().isStarted()) {
			throw new IllegalAccessException(
					"Can't change the world after starting the simulation");
		}
		this.world = world;
	}

	/**
	 * Starts the engine and the simulation clock.
	 * 
	 * @throws IllegalStateException
	 *             if no connection handler or no world was set.
	 */
	public void start() throws IllegalStateException {
		if (SimulationClock.getInstance().isStarted())
			return;
		if (communicationHandler == null)
			throw new IllegalStateException("CommunicationHandler must be set");
		if (world == null)
			throw new IllegalStateException("World must be set");
		SimulationClock.getInstance().init(world.getStartTime());
		SimulationClock.getInstance().start();
	}

	// THINK needs the engine to be a clock listener?

	@Override
	public void timeChanged(SimulationTime newTime) {
		// nothing to do here at the moment
	}

	@Override
	public void simulationPaused(boolean pause) {
		// nothing to do here at the moment
	}

	@Override
	public void simulationStopped() {
		// nothing to do here at the moment
	}

	@Override
	public void simulationStarted() {
		// nothing to do here at the moment
	}
}
