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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import de.uniluebeck.imis.casi.simulation.model.Agent.STATE;
import de.uniluebeck.imis.casi.simulation.model.IAgentListener;

/**
 * @author Moritz Buerger
 *
 */
@SuppressWarnings("serial")
public class AgentView extends JComponent implements IAgentListener {
	
	private Point2D oldPosition;
	private Point2D newPosition;
	private STATE newState;
	
	public AgentView(Point2D startPosition) {
		
		newPosition = startPosition;
		this.setLocation((int)newPosition.getX(),(int)newPosition.getY());
		this.setPreferredSize(new Dimension(10, 10));
	}
	
	/**
	 * Overrides the paintComponents to paint the agent as a 8x8 filled oval.
	 */
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.BLACK);
		g2D.fillOval(0, 0, 8, 8);
	}

	@Override
	public void stateChanged(STATE newState) {
		
		this.newState = newState;
		
		this.repaint();
	}

	@Override
	public void positionChanged(Point2D oldPosition, Point2D newPosition) {
		
		this.oldPosition = oldPosition;
		this.newPosition = newPosition;
		
		/* Simply set the new location to the new position */
		this.setLocation((int)newPosition.getX(),(int)newPosition.getY());
		/* this.repaint(); */
	}

}
