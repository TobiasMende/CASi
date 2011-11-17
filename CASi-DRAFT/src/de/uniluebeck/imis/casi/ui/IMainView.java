package de.uniluebeck.imis.casi.ui;

import de.uniluebeck.imis.casi.controller.IUIController;

/**
 * Should be implemented by the the main user interface
 * @author Tobias Mende
 *
 */
public interface IMainView {
	public void setUIController(IUIController controller);
	public void show();
}
