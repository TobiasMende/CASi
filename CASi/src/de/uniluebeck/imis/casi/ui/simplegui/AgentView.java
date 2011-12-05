/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2011 2012  Moritz B�rger, Marvin Frick, Tobias Mende
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

import de.uniluebeck.imis.casi.simulation.factory.GraphicFactory;
import de.uniluebeck.imis.casi.simulation.model.Agent.STATE;
import de.uniluebeck.imis.casi.simulation.model.IAgentListener;

/**
 * @author Moritz Buerger
 *
 */
@SuppressWarnings("serial")
public class AgentView extends JComponent implements IAgentListener {
	
	private Point2D position;
	private STATE newState;
	
	public AgentView(Point2D startPosition) {
		
		position = startPosition;
		this.setLocation(GraphicFactory.getPointRepresentation(startPosition));
		this.setPreferredSize(new Dimension(10, 10));
		invalidate();
	}
	
	/**
	 * Overrides the paintComponents to paint the agent as a 8x8 filled oval.
	 */
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.DARK_GRAY);
		g2D.fillOval((int)(position.getX()-2.5), (int)(position.getY()-2.5), 5, 5);
	}

	@Override
	public void stateChanged(STATE newState) {
		
		this.newState = newState;
		
		invalidate();
	}

	@Override
	public void positionChanged(Point2D oldPosition, Point2D newPosition) {
		
		/* Simply set the new location to the new position */
		this.setLocation(GraphicFactory.getPointRepresentation(newPosition));
		 invalidate();
	}

}
