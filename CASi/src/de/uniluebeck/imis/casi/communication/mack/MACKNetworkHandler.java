package de.uniluebeck.imis.casi.communication.mack;

import de.uniluebeck.imis.casi.communication.ICommunicationComponent;
import de.uniluebeck.imis.casi.communication.ICommunicationHandler;

public final class MACKNetworkHandler implements ICommunicationHandler {

	private static MACKNetworkHandler instance;
	private String xmppID;

	private MACKNetworkHandler() {

	}


	private String getXmppID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Setter method for xmmpdID. This need to be set before the first usage of this Object!
	 * @param xmmpID
	 */
	public void setXmppID(String xmmpID) {
		this.xmppID = xmmpID;
	}

	@Override
	public synchronized boolean send(ICommunicationComponent sender, Object message) {
		checkIfConnected();
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized void register(ICommunicationComponent comp) {
		// TODO Auto-generated method stub

	}

	private static void checkIfConnected() throws java.nio.channels.NotYetConnectedException {
		if (instance.getXmppID() == null) {
			throw new java.nio.channels.NotYetConnectedException();
		}	
	}
}
