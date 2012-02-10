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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import javax.swing.JPanel;

import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.factory.GraphicFactory;
import de.uniluebeck.imis.casi.simulation.factory.WorldFactory;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * The BackgroundPanel is a {@link JPanel} that paints all static components of
 * the simulation. It is also a {@link MouseListener} of itself to make it
 * possible to show information of the selescted room.
 * 
 * @author Moritz Bürger
 * 
 */
@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel implements MouseListener {

	/** The class logger */
	private static final Logger log = Logger.getLogger(BackgroundPanel.class
			.getName());

	/** The affine transform to scale the background */
	private final AffineTransform transform;

	/** the information panel to show room information */
	private InformationPanel infoPanel;

	/** The view settings save the settings of the view menu */
	private ViewSettings viewSettings;

	/**
	 * The {@link BackgroundPanel} needs the affine transform to scale the
	 * painted components.
	 * 
	 * @param transform
	 *            the affine transform
	 */
	public BackgroundPanel(AffineTransform transform, ViewSettings viewSettings) {

		this.transform = transform;

		this.viewSettings = viewSettings;

		this.setBackground(ColorScheme.BACKGROUND);
		this.repaint();

	}

	/**
	 * This method sets the {@link InformationPanel} to the
	 * {@link BackgroundPanel} and adds itself as mouse listener. So the
	 * InformationPanel can react, when mouse clicks on the background.
	 * 
	 * @param infoPanel
	 *            the information panel
	 */
	public void setInformationPanel(InformationPanel infoPanel) {

		this.infoPanel = infoPanel;
		this.addMouseListener(this);

	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);

		/* Cast graphics into 2DGraphics */
		Graphics2D g2D = (Graphics2D) g;

		/* Set the stroke to 3 */
		g2D.setStroke(new BasicStroke(1));

		try {

			/* Get all rooms of the simulation */
			for (Room room : SimulationEngine.getInstance().getWorld()
					.getRooms()) {
				// Show shape representation of room:
				g2D.setColor(ColorScheme.ROOM);
				g2D.fill(transform.createTransformedShape(room
						.getShapeRepresentation()));

				// Show central point of room:
				Point2D centralPoint = null;
				centralPoint = transform.deltaTransform(room.getCentralPoint(),
						centralPoint);
				Point centralPoint2 = GraphicFactory.getPoint(
						centralPoint.getX() - 2.5, centralPoint.getY() - 2.5);

				// paint, if it is selected
				if (viewSettings.isPaintRoomCentralPoints()) {
					g2D.setColor(ColorScheme.BLUE_DARK);
					g2D.fillOval(centralPoint2.x, centralPoint2.y, 5, 5);
				}

				/* Get the walls of this room */
				for (Wall wall : room.getWalls()) {

					/* Paint the walls in black */
					g.setColor(ColorScheme.BLUE_VERY_DARK);
					g2D.draw(transform.createTransformedShape(wall
							.getShapeRepresentation()));
				}

				/* Get the doors of this room */
				for (Door door : room.getDoors()) {

					g.setFont(new Font(Font.MONOSPACED, Font.PLAIN,
							(int) (8 * transform.getScaleX())));
					Point2D internalDoorPoint = null;
					internalDoorPoint = transform.deltaTransform(
							door.getCentralPoint(), internalDoorPoint);
					Point doorPoint = GraphicFactory.getPoint(
							internalDoorPoint.getX() - 1.5,
							internalDoorPoint.getY() - 1.5);

					g.setColor(ColorScheme.ORANGE_LIGHT);
					// paint door
					g2D.draw(transform.createTransformedShape(door
							.getShapeRepresentation()));
					// Show central point of door:
					g.setColor(ColorScheme.BLUE);

					// paint door central point
					if (viewSettings.isPaintDoorCentralPoints()) {
						g2D.fillOval(doorPoint.x, doorPoint.y, 3, 3);
					}
				}

			}

			/* Get the interaction components */
			for (AbstractInteractionComponent interactionComp : SimulationEngine
					.getInstance().getWorld().getInteractionComponents()) {

				g.setColor(ColorScheme.SENSOR_AREA);

				// paint sensor monitoring area
				if (viewSettings.isPaintSensorMonitoringArea()) {
					g2D.fill(transform.createTransformedShape(interactionComp
							.getShapeRepresentation()));
					Color draw = new Color(ColorScheme.BLUE_DARK.getRed(),
							ColorScheme.BLUE_DARK.getGreen(),
							ColorScheme.BLUE_DARK.getBlue(), 80);
					g.setColor(draw);
					float[] dashes = { 2.0F, 2.0F, 2.0F };
					g2D.setStroke(new BasicStroke(1.0F, BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_MITER, 10.0F, dashes, 0.F));
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
			if (viewSettings.isPaintRoomLabels()) {
				g2D.drawString(
						room.toString(),
						(int) (centralPoint2.x - nameLength
								* transform.getDeterminant()),
						centralPoint2.y - 5);
			}

			// paint door names, if selected
			if (viewSettings.isPaintDoorLabels()) {

				/* Get the doors of this room */
				for (Door door : room.getDoors()) {

					g2D.setColor(Color.BLACK);
					g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN,
							(int) (8 * transform.getScaleX())));
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
		if (viewSettings.isPaintSensorLabels()) {

			/* Get the interaction components */
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

	/**
	 * If mouse clicked on the shape of a room, show room information in the
	 * information panel.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		try {

			Point2D reTransformedPoint = transform.createInverse()
					.deltaTransform(e.getPoint(), e.getPoint());

			if (WorldFactory.getRoomsWithPoint(reTransformedPoint).size() > 0) {

				infoPanel.showRoomInformationOf(WorldFactory.getRoomsWithPoint(
						reTransformedPoint).get(0));
			}

		} catch (NoninvertibleTransformException e1) {

			log.warning("Exception: " + e1);

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
