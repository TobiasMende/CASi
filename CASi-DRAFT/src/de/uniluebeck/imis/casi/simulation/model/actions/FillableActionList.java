/**
 * 
 */
package de.uniluebeck.imis.casi.simulation.model.actions;

import java.util.Vector;

import de.uniluebeck.imis.casi.simulation.model.AbstractAction;

/**
 * This class might be used by a generator to create a single action object from
 * a description language with a combination of multiple simple (concrete)
 * Action objects in it. However, the generator will need to supply the Actions
 * as objects and therefore do smart things to get the classes from its
 * description to instantiate them.
 * 
 * A FillableActionList object will use {@link AbstractAction}'s {@link
 * AbstractAction.perform()} method to execute all of its child-Actions. The
 * sub-actions _must_ be added in a meaningful timed order!
 * 
 * ActionLists can be recursively put in each other to near infinity (till the
 * VMs CallStack overflows)!
 * 
 * @author Marvin Frick
 * 
 */
public class FillableActionList extends AbstractAction {

	@Override
	protected STATE internalPerform() {
		// TODO Auto-generated method stub
		return null;
	}

}
