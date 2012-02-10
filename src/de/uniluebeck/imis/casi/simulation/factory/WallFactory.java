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
package de.uniluebeck.imis.casi.simulation.factory;

import java.awt.geom.Point2D;
import java.util.Hashtable;

import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * This caching factory's purpose is mainly to cache already created walls
 * during Room creation to make it easier to handle doors. If some wall has a
 * door set and another wall is equal to this wall, it has to get the same door.
 * 
 * @author Marvin Frick
 */
public final class WallFactory {
	/** Saves all walls which have already been created */
	private static final Hashtable<Integer, Wall> wallCache = new Hashtable<Integer, Wall>();

	/**
	 * Getter for a wall with the provided start and end points.
	 * 
	 * Searches for a wall in the wall cache even with reversed start and end
	 * point and creates a new wall if no matching wall exists.
	 * 
	 * @param start
	 *            the start point
	 * @param end
	 *            the end point
	 * @return a wall which matches the start and end point.
	 */
	public static final Wall getWallWithPoints(Point2D start, Point2D end) {

		// check if this wall is in the cache
		Wall cachedWall = wallCache.get(Wall.calculateHashCode(start, end));
		if (cachedWall == null) {

			// maybe there is a wall from end to start
			cachedWall = wallCache.get(Wall.calculateHashCode(end, start));
			if (cachedWall == null) {
				cachedWall = new Wall(start, end);
				wallCache.put(cachedWall.hashCode(), cachedWall);
			}
		}
		return cachedWall;
	}
}
