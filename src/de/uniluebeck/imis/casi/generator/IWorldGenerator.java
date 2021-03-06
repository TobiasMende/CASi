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
package de.uniluebeck.imis.casi.generator;

import de.uniluebeck.imis.casi.simulation.model.World;

/**
 * An interface for generators that are able to create a specific world
 * 
 * @author Marvin Frick
 */
public interface IWorldGenerator {

	/**
	 * Lets the generator create a world
	 * 
	 * @return the world
	 */
	public World generateWorld();

}
