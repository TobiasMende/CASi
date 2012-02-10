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

import de.uniluebeck.imis.casi.simulation.model.actions.Move;
import de.uniluebeck.imis.casi.simulation.model.mackActions.TurnCube;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.Cube;

/**
 * This class represents a set of configurations which can be used in
 * {@link AbstractComponent}s to specify information about these components. It
 * contains some constants for common identifiers which may be used by some
 * components.
 * 
 * Feel free to add further identifier. This file is used to collect common
 * identifier and for documenting their use in the simulation. There are many
 * further cases where a configuration map is an useful object. E.g.: speed of
 * agents, additional object, agents may care with them and so on.
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
	 * 
	 * This identifier usually makes sense if configuring the behavior or the
	 * experience of an {@link Agent}.
	 * 
	 * The value should be between {@code 0.0} and {@code 1.0}.
	 */
	public static final String TURN_CUBE_PROBABILITY = "TURN_CUBE_PROBABILITY";

	/**
	 * Under this identifier an {@link Double}-Object may be saved at which
	 * relativ speed an {@link Agent} should walk during a {@link Move} -Action.
	 */
	public static final String MOVE_SPEED = "MOVE_SPEED";

	/**
	 * serialization identifier
	 */
	private static final long serialVersionUID = -2492536412558164741L;

}
