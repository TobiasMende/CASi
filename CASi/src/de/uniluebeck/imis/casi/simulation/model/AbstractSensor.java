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
 * Template for a sensor, that can be integrated in the simulation world
 * 
 * @author Tobias Mende
 * 
 */
public abstract class AbstractSensor extends AbstractInteractionComponent implements
		Listenable<ISensorListener> {



	/**
	 * id for serialization
	 */
	private static final long serialVersionUID = 7356505164039904916L;

	/** List of actions, that can be recognized by this sensor */
	protected Collection<AbstractAction> sensableActions;
	/** actual value this sensor has recognized */
	protected Object lastValue;
	protected List<ISensorListener> listeners = new ArrayList<ISensorListener>();
	
	public AbstractSensor(Point coordinates, int radius, Face direction,
			int opening) {
		super(coordinates, radius, direction, opening);
	}
	
	public AbstractSensor(Point point) {
		super(point);
	}

	@Override
	public void addListener(ISensorListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeListener(ISensorListener listener) {
		listeners.remove(listener);
	}

}
