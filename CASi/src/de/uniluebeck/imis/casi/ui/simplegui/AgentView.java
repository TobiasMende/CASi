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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import javax.swing.JComponent;

import de.uniluebeck.imis.casi.simulation.factory.GraphicFactory;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent.INTERRUPTIBILITY;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Agent.STATE;
import de.uniluebeck.imis.casi.simulation.model.IAgentListener;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

/**
 * @author Moritz Buerger
 *
 */
@SuppressWarnings("serial")
public class AgentView extends JComponent implements IAgentListener {
	
	private static final Logger log = Logger.getLogger(
			AgentView.class.getName());
	
	private Point2D position;

	private Color stateColor;
	
	public AgentView(Point2D startPosition) {
		
		position = startPosition;
		this.setBounds(GraphicFactory.getPointRepresentation(startPosition).x,
				       GraphicFactory.getPointRepresentation(startPosition).y,8,8);
		
		/* Set state color to yellow for debugging */
		this.stateColor = Color.YELLOW;
		
		invalidate();
	}
	
	/**
	 * Overrides the paintComponents to paint the agent as a 8x8 filled oval.
	 */
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setColor(Color.BLACK);
		g2D.fillOval(0, 0, 8, 8);
		g2D.setColor(this.stateColor);
		g2D.fillOval(2, 2, 4, 4);
	}

	@Override
	public void stateChanged(STATE newState, Agent agent) {
		
		/** Set state color depending on the new state */
		switch (newState) {
			case ABSTRACT:
				this.stateColor = Color.GREEN;
				
			case BUSY:
				this.stateColor = Color.RED;
				
			case IDLE: 
				this.stateColor = Color.WHITE;
				
			case UNKNOWN:
				this.stateColor = Color.GRAY;
				
			default:
				this.stateColor = Color.BLACK;
		}
		
		invalidate();
	}

	@Override
	public void positionChanged(Point2D oldPosition, Point2D newPosition, Agent agent) {
		
		this.position = newPosition;
		/* Simply set the new location to the new position */
		this.setLocation(GraphicFactory.getPointRepresentation(newPosition));
		
		invalidate();
		
		 
	}

	@Override
	public void interruptibilityChanged(INTERRUPTIBILITY interruptibility,
			Agent agent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startPerformingAction(AbstractAction action, Agent agent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishPerformingAction(AbstractAction action, Agent agent) {
		// TODO Auto-generated method stub
		
	}

}
