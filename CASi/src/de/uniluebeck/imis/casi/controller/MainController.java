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
package de.uniluebeck.imis.casi.controller;

import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.communication.ICommunicationHandler;
import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.World;
import de.uniluebeck.imis.casi.ui.IMainView;

/**
 * The MainController connects the different interfaces and handles interaction
 * with the simulation.
 * 
 * @author Tobias Mende
 * 
 */
public class MainController {
	/** The development logger */
	private static final Logger log = Logger.getLogger(MainController.class
			.getName());
	/** Holds the main view to deal with */
	private final IMainView mainView;

	/**
	 * The constructor for the main controller
	 * 
	 * @param generator
	 *            the generator that should generate the world
	 * @param communicationHandler
	 *            the handler that handles the communication
	 * @param mainView
	 *            the view that presents the simulation to the user
	 */
	public MainController(IWorldGenerator generator,
			ICommunicationHandler communicationHandler, IMainView mainView) {
		this.mainView = mainView;
		World world = generator.generateWorld();
		try {
			if (!world.isSealed()) {
				world.seal();
			}
			SimulationEngine.getInstance().setWorld(world);
			SimulationEngine.getInstance().setCommunicationHandler(
					communicationHandler);
			// Initialize the engine
			
		} catch (IllegalAccessException e) {
			CASi.SIM_LOG
					.severe("Can't set components after starting the simulation: "
							+ e.fillInStackTrace());
		}
		CASi.SIM_LOG.fine("Setup finished. Everything is fine");
	}

	/**
	 * Must be called to final start the simulation
	 */
	public void start() {
		CASi.SIM_LOG.info("Starting the simulation ...");
		SimulationEngine.getInstance().start();
		log.info("Started successfull");
	}

	/**
	 * Initialize the simulation and show the ui
	 */
	public void init() {
		SimulationEngine.getInstance().init();
		
				mainView.showUi();
		
	}
}
