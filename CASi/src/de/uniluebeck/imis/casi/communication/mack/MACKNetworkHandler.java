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
package de.uniluebeck.imis.casi.communication.mack;

import java.util.HashMap;
import java.util.Map;

import de.uniluebeck.imis.casi.communication.ICommunicationComponent;
import de.uniluebeck.imis.casi.communication.ICommunicationHandler;

/**
 * The MACKNetworkHandler handles the communication with the MACK Framework (the
 * jabber server)
 * 
 * @author Tobias Mende
 * 
 */
public final class MACKNetworkHandler implements ICommunicationHandler {

	/**
	 * the component map holds all components with their identifier which should
	 * also be used in the MACK framework TODO: if not so: change getIdentifier
	 * of the components to work as expected.
	 */
	private Map<String, ICommunicationComponent> components = new HashMap<String, ICommunicationComponent>();

	public MACKNetworkHandler() {
		// TODO implement
	}

	@Override
	public synchronized boolean send(ICommunicationComponent sender,
			Object message) {
		// TODO send the message with the correct identifier to the mack framework
		return false;
	}

	@Override
	public synchronized void register(ICommunicationComponent comp) {
		components.put(comp.getIdentifier(), comp);
		// TODO: Handle further connections here?
	}

	/*
	 * TODO: * Connect to Server * Create a map of jabber identifiers and
	 * component identifiers * Send messages with the correct jabber identifiers
	 * * Receive and forward messages to the correct components (maybe it is
	 * better to extract MACKInformation here than in the components)
	 */
}
