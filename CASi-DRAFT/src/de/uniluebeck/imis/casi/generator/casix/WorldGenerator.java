package de.uniluebeck.imis.casi.generator.casix;

import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.uniluebeck.imis.casi.generator.IWorldGenerator;
import de.uniluebeck.imis.casi.simulation.model.AbstractAction;
import de.uniluebeck.imis.casi.simulation.model.AbstractSensor;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.World;

public class WorldGenerator implements IWorldGenerator {

	public World WorldGenWithCASiXFilepath(String filepath) throws ParserConfigurationException, SAXException, IOException {
		// TODO: open File at filePath
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);

		Collection<Agent> agents = new AgentsGenerator(
				doc.getElementsByTagName("agent")).genObjectFromXML();
		Collection<AbstractAction> actions = new ActionsGenerator(
				doc.getElementsByTagName("action")).genObjectFromXML();
		Collection<AbstractSensor> sensors = new SensorsGenerator(
				doc.getElementsByTagName("sensors")).genObjectFromXML();
		Collection<Room> rooms = new RoomsGenerator(doc.getElementsByTagName("room"))
				.genObjectFromXML();

		buildUpReferences(agents, actions, sensors, rooms);
		return worldFromCollectionsAndConfs(agents, actions, sensors, rooms);

	}

	private World worldFromCollectionsAndConfs(Collection<Agent> agents,
			Collection<AbstractAction> actions,
			Collection<AbstractSensor> sensors, Collection<Room> rooms) {
		// TODO Auto-generated method stub
		return new World();
	}

	private void buildUpReferences(Collection<Agent> agents,
			Collection<AbstractAction> actions,
			Collection<AbstractSensor> sensors, Collection<Room> rooms) {
		// TODO Auto-generated method stub
	}

	@Override
	public World generateWorld() {
		// TODO Auto-generated method stub
		return null;
	}

}
