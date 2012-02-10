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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;

/**
 * The mack information object is a container for informations that are parsed
 * out of a message which was send by the mack framework.
 * 
 * @author Tobias Mende
 * 
 */
public class MACKInformation {
	/**
	 * The types of which messages can be
	 */
	public enum MessageType {
		/** Status Message */
		status,
		/** Response Message */
		response, 
		/** Unknown Type */
		undefined;
	}

	/**
	 * The mode of the message
	 */
	public enum MessageMode {
		/** Pull Mode */
		pull, 
		/** Push Mode */
		push,
		/** Unknown Mode */
		undefined;
	}

	/** Possible request types */
	public enum RequestType {
		/** Data Request */
		data,
		/** Unknown Request Type */
		undefined;
	}

	/** The type of the message */
	private MessageType messageType;
	/** The mode of the message */
	private MessageMode messageMode;
	/** The request type of this message */
	private RequestType requestType;
	/** The subject (the component type) for which this message was. */
	private String subject;
	/** The object of this message */
	private String object;
	/** The raw xml message on which this object is based */
	private String originalMessage;
	/** A map of accessible entities with their values */
	private Map<String, String> accessibleEntities = new HashMap<String, String>();
	/** A map of entities that arn't accessible */
	private List<String> inaccesibleEntities = new ArrayList<String>();

	/**
	 * Generates a new information object
	 * 
	 * @param messageType
	 *            the type of the message
	 * @param messageMode
	 *            the mode of the message
	 * @param originalMessage
	 *            the raw xml message
	 * @param subject
	 *            the subject (which kind of
	 *            {@link AbstractInteractionComponent})
	 * @param object
	 *            the object (should be an {@link Agent} identifier)
	 */
	public MACKInformation(MessageType messageType, MessageMode messageMode,
			RequestType requestType, String originalMessage, String subject,
			String object) {
		this.messageType = messageType;
		this.messageMode = messageMode;
		this.originalMessage = originalMessage;
		this.requestType = requestType;
		this.subject = subject;
		this.object = object;
	}

	/**
	 * Default Constructor
	 * 
	 * @param message
	 *            the raw xml message
	 */
	public MACKInformation(String message) {
		messageMode = MessageMode.undefined;
		messageType = MessageType.undefined;
		requestType = RequestType.undefined;
		originalMessage = message;
	}

	/**
	 * Adds an accessible entity with its value to this object
	 * 
	 * @param entity
	 *            the entity
	 * @param value
	 *            the value
	 */
	public void addAccessibleEntity(String entity, String value) {
		accessibleEntities.put(entity, value);
	}

	/**
	 * Getter for a map of accessible entities an their values
	 * 
	 * @return a map of accessible entities with their values
	 */
	public Map<String, String> getAccessibleEntities() {
		return accessibleEntities;
	}

	/**
	 * Adds an inaccessible entity to this object
	 * 
	 * @param entity
	 *            the entity
	 */
	public void addInaccesibleEntity(String entity) {
		inaccesibleEntities.add(entity);
	}

	/**
	 * Getter for a list of inaccessible entities
	 * 
	 * @return a list of inaccessible entities
	 */
	public List<String> getInaccesibleEntities() {
		return inaccesibleEntities;
	}

	/**
	 * Setter for the subject of the information
	 * 
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Getter for the subject
	 * 
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Setter for the message mode
	 * 
	 * @param messageMode
	 *            the messageMode to set
	 */
	public void setMessageMode(MessageMode messageMode) {
		this.messageMode = messageMode;
	}

	/**
	 * Getter for the message mode
	 * 
	 * @return the messageMode
	 */
	public MessageMode getMessageMode() {
		return messageMode;
	}

	/**
	 * Setter for the message type
	 * 
	 * @param messageType
	 *            the messageType to set
	 */
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	/**
	 * Getter for the message type
	 * 
	 * @return the messageType
	 */
	public MessageType getMessageType() {
		return messageType;
	}

	/**
	 * Setter for the request type
	 * 
	 * @param requestType
	 *            the requestType to set
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	/**
	 * Getter for the request type
	 * 
	 * @return the requestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * Setter for the identifier of an object (in most cases an
	 * {@link Agent#getIdentifier()})
	 * 
	 * @param object
	 *            the object to set (the identifier should be equal to a (
	 *            {@link Agent#getIdentifier()}))
	 */
	public void setObject(String object) {
		this.object = object;
	}

	/**
	 * Getter for the object of this information
	 * 
	 * @return the object (the identifier should be equal to a (
	 *         {@link Agent#getIdentifier()}))
	 */
	public String getObject() {
		return object;
	}

	/**
	 * Getter for the original xml message
	 * 
	 * @return the originalMessage
	 */
	public String getOriginalMessage() {
		return originalMessage;
	}
}
