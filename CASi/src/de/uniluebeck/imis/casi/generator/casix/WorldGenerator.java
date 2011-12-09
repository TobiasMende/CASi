/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende
 *
 *  This program is free software. It is licensed under the
 *  GNU Lesser General Public License with one clarification.
 *  
 *  You should have received a copy of the 
 *  GNU Lesser General Public License along with this program. 
 *  See the LICENSE.txt file in this projects root folder or visit
 *  <http://www.gnu.org/licenses/lgpl.html> for more details.
 */
package de.uniluebeck.imis.casi.generator.casix;

import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.World;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

public class WorldGenerator implements IWorldGenerator {

	public World WorldGenWithCASiXFilepath(String filepath)
			throws ParserConfigurationException, SAXException, IOException {
		// TODO: open File at filePath
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);

		Collection<Agent> agents = new AgentsGenerator(
				doc.getElementsByTagName("agent")).genObjectFromXML();
		Collection<AbstractAction> actions = new ActionsGenerator(
				doc.getElementsByTagName("action")).genObjectFromXML();
		Collection<Room> rooms = new RoomsGenerator(
				doc.getElementsByTagName("room")).genObjectFromXML();

		buildUpReferences(agents, actions, null, rooms);
		return worldFromCollectionsAndConfs(agents, actions, null, rooms);

	}

	private World worldFromCollectionsAndConfs(Collection<Agent> agents,
			Collection<AbstractAction> actions,
			Collection<AbstractInteractionComponent> interactionComps, Collection<Room> rooms) {
		// TODO Auto-generated method stub
		return new World();
	}

	private void buildUpReferences(Collection<Agent> agents,
			Collection<AbstractAction> actions,
			Collection<AbstractInteractionComponent> interactionComps, Collection<Room> rooms) {
		// TODO Auto-generated method stub
	}

	@Override
	public World generateWorld() {
		// TODO Auto-generated method stub
		return null;
	}

}
