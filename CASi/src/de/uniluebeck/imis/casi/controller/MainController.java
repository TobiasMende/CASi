package de.uniluebeck.imis.casi.controller;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.communication.ICommunicationHandler;
import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.World;
import de.uniluebeck.imis.casi.ui.IMainView;
/**
 * The MainController connects the different interfaces and handles interaction with the simulation.
 * @author Tobias Mende
 *
 */
public class MainController {
	/** Holds the main view to deal with */
	private final IMainView mainView;
	/**
	 * The constructor for the main controller
	 * @param generator the generator that should generate the world
	 * @param communicationHandler the handler that handles the communication
	 * @param mainView the view that presents the simulation to the user
	 */
	public MainController(IWorldGenerator generator, ICommunicationHandler communicationHandler, IMainView mainView) {
		this.mainView = mainView;
		World world = generator.generateWorld();
		if(!world.isSealed()) {
			world.seal();
		}
		try {
			SimulationEngine.getInstance().setWorld(world);
			SimulationEngine.getInstance().setCommunicationHandler(communicationHandler);
			
		} catch (IllegalAccessException e) {
			CASi.SIM_LOG.severe("Can't set components after starting the simulation: "+e.fillInStackTrace());
		}
		CASi.SIM_LOG.fine("Setup finished. Everything is fine");
	}
	
	/**
	 * Must be called to final start the simulation
	 */
	public void start() {
		CASi.SIM_LOG.info("Starting the Simulation ...");
		SimulationEngine.getInstance().start();
		mainView.showUi();
	}
}
