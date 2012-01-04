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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;

/**
 * This class extends JLayeredPanel. It contains a background panel, that
 * contains all static components of the simulation and views for agents and
 * sensors. The simulation resizes/scales depending on the size of the main
 * frame.
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

	private final AffineTransform transform;
	private BackgroundPanel backgroundPanel;

	private ArrayList<AgentView> agents = new ArrayList<AgentView>();

	/**
	 * Constructor of the simulation panel sets the preferred size.
	 */
	public SimulationPanel() {

		transform = new AffineTransform();

		this.setLayout(null);

		/* Draw Border */
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

	}

	/**
	 * This method sets the affine transform and the size of the simulation
	 * panel to the right scale, depending on the frame size.
	 * 
	 */
	private void setSimulationToScale() {

		Container parent = this.getParent();
		double size = Math.max(parent.getWidth(), parent.getHeight());

		this.transform.setToScale(size / 1000, size / 1000);
		this.setPreferredSize(new Dimension((int) size, (int) size));
	}

	/**
	 * This method adds views for all components in the simulation. The views
	 * are listeners of the particular components.
	 */
	public void paintSimulationComponents() {

		backgroundPanel = new BackgroundPanel(transform);
		backgroundPanel.setLocation(0, 0);
		this.add(backgroundPanel, new Integer(1));

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

		/** Set scale */
		setSimulationToScale();
	}

	/**
	 * Return change listener for the view menu.
	 * 
	 * @return the changeListener
	 */
	public ActionListener getViewMenuListener() {
		return backgroundPanel;
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
	public void componentResized(ComponentEvent arg0) {

		/*
		 * double size = Math.max(mainFrame.getWidth() - 35,
		 * mainFrame.getHeight() - 145);
		 */

		/** Set scale relative to the frame size */
		setSimulationToScale();

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
