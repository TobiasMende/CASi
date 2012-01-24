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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;
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

	/** The represented agent */
	private Agent agent;
	
	/** The information panel to set the selected agent in the combobox */
	private InformationPanel infoPanel;
	
	/** The simulation panel to repaint it, when agents are overlapping*/
	private SimulationPanel simPanel;

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
		stateColor = getStateColor(agent.getState());
		setToolTipText(agent.getName() + " (" + agent.getIdentifier() + ")");
	}

	@Override
	public void setSelected(AbstractComponent component) {

		isSelected = component.equals(agent);

		this.repaint();

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
	 * This method sets the {@link SimulationPanel} in the AgentView.
	 * 
	 * @param simPanel
	 *            the simulation panel
	 */
	public void setSimulationPanel(SimulationPanel simPanel) {

		this.simPanel = simPanel;

	}

	/**
	 * This method paints the agent as a filled circle.
	 */
	@Override
	public void paint(final Graphics g) {

		super.paint(g);

		Graphics2D g2D = (Graphics2D) g;

		Dimension dim = getSize();

		if (this.isSelected) {
			g2D.setColor(Color.BLACK);
			g2D.fillOval(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
			g2D.setColor(ColorScheme.ORANGE_VERY_LIGHT);
		}

		if (!this.isSelected) {

			g2D.setColor(Color.BLACK);
		}
		g2D.fillOval(1, 1, (int) dim.getWidth() - 2, (int) dim.getHeight() - 2);

		g2D.setColor(stateColor);
		g2D.fillOval(2, 2, (int) dim.getWidth() - 4, (int) dim.getHeight() - 4);
	}

	/**
	 * Returns a color depending on the given {@link STATE}.
	 * 
	 * @param state
	 *            the state
	 * @return the color
	 */
	private Color getStateColor(STATE state) {

		/** Set state color depending on the new state */
		switch (state) {

		case BUSY:
			return Color.RED;
		case IDLE:
			return Color.GREEN;
		case UNKNOWN:
			return Color.YELLOW;
		default:
			return Color.BLACK;
		}

	}

	/**
	 * Sets the state color of this AgentView, if the state changed.
	 */
	@Override
	public void stateChanged(final STATE newState, Agent agent) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				stateColor = getStateColor(newState);
				AgentView.this.invalidate();
				AgentView.this.repaint();
			}
		});
	}

	/**
	 * Sets the position of this AgentView, and repaints the simulation, if the
	 * agent moved from or to a room central point.
	 */
	@Override
	public void positionChanged(final Point2D oldPosition,
			final Point2D newPosition, Agent agent) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				// check if the old point was in the near of a room central
				// point
				if (simPanel.isNearRoomPoint(oldPosition)) {

					// if true, repaint the simulation
					simPanel.paintScaledSimulation();
				}

				// Simply set the new location to the new position
				position = newPosition;

				// check if the new point is in the near of a room central point
				if (simPanel.isNearRoomPoint(position)) {

					// if true, repaint the simulation
					simPanel.paintScaledSimulation();

				} else {

					// else only repaint this agent
					AgentView.this.setTransformedPosition();
				}

			}
		});

	}

	@Override
	public void interruptibilityChanged(
			final INTERRUPTIBILITY interruptibility, final Agent agent) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// nothing to do here

			}
		});

	}

	@Override
	public void startPerformingAction(final AbstractAction action,
			final Agent agent) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// nothing to do here

			}
		});

	}

	@Override
	public void finishPerformingAction(final AbstractAction action,
			final Agent agent) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// nothing to do here

			}
		});

	}

	/**
	 * Sets this agent as selected in the information panel.
	 */
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

	/**
	 * Returns the position of the {@link Agent} that is represented by this
	 * AgentView.
	 */
	@Override
	public Point2D getSimulationPosition() {

		return agent.getCentralPoint();
	}

}
