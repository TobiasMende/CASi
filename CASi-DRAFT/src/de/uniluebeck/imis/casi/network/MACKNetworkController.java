package de.uniluebeck.imis.casi.network;


public final class MACKNetworkController implements INetworkHandler {

	private static final Exception NotYetConnectedException = null;
	private static MACKNetworkController instance;
	private String xmmpID;

	private MACKNetworkController() {

	}


	private String getXmmpID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Setter method for xmmpdID. This need to be set before the first usage of this Object!
	 * @param xmmpID
	 */
	public void setXmmpID(String xmmpID) {
		this.xmmpID = xmmpID;
	}

	@Override
	public boolean send(INetworkComponent sender, Object message) throws Exception {
		checkIfConnected();
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void register(INetworkComponent comp) {
		// TODO Auto-generated method stub

	}

	private static void checkIfConnected() throws Exception {
		if (instance.getXmmpID() == null) {
			throw NotYetConnectedException;
		}	
	}
}
