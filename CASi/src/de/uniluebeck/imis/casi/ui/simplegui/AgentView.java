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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent.INTERRUPTIBILITY;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Agent.STATE;
import de.uniluebeck.imis.casi.simulation.model.IAgentListener;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

/**
 * This class represents an agent in the CASi simulator. Agents are painted as
 * circles, their state can be noticed by the filling color.
 * 
 * @author Moritz Bürger
 * 
 */
@SuppressWarnings("serial")
public class AgentView extends ComponentView implements IAgentListener,
		MouseListener {

	private Agent agent;
	private InformationPanel infoPanel;

	/**
	 * The AgentView needs the represented agent and the affine transform to
	 * scale the size and position.
	 * 
	 * @param agent
	 *            the agent
	 * @param transform
	 *            the affine transform
	 */
	public AgentView(Agent agent, AffineTransform transform) {
		super(agent.getCentralPoint(), transform);
		this.agent = agent;
		setToolTipText(agent.getName() + " (" + agent.getIdentifier() + ")");
	}

	/**
	 * This method sets the information panel to the agent view and adds itself
	 * as mouse listener. So the information panel can react, when mouse clicks
	 * on the agent view.
	 * 
	 * @param infoPanel
	 *            the information panel
	 */
	public void setInformationPanel(InformationPanel infoPanel) {

		this.infoPanel = infoPanel;
		this.addMouseListener(this);

	}

	/**
	 * This method paints the agent as an 8x8 filled circle.
	 */
	@Override
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

				AgentView.this.setTransformed();

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

	@Override
	public void mouseClicked(MouseEvent arg0) {

		infoPanel.setSelectedAgent(agent);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
