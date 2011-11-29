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
package de.uniluebeck.imis.casi.communication;

/**
 * Interface for classes that control network connections
 * 
 * @author Tobias Mende
 * 
 */
public interface ICommunicationHandler {
	/**
	 * Method for sending a message
	 * 
	 * @param sender
	 *            the component that sends the message
	 * @param message
	 *            the message
	 * @return <code>true</code> if the message was sended successful,
	 *         <code>false</code> otherwise.
	 */
	public boolean send(ICommunicationComponent sender, Object message);

	/**
	 * Method for registering a network component in the network controller
	 * 
	 * @param comp
	 *            the component to register
	 */
	public void register(ICommunicationComponent comp);
}
