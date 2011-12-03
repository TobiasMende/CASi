/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende
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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.Wall;

/**
 * This class extends JPanel and paints the walls and doors of the simulation.
 * 
 * @author Moritz Buerger
 * 
 */

@SuppressWarnings("serial")
public class SimulationPanel extends JPanel implements ISimulationClockListener {

	/** Attributes of simulation panel */
	private static final Logger log = Logger.getLogger(
			SimulationPanel.class.getName());

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	
	/**
	 * Constructor of the simulation panel sets the preferred size.
	 */
	public SimulationPanel() {

		/** Set preferred size */
		this.setPreferredSize(new Dimension(
				SimulationPanel.WIDTH,
				SimulationPanel.HEIGHT));
		
		 /* Draw Border */
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
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
		g2D.setStroke(new BasicStroke(3));
		
		try {
			
			/** Get all rooms of the simulation */
			for(Room room : SimulationEngine.getInstance().getWorld().getRooms()) {
				
				/** Get the walls of this room */
				for(Wall wall : room.getWalls()) {
					
					/** Paint the walls in black */
					g.setColor(Color.BLACK);
					g2D.draw(wall.getShapeRepresentation());
				}
				
				/** Get the doors of this room */
				for(Door door : room.getDoors()) {
					
					/** Paint the rooms in red */
					g.setColor(Color.LIGHT_GRAY);
					g2D.draw(door.getShapeRepresentation());
				}
				
			}
			
		} catch (IllegalAccessException e) {
			
			log.warning("Exception: "+e.toString());
		}
	}

	@Override
	public void timeChanged(SimulationTime newTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulationPaused(boolean pause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulationStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulationStarted() {

		this.repaint();
		
	}

}
