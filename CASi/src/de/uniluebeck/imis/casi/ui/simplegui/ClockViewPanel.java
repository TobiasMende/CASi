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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;

/**
 * This class extends JPanel and shows the simulation time in the simple GUI.
 * 
 * @author Moritz Bürger
 *
 */
@SuppressWarnings("serial")
public class ClockViewPanel extends JPanel implements ISimulationClockListener {
	
	private SimulationTime time;
	
	public ClockViewPanel() {
		
		/** Add the clock panel as listener on the simulation clock */
		SimulationClock.getInstance().addListener(this);
		
		this.setPreferredSize(new Dimension(0, 20));
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.drawString(time.toString(), 15, 15);
	}

	/* (non-Javadoc)
	 * @see de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener#timeChanged(de.uniluebeck.imis.casi.simulation.model.SimulationTime)
	 */
	@Override
	public void timeChanged(SimulationTime newTime) {

		this.time = newTime;
		this.repaint();
		
	}

	/* (non-Javadoc)
	 * @see de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener#simulationPaused(boolean)
	 */
	@Override
	public void simulationPaused(boolean pause) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener#simulationStopped()
	 */
	@Override
	public void simulationStopped() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener#simulationStarted()
	 */
	@Override
	public void simulationStarted() {
		// TODO Auto-generated method stub
		
	}

}
