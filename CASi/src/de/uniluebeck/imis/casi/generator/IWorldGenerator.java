/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
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
