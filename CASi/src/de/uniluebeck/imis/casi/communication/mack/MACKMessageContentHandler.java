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

import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import de.uniluebeck.imis.casi.communication.mack.MACKInformation.MessageMode;
import de.uniluebeck.imis.casi.communication.mack.MACKInformation.MessageType;
import de.uniluebeck.imis.casi.communication.mack.MACKInformation.RequestType;

/**
 * This content handler handles messages of the MACKFramework wich are recived
 * e.g. over the {@link MACKNetworkHandler}. This class is used by the
 * {@link MACKProtocolFactory} and parses {@link MACKInformation} out of a xml
 * message.
 * 
 * @author Tobias Mende
 * 
 */
public class MACKMessageContentHandler implements ContentHandler {
	private static final Logger log = Logger
			.getLogger(MACKMessageContentHandler.class.getName());
	private MACKInformation info;
	private String currentArea;
	private Attributes currentAttributes;
	private String currentValue;

	/**
	 * The constructor for a new content handler
	 * 
	 * @param info
	 *            the information object, in which the information should be
	 *            packed.
	 */
	public MACKMessageContentHandler(MACKInformation info) {
		this.info = info;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		currentValue = new String(ch, start, length);
	}

	@Override
	public void startElement(String uri, String localName,
			String qualifiedName, Attributes attributes) throws SAXException {
		currentValue = null;
		currentAttributes = attributes;
		if (localName.equals("message")) {
			handleMessageElementAttributes(attributes);
		} else if (localName.equals("request")) {
			handleRequestElementAttributes(attributes);
		} else if (localName.equals("accessible")) {
			currentArea = localName;
		} else if (localName.equals("inaccessible")) {
			currentArea = localName;
		}

	}

	/**
	 * extracts the message type from the attributes
	 * 
	 * @param attributes
	 *            the message elements attributes
	 */
	private void handleMessageElementAttributes(Attributes attributes) {
		for (int i = 0; i < attributes.getLength(); i++) {
			if (attributes.getLocalName(i).equals("type")) {
				for (MessageType t : MessageType.values()) {
					if (t.toString().equalsIgnoreCase(attributes.getValue(i))) {
						info.setMessageType(t);
						return;
					}
				}
			}
		}
	}

	/**
	 * Extracts the attributes and adds interesting information to the
	 * {@link MACKInformation} object.
	 * 
	 * @param attributes
	 *            the attributes of the request element
	 */
	private void handleRequestElementAttributes(Attributes attributes) {

		for (int i = 0; i < attributes.getLength(); i++) {
			if (attributes.getLocalName(i).equals("type")) {
				for (RequestType t : RequestType.values()) {
					if (t.toString().equalsIgnoreCase(attributes.getValue(i))) {
						info.setRequestType(t);
						break;
					}
				}
			} else if (attributes.getLocalName(i).equals("object")) {
				info.setObject(attributes.getValue(i));
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qualifiedName)
			throws SAXException {
		if (localName.equals("mode")) {
			for (MessageMode m : MessageMode.values()) {
				if (m.toString().equalsIgnoreCase(currentValue)) {
					info.setMessageMode(m);
					break;
				}
			}
		} else if (localName.equals("subject")) {
			info.setSubject(currentValue);
		} else if (localName.equals("accessible")) {
			currentArea = null;
		} else if (localName.equals("inaccessible")) {
			currentArea = null;
		} else if (localName.equals("entity")) {
			handleEntityEnd();
		}

	}

	/**
	 * Handles the end tag of an entity element. At this point, the entity and
	 * its value are added to the information object.
	 */
	private void handleEntityEnd() {
		if (currentArea == null || currentAttributes == null) {
			log.warning("Can't handle this entity! Current Value: "
					+ currentValue + ", Current Attributes: "
					+ currentAttributes);
			return;
		}
		if (currentArea.equals("accessible")) {
			String entity = null;
			for (int i = 0; i < currentAttributes.getLength(); i++) {
				if (currentAttributes.getLocalName(i).equals("name")) {
					entity = currentAttributes.getValue(i);
					break;
				}
			}
			if (entity != null) {
				info.addAccessibleEntity(entity, currentValue);
			} else {
				log.warning("No entity name found!");
			}
		} else if (currentArea.equals("inaccessible")) {
			for (int i = 0; i < currentAttributes.getLength(); i++) {
				if (currentAttributes.getLocalName(i).equals("name")) {
					info.addInaccesibleEntity(currentAttributes.getValue(i));
				}
			}
		}
		currentAttributes = null;

	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	@Override
	public void setDocumentLocator(Locator locator) {
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
	}

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {}

}
