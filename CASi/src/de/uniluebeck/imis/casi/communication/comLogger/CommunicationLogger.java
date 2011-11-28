package de.uniluebeck.imis.casi.communication.comLogger;

import java.util.logging.Logger;

import de.uniluebeck.imis.casi.communication.ICommunicationComponent;
import de.uniluebeck.imis.casi.communication.ICommunicationHandler;

/**
 * The communication logger can be used instead of a network interface or
 * another communication handler for logging messages that are send over the
 * handler.
 * 
 * @author Tobias Mende
 * 
 */
public class CommunicationLogger implements ICommunicationHandler {
	private static final Logger log = Logger
			.getLogger(CommunicationLogger.class.getName());
	
	public CommunicationLogger() {
		log.fine("Hello World! I'm here for logging some network stuff");
	}

	@Override
	public boolean send(ICommunicationComponent sender, Object message) {
		log.info(sender + " has send a message: " + message);
		return true;
	}

	@Override
	public void register(ICommunicationComponent comp) {
		log.info("Registering Component: " + comp);
	}

}
