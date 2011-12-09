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

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;
import de.uniluebeck.imis.casi.utils.Listenable;

/**
 * Template for an actuator, that can be integrated in the simulation world
 * 
 * @author Tobias Mende
 * 
 */
public abstract class AbstractActuator extends AbstractInteractionComponent implements
		Listenable<IActuatorListener> {
	/** id for serialization */
	private static final long serialVersionUID = -181371709984152749L;
	/** Time for pulling values from the server in seconds */
	protected int pullIntervall = 10;
	/** A list of listeners */
	protected List<IActuatorListener> listeners = new ArrayList<IActuatorListener>();
	/** List of actions, that can be vetoed by this actuator */
	protected Collection<AbstractAction> vetoableActions;
	/** Last message, the actuator has received from the network controller */
	protected Object lastResponse;
	
	/**
	 * Constructor for a new actuator with a specified monitored area
	 * @param coordinates the position of this actuator
	 * @param radius the radius of the monitored area
	 * @param direction the face
	 * @param opening the opening angle
	 */
	public AbstractActuator(Point coordinates, int radius, Face direction,
			int opening) {
		super(coordinates, radius, direction, opening);
	}
	
	/**
	 * Constructor for a new actuator
	 * @param coordinates the position of this actuator
	 */
	public AbstractActuator(Point coordinates) {
		super(coordinates);
	}


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
