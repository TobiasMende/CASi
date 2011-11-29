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
package de.uniluebeck.imis.casi.simulation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.uniluebeck.imis.casi.communication.ICommunicationComponent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.utils.Listenable;

//TODO add fancy solution for triggering actions
/**
 * Template for an actuator, that can be integrated in the simulation world
 * 
 * @author Tobias Mende
 * 
 */
public abstract class AbstractActuator extends AbstractComponent implements
		ICommunicationComponent, Listenable<IActuatorListener> {
	public AbstractActuator(String identifier) {
		super(identifier);
	}

	/** Time for pulling values from the server in seconds */
	protected int pullIntervall = 10;
	protected List<IActuatorListener> listeners = new ArrayList<IActuatorListener>();

	/** List of actions, that can be vetoed by this actuator */
	protected Collection<AbstractAction> vetoableActions;
	/** Last message, the actuator has received from the network controller */
	protected Object lastResponse;

	/**
	 * Getter for the type of this sensor
	 * 
	 * @return the sensor type
	 */
	public String getType() {
		return this.getClass().getName();
	}

	/**
	 * Method for handling an action
	 * 
	 * @param action
	 *            the action to handle
	 * @return <code>true</code> if the action is allowed, <code>false</code>
	 *         otherwise
	 */
	public final boolean handle(AbstractAction action) {
		// Do fancy things
		return handleInternal(action);
	}

	/**
	 * Method for handling an action internal. Overwrite for customized behavior
	 * 
	 * @param action
	 *            the action to handle
	 * @return <code>true</code> if the action is allowed, <code>false</code>
	 *         otherwise
	 */
	protected abstract boolean handleInternal(AbstractAction action);

	public abstract String getHumanReadableValue();

	@Override
	public void addListener(IActuatorListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeListener(IActuatorListener listener) {
		listeners.remove(listener);
	}

}
