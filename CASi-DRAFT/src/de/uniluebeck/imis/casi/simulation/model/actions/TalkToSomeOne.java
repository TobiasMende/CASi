/**
 * 
 */
package de.uniluebeck.imis.casi.simulation.model.actions;

import de.uniluebeck.imis.casi.simulation.model.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.Agent;

/**
 * @author Marvin Frick
 * 
 */
public class TalkToSomeOne extends AbstractAction {

	Agent subject = null;
	Agent target = null;

	public TalkToSomeOne(Agent who, Agent withWhom) {
		this.subject = who;
		this.target = withWhom;
	}

	@Override
	protected STATE internalPerform() {

		// TODO Implement these ideas
		// * check if the target is in talking range (like a 2 meter radius
		// around subject)
		// * if not: go to target
		// * talk to target
		// OR maybe this method should not take care if target is in range and
		// than simply return with STATE.INTERRUPTED. This heavily depends on
		// the context and I think this should be up to the actual creator of a
		// simulation.
		// One more reason why writing own actions as JavaClasses is
		// far better than specifying them in some less flexible configuration
		// format!
		
		
		// EXAMPLE for if target is to far away:
		
		AbstractAction talkToWithWalkingThere = new TalkToSomeOne(subject, target);
		talkToWithWalkingThere.addActionToList(new Move(subject,target)); 
		talkToWithWalkingThere.addActionToList(this);
		talkToWithWalkingThere.perform();
		// TODO: something smart with the STATE/boolean mixed up return values
		return null;
	}

}
