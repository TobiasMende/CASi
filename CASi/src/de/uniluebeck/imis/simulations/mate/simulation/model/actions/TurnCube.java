/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2011 2012  Moritz Bürger, Marvin Frick, Tobias Mende
 *
 *  This program is free software. It is licensed under the
 *  GNU Lesser General Public License with one clarification.
 *  
 *  You should have received a copy of the 
 *  GNU Lesser General Public License along with this program. 
 *  See the LICENSE.txt file in this projects root folder or visit
 *  <http://www.gnu.org/licenses/lgpl.html> for more details.
 */
package de.uniluebeck.imis.simulations.mate.simulation.model.actions;

import java.util.logging.Logger;

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;
import de.uniluebeck.imis.simulations.mate.simulation.model.Cube;

/**
 * This action can be used to let an agent turn a cube.
 * @author Tobias Mende
 *
 */
public class TurnCube extends AtomicAction {
	private static final Logger log = Logger.getLogger(TurnCube.class.getName());
	private static final long serialVersionUID = 8116854211425530695L;
	private Cube cube;
	private Cube.State state;
	/**
	 * Creates a new turn action
	 * @param cube the cube to turn
	 * @param state the state to which cube should be set
	 */
	public TurnCube(Cube cube, Cube.State state) {
		this.cube = cube;
		this.state = state;
	}
	
	@Override
	protected boolean internalPerform(AbstractComponent performer) {
		if(!cube.contains(performer)) {
			log.warning("The cube is so far away! Can't turn it!");
			return true;
		}
		cube.turnCube(state);
		return true;
	}

}
