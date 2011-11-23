package de.uniluebeck.imis.casi.generator;

import de.uniluebeck.imis.casi.simulation.model.World;

/**
 * @author marv
 *
 */
public interface IWorldGenerator {

	public World generateWorld() throws Exception;
	
}
