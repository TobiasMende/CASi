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
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;

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
public class AgentView extends ComponentView implements IAgentListener {

	private Agent agent;

	public AgentView(Point2D startPosition, AffineTransform transform) {

		super(startPosition, transform);
	}

	/**
	 * Sets agent to this agent view.
	 * 
	 * @param agent
	 *            the agent
	 */
	public void setAgent(Agent agent) {

		this.agent = agent;
		this.setToolTipText(agent.getName()+"("+agent.getIdentifier()+")");
	}

	/**
	 * Overrides the paint-method to paint the agent as a 8x8 filled oval.
	 */
	public void paint(Graphics g) {

		Graphics2D g2D = (Graphics2D) g;

		g2D.setColor(Color.BLACK);
		g2D.fillOval(0, 0, 8, 8);
		g2D.setColor(this.stateColor);
		g2D.fillOval(2, 2, 4, 4);
	}

	@Override
	public void stateChanged(final STATE newState, Agent agent) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				/** Set state color depending on the new state */
				switch (newState) {

				case BUSY:
					AgentView.this.stateColor = Color.RED;
					break;
				case IDLE:
					AgentView.this.stateColor = Color.GREEN;
					break;
				case UNKNOWN:
					AgentView.this.stateColor = Color.YELLOW;
					break;
				default:
					AgentView.this.stateColor = Color.BLACK;
				}

				AgentView.this.invalidate();
				AgentView.this.repaint();
			}
		});
	}

	@Override
	public void positionChanged(Point2D oldPosition, final Point2D newPosition,
			Agent agent) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				/* Simply set the new location to the new position */
				position = newPosition;

				AgentView.this.setPos();

			}
		});

	}

	@Override
	public void interruptibilityChanged(
			final INTERRUPTIBILITY interruptibility, final Agent agent) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Handle here!!!

			}
		});

	}

	@Override
	public void startPerformingAction(final AbstractAction action,
			final Agent agent) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Handle here!!!

			}
		});

	}

	@Override
	public void finishPerformingAction(final AbstractAction action,
			final Agent agent) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Handle here!!!

			}
		});

	}

}
