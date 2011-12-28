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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
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
public class SimulationPanel extends JLayeredPane implements
		ISimulationClockListener, ComponentListener {

	/** Attributes of simulation panel */
	private static final Logger log = Logger.getLogger(SimulationPanel.class
			.getName());

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;

	private final AffineTransform transform;

	private ArrayList<AgentView> agents = new ArrayList<AgentView>();

	/**
	 * Constructor of the simulation panel sets the preferred size.
	 */
	public SimulationPanel() {

		transform = new AffineTransform();

		this.setLayout(null);

		/* set scale to 0 for debugging TODO */
		setSimulationToScale(0);

		/* Draw Border */
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

	}

	/**
	 * This method sets the affine transform and the size of the simulation
	 * panel to the right scale.
	 * 
	 * @param size
	 */
	private void setSimulationToScale(double size) {

		this.transform.setToScale(size / 1000, size / 1000);
		this.setPreferredSize(new Dimension((int) size, (int) size));
	}

	/**
	 * This method adds views for all components in the simulation. The views
	 * are listeners of the particular components.
	 */
	public void paintSimulationComponents() {

		BackroundPanel backroundPanel = new BackroundPanel(transform);
		backroundPanel.setLocation(0, 0);
		this.add(backroundPanel, new Integer(1));

		try {

			/** At first add views for the agents */
			for (Agent agent : SimulationEngine.getInstance().getWorld()
					.getAgents()) {

				AgentView agentView = new AgentView(agent.getCoordinates(),
						transform);
				agent.addListener(agentView);
				agents.add(agentView);
				this.add(agentView, new Integer(2));

			}

		} catch (IllegalAccessException e) {

			log.warning("Exception: " + e.toString());
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

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(final ComponentEvent arg0) {

		JFrame mainFrame = (JFrame) arg0.getSource();
		double size = Math.max(mainFrame.getWidth() - 35,
				mainFrame.getHeight() - 145);

		setSimulationToScale(size);

		for (AgentView agentView : agents) {

			agentView.setPos();
		}
		SimulationPanel.this.invalidate();

	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

}
