/**
 * 
 */
package de.uniluebeck.imis.casi.simulation.factory;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.uniluebeck.imis.casi.simulation.model.IPosition;
import de.uniluebeck.imis.casi.simulation.model.Room;

/**
 * @author marv
 * 
 */
public class PositionFactory {

	private static Set<Room> rooms = new HashSet<Room>();


	public static IPosition getPostionWithPoint(Point2D p) {

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
	
	public static Room getRoomWithPoint(final Point2D p) {
		for(Room r : rooms) {
			if(r.contains(p)) {
				return r;
			}
		}
		return null;
	}

	public static Room getRoomWithWalls(Collection<Collection<Point2D>> walls) {

		// check if the Room is in the Collection,
		// then return that Room
		// else create a new room and return it.
		return null;
	}

}
