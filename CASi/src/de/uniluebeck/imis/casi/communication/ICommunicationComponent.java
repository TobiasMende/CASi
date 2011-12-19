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
	/**
	 * This method should be used to init the component (the component should register at the {@link ICommunicationHandler}).
	 */
	public void init();
}
