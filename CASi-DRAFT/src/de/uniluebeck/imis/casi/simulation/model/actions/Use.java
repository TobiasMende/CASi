/**
 * 
 */
package de.uniluebeck.imis.casi.simulation.model.actions;

import de.uniluebeck.imis.casi.simulation.model.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;

/**
 * A class with the sole purpose of showing that we need to think about
 * AbstractComponent and whether there are Items in the World that can be
 * interacted with and how one could do that.
 * 
 * @author Marvin Frick
 * 
 */
public class Use extends AbstractAction {

	Agent subject = null;
	AbstractComponent target = null;

	public Use(Agent subject, AbstractComponent target) {
		this.subject = subject;
		this.target = target;
	}

	@Override
	protected STATE internalPerform() {

		// TODO To real things here!
		// target.use() if tagert.methodImplemented("use")
		// otherwise throw new Exception("cant use that!")

		return null;
	}

}
