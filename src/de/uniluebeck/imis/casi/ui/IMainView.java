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
package de.uniluebeck.imis.casi.ui;

import de.uniluebeck.imis.casi.controller.IUIController;

/**
 * Should be implemented by the the main user interface
 * 
 * @author Tobias Mende
 * 
 */
public interface IMainView {

	/**
	 * Sets the UIController of the IMainView.
	 * 
	 * @param controller
	 *            the UI controller
	 */
	public void setUIController(IUIController controller);

	/**
	 * Shows the IMainView.
	 */
	public void showUi();
}
