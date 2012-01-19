/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012 2012  Moritz Buerger, Marvin Frick, Tobias Mende
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
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import de.uniluebeck.imis.casi.simulation.factory.GraphicFactory;
import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;

/**
 * This abstract class represents a component of the simulation. All simulation
 * components that are moving and changing their state are represented as
 * extended ComponentViews.
 * 
 * @author Moritz Bürger
 * 
 */
@SuppressWarnings("serial")
public abstract class ComponentView extends JComponent {

	protected boolean isSelected;
	protected Color stateColor;
	protected AffineTransform transform;
	protected Point2D position;

	/**
	 * The ComponentView needs a start position to set the start location and
	 * the affine transform to scale the position and size.
	 * 
	 * @param startPosition
	 *            - start position
	 * @param transform
	 *            - the affine transform
	 */
	public ComponentView(Point2D startPosition, AffineTransform transform) {
		this.transform = transform;
		position = startPosition;

		Point startPoint = getOptimizedPosition(startPosition);
		this.setBounds(startPoint.x, startPoint.y,
				(int) (8 * transform.getScaleX()),
				(int) (8 * transform.getScaleY()));

		/* Set state color to yellow for debugging */
		this.stateColor = Color.WHITE;
		invalidate();
	}

	/**
	 * Sets the {@link ComponentView} as 'selected', if it is representing the
	 * given {@link AbstractComponent} component.
	 * 
	 * @param component
	 *            the component
	 */
	abstract public void setSelected(AbstractComponent component);

	/**
	 * Returns the real position in the simulation of this ComponentView.
	 * 
	 * @return the real position
	 */
	abstract public Point2D getSimulationPosition();

	/**
	 * Gets the real position for the agent
	 * 
	 * @param position
	 *            the new position
	 * @return the optimized position
	 */
	protected Point getOptimizedPosition(Point2D position) {

		Dimension dim = getBounds().getSize();
		Point2D point = new Point2D.Double(transform.getScaleX()
				* position.getX() - dim.width / 2, transform.getScaleY()
				* position.getY() - dim.height / 2);

		return GraphicFactory.getPointRepresentation(point);
	}

	/**
	 * This method sets the position of the agent depending on the affine
	 * transform.
	 */
	public void setTransformedPosition() {

		Point point = getOptimizedPosition(position);
		this.setBounds(point.x, point.y, (int) (8 * transform.getScaleX()),
				(int) (8 * transform.getScaleY()));
		invalidate();
		repaint();
	}

}