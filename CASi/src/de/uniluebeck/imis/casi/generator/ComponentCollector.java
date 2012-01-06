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

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;

/**
 * @author Marvin Frick
 * 
 *         This class creates a singleton object that can be used to create
 *         Agents. It stores a list of already created agents and offers access
 *         to them through either name or id or whatever.
 * 
 */
public class ComponentCollector {
	private static final Logger log = Logger.getLogger(ComponentCollector.class
			.getName());
	
	/** The instance of this singleton */
	private static ComponentCollector instance;

	/**
	 * The List of already created agents.
	 */
	private Set<AbstractInteractionComponent> alreadyCreatedComponents = new HashSet<AbstractInteractionComponent>();

	/**
	 * The private constructor of this singleton
	 */
	private ComponentCollector() {
		// just here for prohibiting external access
	}

	/**
	 * Getter for the instance of this singleton
	 * 
	 * @return the instance
	 */
	public static ComponentCollector getInstance() {
		if (instance == null) {
			instance = new ComponentCollector();
		}
		return instance;
	}

	/**
	 * Add a new component to this collection
	 * 
	 * @param newComponent
	 *            the new Component
	 */
	public void newComponent(AbstractInteractionComponent newComponent) {
		alreadyCreatedComponents.add(newComponent);
	}

	/**
	 * Returns an Component with a given identifier
	 * 
	 * @param identifierToLookFor
	 * @return the Component with this name or (CAUTION) null if this component cannot
	 *         be found!
	 */
	public AbstractInteractionComponent findAgentByIdentifier(String identifierToLookFor) {
		for (AbstractInteractionComponent comp : alreadyCreatedComponents) {
			if (comp.getIdentifier().equals(identifierToLookFor)) {
				return comp;
			}
		}
		log.warning(String.format("couldn't find component %s, mispelled it?", identifierToLookFor));
		return null;
	}

	/**
	 * Getter for the list of agents
	 * 
	 * @return the Vector with all the agents
	 */
	public Set<AbstractInteractionComponent> getAll() {
		return alreadyCreatedComponents;
	}

	/**
	 * Adds all the new components to this collection
	 * 
	 * @param newComponents
	 *            the new Components
	 */
	public void manyNewComponents(
			HashSet<AbstractInteractionComponent> newComponents) {
		for (AbstractInteractionComponent ac : newComponents) {
			newComponent(ac);
		}
		
	}

}
