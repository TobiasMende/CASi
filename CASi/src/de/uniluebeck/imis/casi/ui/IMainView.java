package de.uniluebeck.imis.casi.ui;

import de.uniluebeck.imis.casi.controller.IUIController;

/**
 * Should be implemented by the the main user interface
 * @author Tobias Mende
 *
 */
public interface IMainView {
	
	/**
	 * Sets the UIController of the IMainView.
	 * 
	 * @param controller the UI controller
	 */
	public void setUIController(IUIController controller);
	
	/**
	 * Shows the IMainView.
	 */
	public void showUi();
}
