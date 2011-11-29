/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
 */
package de.uniluebeck.imis.casi;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.communication.ICommunicationHandler;
import de.uniluebeck.imis.casi.communication.comLogger.CommunicationLogger;
import de.uniluebeck.imis.casi.controller.MainController;
import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.generator.java.WorldGenerator;
import de.uniluebeck.imis.casi.generator.stub.GeneratorStub;
import de.uniluebeck.imis.casi.logging.DevLogFormatter;
import de.uniluebeck.imis.casi.logging.ExtendedConsoleHandler;
import de.uniluebeck.imis.casi.logging.SimLogFormatter;
import de.uniluebeck.imis.casi.ui.IMainView;
import de.uniluebeck.imis.casi.ui.simplegui.MainViewSimpleGui;

/**
 * CASi is the main class of the entire simulator. Modules like the
 * {@link ICommunicationHandler}, {@link IWorldGenerator} or the
 * {@link IMainView} can be exchanged here.
 * 
 * @author Tobias Mende
 * 
 */
public class CASi {
	/** The development logger */
	private static final Logger log = Logger
			.getLogger("de.uniluebeck.imis.casi");
	/**
	 * Default logger for logging simulation information. Should be used from
	 * the whole project
	 */
	public static final Logger SIM_LOG = Logger.getLogger("SimulationLoggger");
	/** flag for determining whether productive mode is on or off */
	public static final boolean PRODUCTIVE_MODE = false;
	/** Handlers for logging in file */
	private static FileHandler devFileHandler, simFileHandler;
	/** Handlers for logging on the console */
	private static ExtendedConsoleHandler devConsoleHandler, simConsoleHandler;

	/**
	 * The starting point for the entire program, changes can be made here to
	 * customize the simulator
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// DON'T REMOVE THESE LINES:
		setupLogging();

		// DO WHAT YOU WANT:
		log.info("Test!");
		IWorldGenerator generator = new GeneratorStub();
		ICommunicationHandler networkLogger = new CommunicationLogger();
		IMainView mainView = new MainViewSimpleGui();

		// Call the main controller and let it work:
		MainController mc = new MainController(generator, networkLogger,
				mainView);
		mc.start();
	}

	/**
	 * Sets up the logging
	 */
	public static void setupLogging() {
		// setup logging
		log.setUseParentHandlers(false);
		SIM_LOG.setUseParentHandlers(false);
		log.setLevel(Level.ALL);
		SIM_LOG.setLevel(Level.INFO);
		configureDevelopmentLogging();
		log.addHandler(devConsoleHandler);

		// configure the lowest levels for different loggers
		if (PRODUCTIVE_MODE) {
			// Create the sim log only in productive mode
			configureSimulationLogging();
			SIM_LOG.addHandler(simConsoleHandler);
			if (simFileHandler != null) {
				SIM_LOG.addHandler(simFileHandler);
			}
		} else {
			// use the dev console for sim logging only in dev mode
			SIM_LOG.addHandler(devConsoleHandler);
		}
		// write simulation information to the dev log in every case
		if (devFileHandler != null) {
			log.addHandler(devFileHandler);
			SIM_LOG.addHandler(devFileHandler);
		}

	}

	/**
	 * Configuring the development logging
	 * 
	 * @throws SecurityException
	 *             if no write permissions are given for the log directory
	 * @throws IOException
	 *             if an error occurred while writing the log file
	 */
	private static void configureDevelopmentLogging() {
		// Configure console logger
		devConsoleHandler = new ExtendedConsoleHandler();
		devConsoleHandler.setFormatter(new DevLogFormatter());

		// Configure file logger
		long time = Calendar.getInstance().getTimeInMillis();
		try {
			devFileHandler = new FileHandler(String.format("log/%d.log", time));
			devFileHandler.setFormatter(new DevLogFormatter()); // Use
																// HTMLFormatter
			// for fancy output

			if (PRODUCTIVE_MODE) {
				// define the behavior of the development handler in productive
				// mode
				// log everything important into the dev log file
				devFileHandler.setLevel(Level.CONFIG);
			} else {
				// log everything to the log file
				devFileHandler.setLevel(Level.ALL);
			}
		} catch (Exception e) {
			log.info("Es wird keine Protokolldatei erzeugt: " + e.getMessage());
		}

		if (PRODUCTIVE_MODE) {
			// define the behavior of the development handler in productive mode
			devConsoleHandler.setLevel(Level.SEVERE); // show important errors
														// on the console
		} else {
			// log more information on the console
			devConsoleHandler.setLevel(Level.INFO);
		}
	}

	/**
	 * Configuring the simulation logging
	 * 
	 * @throws SecurityException
	 *             if no write permissions are given for the log directory
	 * @throws IOException
	 *             if an error occurred while writing the log file
	 */
	private static void configureSimulationLogging() {
		// Configure console logger
		simConsoleHandler = new ExtendedConsoleHandler();
		simConsoleHandler.setFormatter(new SimLogFormatter());

		// Configure file logger
		long time = Calendar.getInstance().getTimeInMillis();

		try {
			simFileHandler = new FileHandler(String.format("log/sim-%d.log",
					time));
			simFileHandler.setFormatter(new SimLogFormatter()); // Use
																// HTMLFormatter
			simFileHandler.setLevel(Level.ALL);
		} catch (Exception e) {
			log.info("Es wird keine Protokolldatei erzeugt: " + e.getMessage());
		}
		// for fancy output
		simConsoleHandler.setLevel(Level.INFO);

	}
}
