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


/**
 * This class represents a xmpp identifier that is used by the {@link MACKNetworkHandler}
 * @author Tobias Mende
 *
 */
public class XmppIdentifier {
	private String componentType;
	private String componentOwner;
	private String jabberId;
	
	public XmppIdentifier(String compOwner, String compType, String jabberUser) {
		componentType = compType;
		componentOwner = compOwner;
		this.jabberId = jabberUser;
	}
	
	public String getComponentOwner() {
		return componentOwner;
	}
	
	public String getComponentType() {
		return componentType;
	}
	
	public String getId() {
		return jabberId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((componentOwner == null) ? 0 : componentOwner.hashCode());
		result = prime * result
				+ ((componentType == null) ? 0 : componentType.hashCode());
		result = prime * result
				+ ((jabberId == null) ? 0 : jabberId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XmppIdentifier other = (XmppIdentifier) obj;
		if (componentOwner == null) {
			if (other.componentOwner != null)
				return false;
		} else if (!componentOwner.equals(other.componentOwner))
			return false;
		if (componentType == null) {
			if (other.componentType != null)
				return false;
		} else if (!componentType.equals(other.componentType))
			return false;
		if (jabberId == null) {
			if (other.jabberId != null)
				return false;
		} else if (!jabberId.equals(other.jabberId))
			return false;
		return true;
	}
	
	
	
}
