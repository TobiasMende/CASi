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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import javax.swing.JPanel;

import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.factory.GraphicFactory;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * @author Moritz Buerger
 *
 */
@SuppressWarnings("serial")
public class BackroundPanel extends JPanel {
	
	private static final Logger log = Logger.getLogger(
			BackroundPanel.class.getName());
		
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	
	public BackroundPanel() {
				
		/** Set preferred size */
		this.setSize(new Dimension(
				BackroundPanel.WIDTH,
				BackroundPanel.HEIGHT));
		
		this.repaint();
	}
	
	/**
	 * Overrides the paintComponent method to paint walls and doors of the
	 * simulation.
	 */
	public void paintComponent(Graphics g) {
				
		/** Call paintComponents of the superior class */
		super.paintComponent(g);
		
		/** Cast graphics into 2DGraphics */
		Graphics2D g2D = (Graphics2D) g;
		
		/** Set the stroke to 3 */
		g2D.setStroke(new BasicStroke(1));
		
		try {
			
			/** Get all rooms of the simulation */
			for(Room room : SimulationEngine.getInstance().getWorld().getRooms()) {
				// Show shape representation of room:
				g2D.setColor(Color.LIGHT_GRAY);
				g2D.fill(room.getShapeRepresentation());
				// Show central point of room:
				Point2D centralPoint = room.getCentralPoint();
				g2D.setColor(Color.GREEN);
				Point centralPoint2 = GraphicFactory.getPoint(centralPoint.getX()-2.5, centralPoint.getY()-2.5);
				g2D.fillOval(centralPoint2.x,centralPoint2.y, 5, 5);
				// Show room name:
				g.setColor(Color.BLACK);
				g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 8));
				g2D.drawString("("+centralPoint2.x+"|"+centralPoint2.y+")", centralPoint2.x-10, centralPoint2.y-5);
				/** Get the walls of this room */
				for(Wall wall : room.getWalls()) {
					
					/** Paint the walls in black */
					if (wall.getDoors().isEmpty()) {
						// for debugging: make door-less walls red!
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.BLACK);	
					}
					g2D.draw(wall.getShapeRepresentation());
				}
				
				/** Get the doors of this room */
				for(Door door : room.getDoors()) {
					g.setColor(Color.BLUE);
					g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 8));
					Point doorPoint = GraphicFactory.getPoint(door.getCentralPoint().getX()-1.5, door.getCentralPoint().getY()-1.5);
					g2D.drawString(door.toString(), doorPoint.x, doorPoint.y);
					g.setColor(Color.LIGHT_GRAY);
					g2D.draw(door.getShapeRepresentation());
					// Show central point of door:
					g.setColor(Color.BLUE);
					g2D.fillOval(doorPoint.x, doorPoint.y, 3, 3);
				}
				
			}
			
		} catch (IllegalAccessException e) {
			
			log.warning("Exception: "+e.toString());
		}
	}

}