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
