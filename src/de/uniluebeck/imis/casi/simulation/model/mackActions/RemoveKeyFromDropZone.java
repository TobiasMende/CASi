/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012 2012  Moritz Bürger, Marvin Frick, Tobias Mende
 *
 *  This program is free software. It is licensed under the
 *  GNU Lesser General Public License with one clarification.
 *  
 *  You should have received a copy of the 
 *  GNU Lesser General Public License along with this program. 
 *  See the LICENSE.txt file in this projects root folder or visit
 *  <http://www.gnu.org/licenses/lgpl.html> for more details.
 */
package de.uniluebeck.imis.casi.simulation.model.mackActions;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AtomicAction;
import de.uniluebeck.imis.casi.simulation.model.mackComponents.DropZone;

/**
 * This action can be used to remove a key from a drop zone.
 * 
 * @author Tobias Mende
 * 
 */
public class RemoveKeyFromDropZone extends AtomicAction {

	/**
	 * serialization identifier
	 */
	private static final long serialVersionUID = 4502377324115676498L;
	/** The drop zone to deal with */
	private DropZone dropZone;

	/**
	 * Constructor for a new action which allows the performer to remove a key
	 * from the drop zone
	 * 
	 * @param dz
	 *            The drop zone to put the key in
	 */
	public RemoveKeyFromDropZone(DropZone dz) {
		dropZone = dz;
	}

	@Override
	protected boolean internalPerform(AbstractComponent performer) {
		if (!(performer instanceof Agent)) {
			CASi.SIM_LOG.warning(performer
					+ " isn't an agent. Nothing to do here");
			return true;
		}
		if (performerIsInDropZoneRoom(performer)) {
			CASi.SIM_LOG.warning("Can't add " + performer + " to " + dropZone
					+ " because they are not in the same room");
			return true;
		}
		dropZone.removeKey((Agent) performer);
		return true;
	}

	/**
	 * Checks whether the performer is in the same room as the drop zone
	 * 
	 * @param performer
	 *            the performer
	 * @return {@code true} if the positions are equal, {@code false} otherwise.
	 */
	private boolean performerIsInDropZoneRoom(AbstractComponent performer) {
		return performer.getCurrentPosition().equals(
				dropZone.getCurrentPosition());
	}

	@Override
	public String getInformationDescription() {
		return "remove key from "+dropZone;
	}
}
