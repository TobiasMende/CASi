/**
 * 
 */
package de.uniluebeck.imis.casi.simulation.factory;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.IPosition;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * The position factory helps when dealing with different positions.
 * @author Marvin Frick, Tobias Mende
 * 
 */
public class PositionFactory {
	/** The development logger */
	private static final Logger log = Logger.getLogger(PositionFactory.class.getName());
	
	/**
	 * Method for getting a position object for a given point.
	 * 
	 * @param point the point to get the position for
	 * @return the position, if one is found or <code>null</code> otherwise.
	 */
	public static IPosition getPostionWithPoint(Point2D point) {
		// TODO implement & adjust javadoc
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
	
	/**
	 * Method for getting a room that contains a specific point
	 * @param point the point to get a room for
	 * @return a room containing the provided point or <code>null</code> if no room was found.
	 */
	public static Room getRoomWithPoint(final Point2D point) {
		Collection<Room> rooms;
		try {
			rooms = SimulationEngine.getInstance().getWorld().getRooms();
			for(Room r : rooms) {
				if(r.contains(point)) {
					return r;
				}
			}
		} catch (IllegalAccessException e) {
			log.severe("Method should not be called before sealing the world. "+e.fillInStackTrace());
		}
		return null;
	}
	
	/**
	 * Method for getting a room with a provided collection of walls
	 * @param walls the walls to search the room for
	 * @return the room, if one was found or <code>null</code> otherwise.
	 */
	public static Room getRoomWithWalls(Collection<Wall> walls) {
		// TODO implement & adjust javadoc
		// check if the Room is in the Collection,
		// then return that Room
		// else create a new room and return it.
		return null;
	}

}
