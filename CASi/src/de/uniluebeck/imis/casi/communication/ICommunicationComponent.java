/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
 */
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
