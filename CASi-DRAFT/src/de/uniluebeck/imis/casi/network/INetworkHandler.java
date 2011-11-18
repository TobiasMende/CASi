package de.uniluebeck.imis.casi.network;
/**
 * Interface for classes that control network connections
 * @author Tobias Mende
 *
 */
public interface INetworkHandler {
	/**
	 * Method for sending a message
	 * @param sender the component that sends the message
	 * @param message the message
	 * @return <code>true</code> if the message was sended successful, <code>false</code> otherwise.
	 * @throws Exception if something went wrong 
	 */
	public boolean send(INetworkComponent sender, Object message) throws Exception;
	/**
	 * Method for registering a network component in the network controller
	 * @param comp the component to register
	 */
	public void register(INetworkComponent comp);
}
