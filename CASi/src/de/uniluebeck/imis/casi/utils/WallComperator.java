package de.uniluebeck.imis.casi.utils;

import java.util.Comparator;

import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * This comparator tries to compare walls to provide the following functionalities:
 * <ul>
 * 	<li>Sorting walls in a room</li>
 * 	<li>If startpoint of wall a equals endpoint of wall b, b is lower</li>
 *  <li>If endpoint of wall a equals startpoint of wall b, b is higher</li>
 *  <li>They are equal if the equals method returns true</li>
 * 	<li><strong>In other cases, the second wall is rated higher at the moment.</strong></li>
 * </ul>
 * @author Tobias Mende
 *
 */
public class WallComperator implements Comparator<Wall> {

	@Override
	public int compare(Wall first, Wall second) {
		if(first.equals(second)) {
			return 0;
		}
		if(first.getStartPoint().equals(second.getEndPoint())) {
			return -1;
		}
		/*
		 * FIXME better implementation needed
		 * problem is that last wall > first wall and fist wall > last wall in this implementation
		 * e.q. its easier to add walls in the right order in Room.
		 */
		if(first.getEndPoint().equals(second.getStartPoint())) {
			return 1;
		}
		return 1; //FIXME add better cases. not sure whether this works good.
	}

}
