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

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

/**
 * @author Moritz Bürger
 * 
 */
public class SimpleGuiScaler {

	private static final Logger log = Logger.getLogger(SimpleGuiScaler.class
			.getName());

	private static double scaleFactor = 2;

	public static void setScaleFactor(double scaleFactor) {

		SimpleGuiScaler.scaleFactor = scaleFactor;
	}
	
	public static double getScaleFactor() {
		
		return scaleFactor;
	}

	/**
	 * Returns scaled shape representation of a given shape. (Only works for
	 * polygons yet.)
	 * 
	 * @param shape
	 *            the shape
	 * @return scaled shape
	 */
	public static Shape getShapeRepresentation(Shape shape) {

		if (shape.getClass().getName() == "java.awt.geom.Line2D$Double") {

			Line2D.Double line = (Line2D.Double) shape;

			return new Line2D.Double(line.x1 * scaleFactor, line.y1
					* scaleFactor, line.x2 * scaleFactor, line.y2 * scaleFactor);

		} else if (shape.getClass().getName() == "java.awt.Polygon") {

			Polygon polygonShape = (Polygon) shape;
			int[] newXPoints = new int[polygonShape.npoints];
			int[] newYPoints = new int[polygonShape.npoints];

			for (int i = 0; i < polygonShape.npoints; i++) {

				newXPoints[i] = (int) (polygonShape.xpoints[i] * scaleFactor);
				newYPoints[i] = (int) (polygonShape.ypoints[i] * scaleFactor);

			}

			return new Polygon(newXPoints, newYPoints, polygonShape.npoints);

		}

		log.warning("This shape is not scaleable!");

		return new Polygon();
	}

	/**
	 * Returns a scaled normal point from a point2d.
	 * 
	 * @param point
	 *            the point as {@link Point2D}
	 * @return the point as {@link Point}
	 */
	public static Point getPointRepresentation(Point2D point2d) {

		Point point = new Point((int) (point2d.getX() * scaleFactor),
				(int) (point2d.getY() * scaleFactor));
		return point;
	}

	/**
	 * Returns a scaled normal point with given x and y.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the point as {@link Point}
	 */
	public static Point getPoint(double x, double y) {

		Point point = new Point();
		point.setLocation(x * scaleFactor, y * scaleFactor);
		return point;
	}
}
