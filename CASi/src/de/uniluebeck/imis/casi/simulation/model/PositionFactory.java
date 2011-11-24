/**
 * 
 */
package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.util.Collection;

/**
 * @author marv
 * 
 */
public class PositionFactory {

	private static Collection<Room> rooms = null;
	private static Collection<Point> points = null;


	public static IPosition getPostionWithPoint(Point p) {

		// to streamline the process of getting the actual coordinates form the
		// returned Object we should create a new class that is in fact the
		// awt.Point but implements our IPosition

		// check if the position is in the Collection,
		// then return that position
		// else:
		// create a new Point() and put in in the Collection
		// and return it
		return null;
	}
	
	public static Room getRoomWithPoint(final Point p) {
		for(Room r : rooms) {
			if(r.contains(p)) {
				return r;
			}
		}
		return null;
	}

	public static Room getRoomWithWalls(Collection<Collection<Point>> walls) {

		// check if the Room is in the Collection,
		// then return that Room
		// else create a new room and return it.
		return null;
	}

}
