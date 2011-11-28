package de.uniluebeck.imis.casi.ui.simplegui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 * @author Moritz B�rger
 *
 */

@SuppressWarnings("serial")
public class SimulationPanel extends JPanel {
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(5, 5, 200, 200);
		g.setColor(Color.WHITE);
		g.drawString("Test", 20, 20);
	}

}
