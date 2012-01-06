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
package de.uniluebeck.imis.casi.simulation.model.actions;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.factory.PathFactory;
import de.uniluebeck.imis.casi.simulation.factory.WorldFactory;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.IPosition;
import de.uniluebeck.imis.casi.simulation.model.Path;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;

/**
 * This class is the representation of a move action.
 * 
 * @author Tobias Mende
 */
public class Move extends AtomicAction {
	/** The speed of the performer (the points it moves per simulated second) */
	public static final int POINTS_PER_SECOND = 10;

	/**
	 * id for serialization
	 */
	private static final long serialVersionUID = -6611222628647039039L;
	/** The development logger */
	private static final Logger log = Logger.getLogger(Move.class.getName());

	/** the performer of this action */
	private AbstractComponent performer;
	/** the start point of this move */
	private IPosition startPosition;
	/** the end point of this move */
	private IPosition endPosition;
	/** the path from start to end point */
	private Path path;
	/** the point which was visited in the last iteration */
	private Point2D lastPoint;
	/** A door which has to be opened while going on */
	private Door doorToOpen;
	/** A door which has to be closed while going on */
	private Door doorToClose;
	/** The last state of the door (to rescue after entering the room) */

	private Door.State doorsLastState;
	/** The iterator used for traveling along the path */
	private ListIterator<Point2D> pathIterator;

	/**
	 * Creates a new move action with a given destination
	 * 
	 * @param endPosition
	 *            the destination to set
	 */
	public Move(IPosition endPosition) {
		this.endPosition = endPosition;
	}

	@Override
	protected boolean internalPerform(AbstractComponent performer) {
		if (path == null) {
			this.performer = performer;
			setStartPosition(performer);
			if (endPosition == null) {
				CASi.SIM_LOG
						.warning("No destination was set for this move. Asuming that the performer has already arrived the destination");
				return true;
			}
			// Do some magic:
			path = PathFactory.findPath(startPosition, endPosition);
			if (path == null) {
				CASi.SIM_LOG
						.warning("No path was found. Can't execute this action. Completing ...");
				return true;
			}
			pathIterator = path.listIterator();
			log.fine("Path calculated succesful, starting move action");
			// Path calculation in the first second completed:
			return false;
		}
		for (int i = 0; i < POINTS_PER_SECOND; i++) {
			if (doorToClose != null) {
				doorToClose.setState(doorsLastState);
				doorToClose = null;
			}
			if (affectingDoor()) {
				if (doorToOpen.getState().equals(Door.State.LOCKED)) {
					CASi.SIM_LOG.warning("Door " + doorToOpen
							+ " is locked. Aborting move");
					return true;
				}
				doorsLastState = doorToOpen.getState();
				doorToOpen.setState(Door.State.OPEN);
				doorToClose = doorToOpen;
			}
			if (pathIterator.hasNext()) {
				lastPoint = pathIterator.next();
				this.performer.setCoordinates(lastPoint);
			} else {
				// Finally arrived:
				return true;
			}
		}
		if (!pathIterator.hasNext()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether this move affects a door or not. saves the affected door
	 * in {@link Move#doorToOpen} if one is found.
	 * 
	 * @return {@code true} if a door is affected or {@code false} otherwise.
	 */
	private boolean affectingDoor() {
		if (lastPoint == null) {
			return false;
		}
		doorToOpen = WorldFactory.getDoorWithPoint(lastPoint);
		return doorToOpen != null;
	}

	/**
	 * Setter for the destination of this move. The destination must be set
	 * before performing the action.
	 * 
	 * @param destination
	 *            the destination where the performer should move to.
	 */
	public void setDestination(IPosition destination) {
		endPosition = destination;
	}

	/**
	 * Setter for the start position of this move
	 * 
	 * @param start
	 *            the start position where the performer should move from.
	 */
	private void setStartPosition(IPosition start) {
		startPosition = start;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endPosition == null) ? 0 : endPosition.hashCode());
		result = prime * result
				+ ((performer == null) ? 0 : performer.hashCode());
		result = prime * result
				+ ((startPosition == null) ? 0 : startPosition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (endPosition == null) {
			if (other.endPosition != null)
				return false;
		} else if (!endPosition.equals(other.endPosition))
			return false;
		if (performer == null) {
			if (other.performer != null)
				return false;
		} else if (!performer.equals(other.performer))
			return false;
		if (startPosition == null) {
			if (other.startPosition != null)
				return false;
		} else if (!startPosition.equals(other.startPosition))
			return false;
		return true;
	}

	@Override
	protected boolean preActionTask(AbstractComponent performer) {
		CASi.SIM_LOG.info(performer + " starting Move from "
				+ performer.getCurrentPosition() + " to " + endPosition);
		return super.preActionTask(performer);
	}

	@Override
	protected void postActionTask(AbstractComponent performer) {
		CASi.SIM_LOG.info(performer + " completes Move. Destination: "
				+ performer.getCurrentPosition());
		super.postActionTask(performer);
	}

	@Override
	public void reset() {
		path = null;
		pathIterator = null;
		super.reset();
	}

	@Override
	public String toString() {
		return super.toString() + " - Dest: " + endPosition;
	}

	/**
	 * Getter for a copy of the path iterator
	 * 
	 * @return the pathIterator
	 */
	public Iterator<Point2D> getPathIterator() {
		if (path == null) {
			return null;
		}
		return path.listIterator(pathIterator.nextIndex());
	}

}
