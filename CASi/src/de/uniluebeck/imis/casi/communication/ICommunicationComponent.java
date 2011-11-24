package de.uniluebeck.imis.casi.communication;

public interface ICommunicationComponent {
	/**
	 * Getter for the unique identifier of this component
	 * @return the identifier
	 */
	public String getIdentifier();
	/**
	 * Method for receiving a message
	 * @param message the message
	 */
	public void receive(Object message);
}