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
package de.uniluebeck.imis.casi.generator;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulations.mate.generator.java.WorldGenerator;

/**
 * A simulation Linker links all previously created rooms, agents, actions and
 * components together.
 * 
 * 
 * @author Marvin Frick
 * 
 */
public class Linker {

	/**
	 * Method that uses reflection to invoke all the other methods in this
	 * Linker object.
	 * 
	 * Dont touch this. Put all your linker constraints in other, specific
	 * methods. Take care that methods need to start with "link" to be evaluated
	 * automatically!
	 */
	public void linkAll() {
		Logger log = Logger.getLogger(WorldGenerator.class.getName());

		Method[] allMethods = this.getClass().getDeclaredMethods();
		// for every method in this class...
		for (Method m : allMethods) {
			String mname = m.getName();
			// ... if it starts with "link" and is not this "linkAll" method...
			if (mname.startsWith("link") && (!mname.equals("linkAll"))) {
				log.config(String.format("invoking %s", mname));
				try {
					// ... invoke that method!
					m.invoke(this);
				} catch (Exception e) {
					log.severe("Linker had some Problem with linking the desired constraints");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Links Agents their Actions. Both scheduled actions and pool actions. The
	 * action and agent get associated by looking at the beginning of the
	 * actions hashkey, it must not start with anything else than the identifier
	 * of the agent! All actions that have keys ending in "_pool" will be added
	 * to the pool rather than the action list.
	 * 
	 * ASSERT: both Agents and Actions are all set
	 */
	public void linkAgentsToActions() {
		ActionCollector as = ActionCollector.getInstance();

		for (Agent a : AgentCollector.getInstance().getAll()) {
			for (String actionKey : as.getAlreadyCreatedActions().keySet()) {
				if (actionKey.startsWith(a.getIdentifier())) {
					// This Action needs to be added to this agent.
					// Either to his action pool...
					if (actionKey.endsWith("_pool")) {
						a.addActionToPool(as.getAlreadyCreatedActions().get(
								actionKey));
					} else {
						// or to his action list.
						a.addActionToList(as.getAlreadyCreatedActions().get(
								actionKey));
					}
				}
			}
		}
	}

}
