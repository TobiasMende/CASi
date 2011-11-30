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
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.factory.PathFactory;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
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
	/** The iterator used for traveling along the path */
	private Iterator<Point2D> pathIterator;

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
			pathIterator = path.iterator();
			// Path calculation in the first second completed:
			return false;
		}
		log.fine("Path calculated succesful, starting move action");
		for (int i = 0; i < POINTS_PER_SECOND; i++) {
			if (pathIterator.hasNext()) {
				this.performer.setCoordinates(pathIterator.next());
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

}
