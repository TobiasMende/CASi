/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012 2012  Moritz Bürger, Marvin Frick, Tobias Mende
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

import java.util.HashMap;

import de.uniluebeck.imis.casi.simulation.model.mackActions.TurnCube;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Cube;

/**
 * This class represents a set of configurations which can be used in
 * {@link AbstractComponent}s to specify information about these components. It
 * contains some constants for common identifiers which may be used by some
 * components.
 * 
 * @author Tobias Mende
 * 
 */
public class ConfigMap extends HashMap<String, Object> {
	/**
	 * Under this identifier a {@link Double}-Object may be saved which
	 * represents the probability a {@link Cube} would use to trigger a
	 * {@link TurnCube} action, when it's state doesn't match the state which is
	 * assumed to be correct.
	 */
	public static final String TURN_CUBE_PROBABILITY = "TURN_CUBE_PROBABILITY";

	/**
	 * serialization identifier
	 */
	private static final long serialVersionUID = -2492536412558164741L;

}
