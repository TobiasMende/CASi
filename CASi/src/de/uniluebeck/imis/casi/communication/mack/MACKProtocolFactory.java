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
package de.uniluebeck.imis.casi.communication.mack;

import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.uniluebeck.imis.casi.simulation.model.Agent;

/**
 * This class generates messages for the communication with the MACK framework. These messages could be send by using the {@link MACKNetworkHandler}.
 * @author Tobias Mende
 *
 */
public class MACKProtocolFactory {
	private static final Logger log = Logger.getLogger(MACKProtocolFactory.class.getName());

	/**
	 * Generates a pull request message for communication with the MACK framework
	 * @param dataObject the agent to get information for
	 * @param subject the component type
	 * @param entities the interesting entities
	 * @return the message
	 */
	public static String generatePullRequest(Agent dataObject, String subject, String... entities) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<message type=\"status\">\n");
		buffer.append("\t<mode>pull</mode>\n");
		buffer.append("\t<subject>"+subject+"</subject>\n");
		buffer.append("\t<request type=\"data\" object=\"" + dataObject + "\">\n");
		for(String entity : entities) {
			buffer.append("\t\t<entity name=\""+entity+"\"></entity>\n");
		}
		buffer.append("\t</request>\n");
		buffer.append("</message>");
		return buffer.toString();
	}
	
	
	
	public static MACKInformation parseMessage(String message) {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = null;
		MACKInformation information = null;
		try {
			parser = factory.createXMLStreamReader(new StringReader(message));
			information = parseMessageInternal(parser);
		} catch (XMLStreamException e) {
			log.severe("Can't handle XML Message: "+e.fillInStackTrace());
		}
		
		return information;
	}
	
	private static MACKInformation parseMessageInternal(XMLStreamReader parser) throws XMLStreamException {
		MACKInformation information = null;
		while(parser.hasNext()) {
			switch(parser.getEventType()) {
			case XMLStreamConstants.START_DOCUMENT:
				log.fine("START_DOCUMENT: "+parser.getVersion());
				parser.next();
				break;
			case XMLStreamConstants.END_DOCUMENT:
				log.fine("END_DOCUMENT: Message successful parsed!");
				parser.next();
				parser.close();
				break;
			case XMLStreamConstants.START_ELEMENT:
				if(parser.getLocalName().equalsIgnoreCase("message")) {
					information = parseMessageHeader(parser);
				}
				break;
			//TODO implement other cases
			}
		}
		return information;
	}
	
	private static MACKInformation parseMessageHeader(XMLStreamReader parser) throws XMLStreamException {
		// We have the message element:
		MACKInformation.MessageType type;
		MACKInformation information = null;
		for(int i = 0; i < parser.getAttributeCount(); i++) {
			if(parser.getAttributeLocalName(i).equalsIgnoreCase("type")) {
				for(MACKInformation.MessageType t : MACKInformation.MessageType.values()) {
					if(t.toString().equalsIgnoreCase(parser.getAttributeValue(i))) {
						type = t;
						break;
					}
				}
			}
		}
		if(!parser.hasNext()) {
			log.warning("Invalid message. Can't extract header");
			return null;
		}
		parser.next();
		// TODO parse  mode, subject, requestType and object. Create MACKInformation
	
		
		return information;
	}
}
