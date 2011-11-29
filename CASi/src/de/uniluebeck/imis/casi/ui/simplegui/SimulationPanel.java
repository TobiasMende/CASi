/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
 */
package de.uniluebeck.imis.casi.ui.simplegui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * 
 * @author Moritz Bürger
 * 
 */

@SuppressWarnings("serial")
public class SimulationPanel extends JPanel {

	public SimulationPanel() {

		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(5, 5, 200, 200);
		g.setColor(Color.WHITE);
		g.drawString("Test", 20, 20);
	}

}
