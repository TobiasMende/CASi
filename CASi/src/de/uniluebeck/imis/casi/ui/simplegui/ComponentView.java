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

/**
 * @author Moritz Buerger
 * 
 */
@SuppressWarnings("serial")
public abstract class ComponentView extends JComponent {

	protected Color stateColor;
	protected AffineTransform transform;
	protected Point2D position;

	/**
	 * 
	 */
	public ComponentView(Point2D startPosition, AffineTransform transform) {

		this.transform = transform;
		position = startPosition;

		Point startPoint = getOptimizedPosition(startPosition);
		this.setBounds(GraphicFactory.getPointRepresentation(startPoint).x,
				GraphicFactory.getPointRepresentation(startPoint).y, 8, 8);

		/* Set state color to yellow for debugging */
		this.stateColor = Color.YELLOW;

		invalidate();
	}

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
	 * This method sets the position of the agent depending on affine transform.
	 */
	public void setPos() {

		this.setLocation(getOptimizedPosition(position));
		super.invalidate();
	}

}