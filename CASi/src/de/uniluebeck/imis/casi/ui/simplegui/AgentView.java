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
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

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

	private static final Logger log = Logger.getLogger(AgentView.class
			.getName());

	private Color stateColor;
	private final AffineTransform transform;
	public AgentView(Point2D startPosition, AffineTransform transform) {
		this.transform = transform;
		Point startPoint = getOptimizedPosition(startPosition);
		
		this.setBounds(GraphicFactory.getPointRepresentation(startPoint).x,
				GraphicFactory.getPointRepresentation(startPoint).y, 8, 8);

		/* Set state color to yellow for debugging */
		this.stateColor = Color.YELLOW;

		invalidate();
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
				AgentView.this.setLocation(getOptimizedPosition(newPosition));
				AgentView.this.invalidate();

			}
		});

	}

	/**
	 * Gets the real position for the agent
	 * 
	 * @param position
	 *            the new position
	 * @return the optimized position
	 */
	private Point getOptimizedPosition(Point2D position) {
		Dimension dim = getBounds().getSize();
		Point2D point = new Point2D.Double(position.getX() - dim.width / 2, position.getY() - dim.height / 2);
		point = transform.deltaTransform(point, point);		
		return GraphicFactory.getPointRepresentation(point);
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
