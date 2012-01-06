/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012 2012  Moritz Buerger, Marvin Frick, Tobias Mende
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;

/**
 * The InformationPanel is a JPanel. It allows to select an agent or interaction
 * component of the simulation and shows further information of it.
 * 
 * @author Moritz Bürger
 * 
 */
@SuppressWarnings("serial")
public class InformationPanel extends JPanel implements ActionListener,
		ISimulationClockListener {

	private static final Logger log = Logger.getLogger(InformationPanel.class
			.getName());

	private JComboBox selectComponentBox;
	private JTextArea informationTextArea;

	private ArrayList<Agent> agentMap;
	private ArrayList<AbstractInteractionComponent> interactionCompMap;

	/**
	 * The constructor sets layout and components.
	 */
	public InformationPanel() {

		/** Set layout to FlowLayout */
		this.setLayout(new BorderLayout());

		/** Set the components */
		this.setComponents();

		/** Set preferred size */
		this.setPreferredSize(new Dimension(250, 0));
	}

	/**
	 * Sets components of the information panel.
	 */
	private void setComponents() {

		informationTextArea = new JTextArea();
		informationTextArea.setBorder(BorderFactory
				.createTitledBorder("Information:"));
		// informationTextArea.setEnabled(false);
		informationTextArea.setEditable(false);
		add(informationTextArea, BorderLayout.CENTER);
	}

	/**
	 * This method sets the entries of the JComboBox.
	 */
	public void setInformationComboBox() {

		try {

			agentMap = new ArrayList<Agent>();

			for (Agent agent : SimulationEngine.getInstance().getWorld()
					.getAgents()) {

				agentMap.add(agent);

			}

			interactionCompMap = new ArrayList<AbstractInteractionComponent>();

			for (AbstractInteractionComponent interactionComp : SimulationEngine
					.getInstance().getWorld().getInteractionComponents()) {

				interactionCompMap.add(interactionComp);

			}

		} catch (IllegalAccessException e) {

			log.warning("Exception: " + e.toString());
		}

		selectComponentBox = new JComboBox(getVectorData());
		selectComponentBox.setBorder(BorderFactory
				.createTitledBorder("Select component:"));
		selectComponentBox.addActionListener(this);
		selectComponentBox.setRenderer(new ComboBoxRenderer());

		/*
		 * see here:
		 * http://www.java2s.com/Code/Java/Swing-Components/BlockComboBoxExample
		 * .htm
		 */

		add(selectComponentBox, BorderLayout.NORTH);

		/** Add the information panel as listener on the simulation clock */
		SimulationClock.getInstance().addListener(this);

	}

	private Vector<String> getVectorData() {

		Vector<String> data = new Vector<String>();

		/** Add agent to vector */
		for (Agent agent : agentMap) {

			data.addElement(agent.getName());
		}

		/** Add separator to vector */
		data.addElement(ComboBoxRenderer.SEPERATOR);

		/** Add interaction components to vector */
		for (AbstractInteractionComponent interactionComp : interactionCompMap) {

			data.addElement(interactionComp.getIdentifier() + "::"
					+ interactionComp.getType());
		}

		return data;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				setInformation();
			}
		});

	}

	/**
	 * This method updates the information of the selected component.
	 */
	private void setInformation() {

		int selectedIndex_A = this.selectComponentBox.getSelectedIndex();
		int selectedIndex_I = selectedIndex_A - agentMap.size() - 1;

		// if the selected index is an agent
		if (selectedIndex_I < -1) {

			informationTextArea.setText(getAgentInformation(agentMap
					.get(selectedIndex_A)));

			// if the selected index is an interaction component
		} else if (selectedIndex_I > -1) {

			informationTextArea
					.setText(getInteractionComponentInformation(interactionCompMap
							.get(selectedIndex_I)));

			// if the separator is selected
		} else {

			// do nothing
		}

	}

	/**
	 * This method returns the information of an agent as String.
	 * 
	 * @param agent
	 *            the agent
	 * @return the information
	 */
	private String getAgentInformation(Agent agent) {

		String info;

		if (agent.getCurrentAction() != null) {

			info = "Name: " + agent.getName() + "\n" + "Indentifier: "
					+ agent.getIdentifier() + "\n" + "Status: "
					+ agent.getState() + "\n" + "Current action: \n"
					+ "   - Name: "
					+ agent.getCurrentAction().getClass().getSimpleName()
					+ "\n" + "   - Type: " + agent.getCurrentAction().getType()
					+ "\n" + "   - State: "
					+ agent.getCurrentAction().getState() + "\n"
					+ "   - Duration: "
					+ agent.getCurrentAction().getDuration() + " minutes\n"
					+ "Current position: " + agent.getCurrentPosition();
		} else {

			info = "Name: " + agent.getName() + "\n" + "Indentifier: "
					+ agent.getIdentifier() + "\n" + "Status: "
					+ agent.getState() + "\n" + "Current action: ---\n"
					+ "Current position: " + agent.getCurrentPosition();

		}

		return info;
	}

	/**
	 * This method returns the information of an interaction component as
	 * String.
	 * 
	 * @param interactionComp
	 *            the interaction component
	 * @return the information
	 */
	private String getInteractionComponentInformation(
			AbstractInteractionComponent interactionComp) {

		String info;

		info = "Indentifier: " + interactionComp.getIdentifier() + "\n"
				+ "Type: " + interactionComp.getType() + "\n" + "Position: "
				+ interactionComp.getCurrentPosition() + "\n" + "Wearable: "
				+ interactionComp.isWearable() + "\n" + "Agent: "
				+ interactionComp.getAgent() + "\n" + "Current value: "
				+ interactionComp.getHumanReadableValue();

		return info;
	}

	/**
	 * This method sets the given agent as selected, if it is in the list.
	 * 
	 * @param agent
	 *            the agent
	 */
	public void setSelectedAgent(Agent agent) {

		/* get index of agent in list */
		int index = agentMap.indexOf(agent);

		/* set index of combobox */
		if (index != -1) {
			selectComponentBox.setSelectedIndex(index);
		}

	}

	/**
	 * This method sets the given interaction component as selected, if it is in
	 * the list.
	 * 
	 * @param interactionComp
	 *            the interaction component
	 */
	public void setSelectedInteractionComponent(
			AbstractInteractionComponent interactionComp) {

		/* get index of interaction component in list */
		int index = interactionCompMap.indexOf(interactionComp);

		/* set index of combobox */
		if (index != -1) {
			selectComponentBox.setSelectedIndex(agentMap.size() + 1 + index);
		}
	}

	/**
	 * Sets information new, if time changed.
	 */
	@Override
	public void timeChanged(SimulationTime newTime) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				setInformation();
			}
		});

	}

	@Override
	public void simulationPaused(boolean pause) {

	}

	@Override
	public void simulationStopped() {

	}

	@Override
	public void simulationStarted() {

	}
}
