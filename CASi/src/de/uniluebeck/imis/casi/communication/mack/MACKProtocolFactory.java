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

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import de.uniluebeck.imis.casi.simulation.model.Agent;

/**
 * This class generates messages for the communication with the MACK framework.
 * These messages could be send by using the {@link MACKNetworkHandler}.
 * 
 * @author Tobias Mende
 * 
 */
public class MACKProtocolFactory {
	private static final Logger log = Logger
			.getLogger(MACKProtocolFactory.class.getName());

	/**
	 * Generates a pull request message for communication with the MACK
	 * framework
	 * 
	 * @param dataObject
	 *            the agent to get information for
	 * @param subject
	 *            the component type
	 * @param entities
	 *            the interesting entities
	 * @return the message
	 */
	public static String generatePullRequest(Agent dataObject, String subject,
			String... entities) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<message type=\"status\">\n");
		buffer.append("\t<mode>pull</mode>\n");
		buffer.append("\t<subject>" + subject + "</subject>\n");
		buffer.append("\t<request type=\"data\" object=\"" + dataObject
				+ "\">\n");
		for (String entity : entities) {
			buffer.append("\t\t<entity name=\"" + entity + "\"></entity>\n");
		}
		buffer.append("\t</request>\n");
		buffer.append("</message>");
		return buffer.toString();
	}

	/**
	 * Parses a provided XML message according to the XML standard of the MACK
	 * framework and returns the information as object
	 * 
	 * @param message
	 *            the XML message
	 * @return the information object
	 */
	public static MACKInformation parseMessage(String message) {
		MACKInformation information = new MACKInformation(message);
		try {
			XMLReader parser = XMLReaderFactory.createXMLReader();
			parser.setContentHandler(new MACKMessageContentHandler(information));
			parser.parse(new InputSource(new StringReader(message)));
		} catch (Exception e) {
			log.severe("Can't handle XML Message: " + e.fillInStackTrace());
			return null;
		}

		return information;
	}
}
