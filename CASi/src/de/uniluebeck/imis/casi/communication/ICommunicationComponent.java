package de.uniluebeck.imis.casi.communication;

/**
 * This interface can be implemented by components of the simulation that have
 * to deal with a communication handler. It provides methods, needed for a two
 * way communication.
 * 
 * @author Tobias Mende
 * 
 */
public interface ICommunicationComponent {
	/**
	 * Getter for the unique identifier of this component
	 * 
	 * @return the identifier
	 */
	public String getIdentifier();

	/**
	 * Method for receiving a message
	 * 
	 * @param message
	 *            the message
	 */
	public void receive(Object message);
}
