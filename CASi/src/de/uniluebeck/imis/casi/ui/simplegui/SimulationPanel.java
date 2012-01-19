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
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;

/**
 * This class extends JLayeredPanel. It contains a background panel, that
 * contains all static components of the simulation and views for agents and
 * sensors. The simulation resizes/scales depending on the size of the main
 * frame.
 * 
 * @author Moritz Bürger
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

	private double worldSizeX;
	private double worldSizeY;

	private ArrayList<ComponentView> simulationComponents = new ArrayList<ComponentView>();

	private ViewSettings viewSettings;

	private Set<Room> roomPoints;

	/**
	 * Constructor of the simulation panel sets the preferred size.
	 */
	public SimulationPanel() {

		transform = new AffineTransform();

		viewSettings = new ViewSettings(this);

		this.setLayout(null);

		/* Draw Border */
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	/**
	 * This method sets the affine transform and the size of the simulation
	 * panel to the right scale, depending on the frame size.
	 * 
	 */
	public void setSimulationToScale() {

		Container parent = this.getParent();
		double size = Math.min(parent.getWidth(), parent.getHeight());

		this.transform.setToScale((size - 40) / worldSizeX, (size - 40)
				/ worldSizeY);

		this.setBounds(20, 20, (int) size - 30, (int) size - 30);
		this.backgroundPanel.setBounds(0, 0, (int) size, (int) size);

		for (ComponentView componentView : simulationComponents) {

			componentView.setTransformedPosition();
		}

		for (Room room : roomPoints) {

			paintComponentsInCircle(room.getCentralPoint(), 8,
					getSimulationComponentesIn(room.getCentralPoint()));

		}

		SimulationPanel.this.invalidate();
		backgroundPanel.repaint();

	}

	/**
	 * This method adds views for all components in the simulation. The views
	 * are listeners of the particular components.
	 * 
	 * @param infoPanel
	 *            the InformationPanel
	 */
	public void paintSimulationComponents(InformationPanel infoPanel) {

		worldSizeX = SimulationEngine.getInstance().getWorld()
				.getSimulationDimension().getWidth();

		worldSizeY = SimulationEngine.getInstance().getWorld()
				.getSimulationDimension().getHeight();

		backgroundPanel = new BackgroundPanel(transform, viewSettings);
		backgroundPanel.setLocation(0, 0);
		backgroundPanel.setInformationPanel(infoPanel);
		this.add(backgroundPanel, new Integer(1));

		try {

			/** At first add views for the agents */
			for (Agent agent : SimulationEngine.getInstance().getWorld()
					.getAgents()) {

				AgentView agentView = new AgentView(agent, transform);
				agent.addListener(agentView);
				simulationComponents.add(agentView);
				agentView.setInformationPanel(infoPanel);
				agentView.setSimulationPanel(this);
				this.add(agentView, new Integer(3));

			}

			/** Add views for interaction components */
			for (AbstractInteractionComponent interactionComp : SimulationEngine
					.getInstance().getWorld().getInteractionComponents()) {

				InteractionComponentView interactionCompView = new InteractionComponentView(
						interactionComp, transform, viewSettings);
				simulationComponents.add(interactionCompView);
				interactionCompView.setInformationPanel(infoPanel);
				this.add(interactionCompView, new Integer(2));
			}

			this.roomPoints = SimulationEngine.getInstance().getWorld()
					.getRooms();

		} catch (IllegalAccessException e) {

			log.warning("Exception: " + e.toString());
		}
		/** Set scale */
		setSimulationToScale();
	}

	/**
	 * Returns {@link ViewSettings} for the view menu.
	 * 
	 * @return the view menu listener
	 */
	public ActionListener getViewMenuListener() {
		return viewSettings;
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

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {

	}

	@Override
	public void componentResized(ComponentEvent arg0) {

		/** Set scale relative to the frame size */
		setSimulationToScale();

	}

	@Override
	public void componentShown(ComponentEvent arg0) {

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	/**
	 * Returns all the simulation components.
	 * 
	 * @return the simulation components
	 */
	public List<ComponentView> getSimulationComponents() {

		return simulationComponents;
	}

	/**
	 * Returns all the simulation components in this point, if it is a central
	 * point of a room.
	 * 
	 * @param point
	 *            the point
	 * @return the simulation components
	 */
	public LinkedList<ComponentView> getSimulationComponentesIn(Point2D point) {

		LinkedList<ComponentView> list = new LinkedList<ComponentView>();

		if(!isNearRoomPoint(point)) {
			
			return list;
		}
		
		Rectangle rect = new Rectangle((int) point.getX() - 3,
				(int) point.getY() - 3, 6, 6);

		for (ComponentView componentView : simulationComponents) {

			if (rect.contains(componentView.getSimulationPosition())) {

				list.add(componentView);
			}

		}

		return list;
	}

	public void paintComponentsInCircle(Point2D point, int size,
			LinkedList<ComponentView> list) {

		Point2D centerPoint = new Point2D.Double(transform.getScaleX()
				* point.getX(), transform.getScaleY() * point.getY());

		int numberOfComponents = list.size();
		double radius = transform.getDeterminant() * size * numberOfComponents
				/ (2 * Math.PI);
		double angle = 2 * Math.PI / numberOfComponents;
		double newAngle = 0;

		for (ComponentView componentView : list) {

			int scaledX = (int) ((radius * Math.cos(newAngle) - 2)
					* transform.getScaleX() + centerPoint.getX());
			int scaledY = (int) ((radius * Math.sin(newAngle) - 2)
					* transform.getScaleY() + centerPoint.getY());

			componentView.setBounds(scaledX, scaledY,
					(int) (8 * transform.getScaleX()),
					(int) (8 * transform.getScaleY()));

			newAngle = newAngle + angle;
		}

	}

	/**
	 * This method checks, if the given point is in the near of a room point.
	 * 
	 * @param point
	 *            the point
	 * @return <code>true</code>, if the point is near a room point, else <code>false</code>.
	 */
	public boolean isNearRoomPoint(Point2D point) {

		boolean isRoomCentral = false;
		Rectangle rect = new Rectangle((int) point.getX() - 3,
				(int) point.getY() - 3, 6, 6);

		for (Room room : roomPoints) {

			if (rect.contains(room.getCentralPoint())) {

				isRoomCentral = true;
			}

		}

		return isRoomCentral;
	}

}
