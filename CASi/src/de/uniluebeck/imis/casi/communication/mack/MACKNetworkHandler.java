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

import de.uniluebeck.imis.casi.communication.ICommunicationComponent;
import de.uniluebeck.imis.casi.communication.ICommunicationHandler;

public final class MACKNetworkHandler implements ICommunicationHandler {

	private static MACKNetworkHandler instance;
	private String xmppID;

	public MACKNetworkHandler() {
		// TODO implement
	}

	private String getXmppID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Setter method for xmmpdID. This need to be set before the first usage of
	 * this Object!
	 * 
	 * @param xmmpID
	 */
	public void setXmppID(String xmmpID) {
		this.xmppID = xmmpID;
	}

	@Override
	public synchronized boolean send(ICommunicationComponent sender,
			Object message) {
		checkIfConnected();
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized void register(ICommunicationComponent comp) {
		// TODO Auto-generated method stub

	}

	private static void checkIfConnected()
			throws java.nio.channels.NotYetConnectedException {
		if (instance.getXmppID() == null) {
			throw new java.nio.channels.NotYetConnectedException();
		}
	}
}
