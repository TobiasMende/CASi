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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;

/**
 * @author Moritz Buerger
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
	 * Sets the combo box list.
	 */
	public void setInformationComboBox() {

		String[] names;

		try {

			int count = SimulationEngine.getInstance().getWorld().getAgents()
					.size()
					+ SimulationEngine.getInstance().getWorld()
							.getInteractionComponents().size();

			names = new String[count];
			int index = 0;

			agentMap = new ArrayList<Agent>();

			for (Agent agent : SimulationEngine.getInstance().getWorld()
					.getAgents()) {

				names[index] = agent.getName();
				agentMap.add(agent);
				index++;

			}

			interactionCompMap = new ArrayList<AbstractInteractionComponent>();

			for (AbstractInteractionComponent interactionComp : SimulationEngine
					.getInstance().getWorld().getInteractionComponents()) {

				names[index] = interactionComp.getIdentifier() + "::"
						+ interactionComp.getType();
				interactionCompMap.add(interactionComp);
				index++;

			}

		} catch (IllegalAccessException e) {

			log.warning("Exception: " + e.toString());
			names = new String[0];
		}

		selectComponentBox = new JComboBox(names);
		selectComponentBox.setBorder(BorderFactory
				.createTitledBorder("Select component:"));
		selectComponentBox.addActionListener(this);
		
		/*
		 * see here: 
		 * http://www.java2s.com/Code/Java/Swing-Components/BlockComboBoxExample.htm
		 */
		
		add(selectComponentBox, BorderLayout.NORTH);

		/** Add the information panel as listener on the simulation clock */
		SimulationClock.getInstance().addListener(this);

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
		int selectedIndex_I = selectedIndex_A - agentMap.size();

		// if the selected index is an agent
		if (selectedIndex_I < 0) {

			informationTextArea.setText(getAgentInformation(agentMap
					.get(selectedIndex_A)));
			
		} else {

			informationTextArea
					.setText(getInteractionComponentInformation(interactionCompMap
							.get(selectedIndex_I)));
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

	public void setSelectedAgent(Agent agent) {
		
		int index = agentMap.indexOf(agent);
		selectComponentBox.setSelectedIndex(index);
		
		
	}

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
