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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;

/**
 * This class extends JPanel and paints the walls and doors of the simulation.
 * 
 * @author Moritz Buerger
 * 
 */

@SuppressWarnings("serial")
public class SimulationPanel extends JLayeredPane implements ISimulationClockListener {

	/** Attributes of simulation panel */
	private static final Logger log = Logger.getLogger(
			SimulationPanel.class.getName());

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	
	private final AffineTransform transform;
	
	/**
	 * Constructor of the simulation panel sets the preferred size.
	 */
	public SimulationPanel() {
		transform = new AffineTransform();
		transform.setToScale(2,2);
		/** Set preferred size */
		this.setPreferredSize(new Dimension(
				SimulationPanel.WIDTH,
				SimulationPanel.HEIGHT));
		
		this.setLayout(null);
		
		 /* Draw Border */
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

	}
	
	/**
	 * This method adds views for all components in the simulation. The views are listeners
	 * of the particular components.
	 */
	public void paintSimulationComponents() {
		
		BackroundPanel backroundPanel = new BackroundPanel(transform);
		backroundPanel.setLocation(0, 0);
		this.add(backroundPanel,new Integer(1));
		
		try {
			
			/** At first add views for the agents */
			for(Agent agent : SimulationEngine.getInstance().getWorld().getAgents()) {
				
				AgentView agentView = new AgentView(agent.getCoordinates(), transform);
				agent.addListener(agentView);
				this.add(agentView,new Integer(2));
				
			}
			
		} catch (IllegalAccessException e) {
			
			log.warning("Exception: "+e.toString());
		}
	}

	@Override
	public void timeChanged(SimulationTime newTime) {
		// nothing to do here at the moment
	}

	@Override
	public void simulationPaused(boolean pause) {
		// nothing to do here at the moment
	}

	@Override
	public void simulationStopped() {
		// nothing to do here at the moment
	}

	@Override
	public void simulationStarted() {
		this.repaint();
	}

}
