package de.uniluebeck.imis.casi.communication;
/**
 * Interface for classes that control network connections
 * @author Tobias Mende
 *
 */
public interface ICommunicationHandler {
	/**
	 * Method for sending a message
	 * @param sender the component that sends the message
	 * @param message the message
	 * @return <code>true</code> if the message was sended successful, <code>false</code> otherwise.
	 */
	public boolean send(ICommunicationComponent sender, Object message);
	/**
	 * Method for registering a network component in the network controller
	 * @param comp the component to register
	 */
	public void register(ICommunicationComponent comp);
}
