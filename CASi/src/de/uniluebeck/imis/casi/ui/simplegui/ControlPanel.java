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
package de.uniluebeck.imis.casi.ui.simplegui;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * The ControlPanel is a JPanel that allows to pause and resume the simulation
 * with a {@link PauseButton}.
 * 
 * @author Moritz Bürger
 * 
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	/**
	 * The constructor sets layout and components.
	 */
	public ControlPanel() {

		this.setBorder(BorderFactory.createTitledBorder("Control field:"));

		/** Set Layout */
		this.setLayout(new FlowLayout());

		/** Set the components */
		this.setComponents();

	}

	/**
	 * This method sets the components of the control panel.
	 */
	private void setComponents() {

		/** Set start button */
		PauseButton pauseButton = new PauseButton();
		pauseButton.setSize(pauseButton.getPreferredSize());
		this.add(pauseButton);

	}

}
