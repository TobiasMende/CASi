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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.factory.GraphicFactory;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * The BackgroundPanel is a JPanel that paints all static components of the
 * simulation. It is also a ActionListener (of the ViewMenu) to make it possible
 * to paint only selected components.
 * 
 * @author Moritz Bürger
 * 
 */
@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel implements ActionListener {

	private static final Logger log = Logger.getLogger(BackgroundPanel.class
			.getName());

	/** booleans to adjust the information showed in the gui */
	private boolean paintDoorLabels, paintSensorLabels,
			paintRoomLabels, paintSensorMonitoringArea,
			paintDoorCentralPoints, paintRoomCentralPoints;

	private final AffineTransform transform;

	/**
	 * The BackgroundPanel needs the affine transform to scale the painted
	 * components.
	 * 
	 * @param transform
	 *            the affine transform
	 */
	public BackgroundPanel(AffineTransform transform) {
		
		paintDoorLabels = CASi.DEV_MODE;
		paintSensorLabels = CASi.DEV_MODE;
		paintRoomLabels = CASi.DEV_MODE;
		paintSensorMonitoringArea = CASi.DEV_MODE;
		paintDoorCentralPoints = CASi.DEV_MODE;
		paintRoomCentralPoints = CASi.DEV_MODE;

		this.transform = transform;

		this.repaint();
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);

		/** Cast graphics into 2DGraphics */
		Graphics2D g2D = (Graphics2D) g;

		/** Set the stroke to 3 */
		g2D.setStroke(new BasicStroke(1));

		try {

			/** Get all rooms of the simulation */
			for (Room room : SimulationEngine.getInstance().getWorld()
					.getRooms()) {
				// Show shape representation of room:
				g2D.setColor(Color.LIGHT_GRAY);
				g2D.fill(transform.createTransformedShape(room
						.getShapeRepresentation()));

				// Show central point of room:
				Point2D centralPoint = null;
				centralPoint = transform.deltaTransform(room.getCentralPoint(),
						centralPoint);
				Point centralPoint2 = GraphicFactory.getPoint(
						centralPoint.getX() - 2.5, centralPoint.getY() - 2.5);

				// paint, if it is selected
				if (paintRoomCentralPoints) {
					g2D.setColor(Color.GREEN);
					g2D.fillOval(centralPoint2.x, centralPoint2.y, 5, 5);
				}

				/** Get the walls of this room */
				for (Wall wall : room.getWalls()) {

					/** Paint the walls in black */
					if (wall.getDoors().isEmpty()) {
						// for debugging: make door-less walls red!
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.BLACK);
					}
					g2D.draw(transform.createTransformedShape(wall
							.getShapeRepresentation()));
				}

				/** Get the doors of this room */
				for (Door door : room.getDoors()) {

					g.setColor(Color.BLUE);
					g.setFont(new Font(Font.MONOSPACED, Font.PLAIN,
							(int) (8 * transform.getScaleX())));
					Point2D internalDoorPoint = null;
					internalDoorPoint = transform.deltaTransform(
							door.getCentralPoint(), internalDoorPoint);
					Point doorPoint = GraphicFactory.getPoint(
							internalDoorPoint.getX() - 1.5,
							internalDoorPoint.getY() - 1.5);

					g.setColor(Color.YELLOW);
					// paint door
					g2D.draw(transform.createTransformedShape(door
							.getShapeRepresentation()));
					// Show central point of door:
					g.setColor(Color.BLUE);

					// paint door central point
					if (paintDoorCentralPoints) {
						g2D.fillOval(doorPoint.x, doorPoint.y, 3, 3);
					}
				}

			}

			/** Get the interaction components */
			for (AbstractInteractionComponent interactionComp : SimulationEngine
					.getInstance().getWorld().getInteractionComponents()) {

				g.setColor(Color.BLUE);

				// paint sensor monitoring area
				if (paintSensorMonitoringArea) {
					g2D.draw(transform.createTransformedShape(interactionComp
							.getShapeRepresentation()));
				}

			}

			paintLabels(g2D);

		} catch (IllegalAccessException e) {

			log.warning("Exception: " + e.toString());
		}
	}

	/**
	 * This method paints only the labels of the simulation components.
	 * 
	 * @param g2D
	 *            - the graphics object
	 * @throws IllegalAccessException
	 */
	private void paintLabels(Graphics2D g2D) throws IllegalAccessException {

		for (Room room : SimulationEngine.getInstance().getWorld().getRooms()) {

			// Show central point of room:
			Point2D centralPoint = null;
			centralPoint = transform.deltaTransform(room.getCentralPoint(),
					centralPoint);
			Point centralPoint2 = GraphicFactory.getPoint(
					centralPoint.getX() - 2.5, centralPoint.getY() - 2.5);

			// Show room name:
			g2D.setColor(Color.BLACK); /**/
			g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN,
					(int) (8 * transform.getScaleX()))); /**/

			int nameLength = room.toString().length();

			// paint, if it is selected
			if (paintRoomLabels) {
				g2D.drawString(
						room.toString(),
						(int) (centralPoint2.x - nameLength
								* transform.getDeterminant()),
						centralPoint2.y - 5);
			}

			// paint door names, if selected
			if (paintDoorLabels) {

				/** Get the doors of this room */
				for (Door door : room.getDoors()) {

					g2D.setColor(Color.BLUE); /**/
					g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN,
							(int) (8 * transform.getScaleX()))); /**/
					Point2D internalDoorPoint = null;
					internalDoorPoint = transform.deltaTransform(
							door.getCentralPoint(), internalDoorPoint);
					Point doorPoint = GraphicFactory.getPoint(
							internalDoorPoint.getX() - 1.5,
							internalDoorPoint.getY() - 1.5);

					g2D.drawString(door.toString(), doorPoint.x, doorPoint.y);
				}
			}
		}

		// paint sensor labels, if selected
		if (paintSensorLabels) {

			/** Get the interaction components */
			for (AbstractInteractionComponent interactionComp : SimulationEngine
					.getInstance().getWorld().getInteractionComponents()) {

				g2D.setColor(Color.BLACK); /**/
				g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN,
						(int) (6 * transform.getScaleX()))); /**/

				Point2D internalSensorPoint = null;
				internalSensorPoint = transform.deltaTransform(
						interactionComp.getCentralPoint(), internalSensorPoint);
				Point sensorPoint = GraphicFactory.getPoint(
						internalSensorPoint.getX(),
						internalSensorPoint.getY() - 1.5);

				g2D.drawString(interactionComp.getHumanReadableValue(),
						sensorPoint.x,
						sensorPoint.y + (int) (5 * transform.getScaleX()));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		JRadioButtonMenuItem clickedItem = (JRadioButtonMenuItem) arg0
				.getSource();

		if (arg0.getActionCommand().equals("paintDoorLabels")) {

			paintDoorLabels = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintSensorLabels")) {

			paintSensorLabels = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintRoomLabels")) {

			paintRoomLabels = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintSensorMonitoringArea")) {

			paintSensorMonitoringArea = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintDoorCentralPoints")) {

			paintDoorCentralPoints = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintRoomCentralPoints")) {

			paintRoomCentralPoints = clickedItem.isSelected();

		}

		this.repaint();
	}

}
