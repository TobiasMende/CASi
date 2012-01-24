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
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.engine.SimulationEngine;
import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;
import de.uniluebeck.imis.casi.simulation.model.Agent;
import de.uniluebeck.imis.casi.simulation.model.Door;
import de.uniluebeck.imis.casi.simulation.model.Room;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.actionHandling.AbstractAction;

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

	/** ComboBox to select the component */
	private JComboBox selectComponentBox;

	/** TextArea to show information of the selected component */
	private JTextArea informationTextArea;

	/** TextArea to show information of the selected room */
	private JTextArea informationTextAreaRoom;

	/** List of agent in the simulation */
	private ArrayList<Agent> agentList;

	/** List of sensors and actuators in the simulation */
	private ArrayList<AbstractInteractionComponent> interactionCompList;

	/** Saves the last clicked room */
	private Room shownRoom;

	/** List of all component views */
	private List<ComponentView> componentViews;

	/** SplitPane between the TextAreas */
	private JSplitPane splitPane;

	/**
	 * The constructor sets layout and components.
	 */
	public InformationPanel() {

		/* Set layout to FlowLayout */
		this.setLayout(new BorderLayout());

		/* Set the components */
		this.setComponents();

		/* Set preferred size */
		this.setPreferredSize(new Dimension(250, 0));
	}

	/**
	 * Sets components of the information panel.
	 */
	private void setComponents() {

		informationTextArea = new JTextArea();
		informationTextArea.setBorder(BorderFactory
				.createTitledBorder("Information:"));
		informationTextArea.setEditable(false);
		informationTextArea.setBackground(ColorScheme.BACKGROUND);

		JScrollPane scrollPane = new JScrollPane(informationTextArea);

		informationTextAreaRoom = new JTextArea();
		informationTextAreaRoom.setBorder(BorderFactory
				.createTitledBorder("Room information:"));
		informationTextAreaRoom.setEditable(false);
		informationTextAreaRoom.setBackground(ColorScheme.BACKGROUND);

		JScrollPane scrollPaneRoom = new JScrollPane(informationTextAreaRoom);

		this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane,
				scrollPaneRoom);
		splitPane.setDividerLocation(this.getHeight() / 2);

		add(splitPane, BorderLayout.CENTER);
	}

	/**
	 * This method sets the entries of the JComboBox.
	 */
	public void setInformationComboBox(List<ComponentView> componentViews) {

		splitPane.setDividerLocation(this.getHeight() / 2);

		this.componentViews = componentViews;

		try {

			agentList = new ArrayList<Agent>();

			for (Agent agent : SimulationEngine.getInstance().getWorld()
					.getAgents()) {

				agentList.add(agent);

			}

			interactionCompList = new ArrayList<AbstractInteractionComponent>();

			for (AbstractInteractionComponent interactionComp : SimulationEngine
					.getInstance().getWorld().getInteractionComponents()) {

				interactionCompList.add(interactionComp);

			}

		} catch (IllegalAccessException e) {

			log.warning("Exception: " + e.toString());
		}

		selectComponentBox = new JComboBox(getVectorData());
		selectComponentBox.setBorder(BorderFactory
				.createTitledBorder("Select component:"));
		selectComponentBox.setBackground(ColorScheme.BACKGROUND_GUI);
		selectComponentBox.addActionListener(this);
		selectComponentBox.setRenderer(new ComboBoxRenderer());

		/*
		 * see here:
		 * http://www.java2s.com/Code/Java/Swing-Components/BlockComboBoxExample
		 * .htm
		 */

		add(selectComponentBox, BorderLayout.NORTH);

		/* Add the information panel as listener on the simulation clock */
		SimulationClock.getInstance().addListener(this);

	}

	/**
	 * This method returns the data for the selection box and adds a separator
	 * between the agents and sensors/actuators.
	 * 
	 * @return data as {@link Vector} of String.
	 */
	private Vector<String> getVectorData() {

		Vector<String> data = new Vector<String>();

		/* Add agent to vector */
		for (Agent agent : agentList) {

			data.addElement(agent.getName());
		}

		/* Add separator to vector */
		data.addElement(ComboBoxRenderer.SEPERATOR);

		/* Add interaction components to vector */
		for (AbstractInteractionComponent interactionComp : interactionCompList) {

			data.addElement(interactionComp.getIdentifier() + "::"
					+ interactionComp.getType());
		}

		return data;
	}

	/**
	 * Sets the room, that is shown.
	 * 
	 * @param room
	 *            the room
	 */
	public void showRoomInformationOf(Room room) {

		this.shownRoom = room;
		setInformation();
	}

	/**
	 * Sets the information, if selection box changes.
	 */
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
		int selectedIndex_I = selectedIndex_A - agentList.size() - 1;

		// if the selected index is an agent
		if (selectedIndex_I < -1) {

			String newInfo = getAgentInformation(agentList.get(selectedIndex_A));

			if (!informationTextArea.getText().equals(newInfo)) {
				informationTextArea.setText(newInfo);

				for (ComponentView componentView : this.componentViews) {

					componentView.setSelected(agentList.get(selectedIndex_A));

				}
			}

			// if the selected index is an interaction component
		} else if (selectedIndex_I > -1) {

			String newInfo = getInteractionComponentInformation(interactionCompList
					.get(selectedIndex_I));

			if (!informationTextArea.getText().equals(newInfo)) {
				informationTextArea.setText(newInfo);

				for (ComponentView componentView : this.componentViews) {

					componentView.setSelected(interactionCompList
							.get(selectedIndex_I));

				}
			}

			// if the separator is selected
		} else {

			// do nothing
		}

		if (shownRoom != null) {

			String newInfo = getRoomInformation(shownRoom);

			if (!informationTextAreaRoom.getText().equals(newInfo)) {
				informationTextAreaRoom.setText(newInfo);
			}

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
					+ "   - Description: "
					+ agent.getCurrentAction().getInformationDescription()
					+ "\n" + "   - Duration: "
					+ agent.getCurrentAction().getDuration() + " minutes\n"
					+ "Current position: " + agent.getCurrentPosition() + "\n";

			info = info + "Action pool:\n";

			for (AbstractAction abstractAction : agent.getActionPoolCopy()) {

				info = info + "  " + abstractAction.getInformationDescription()
						+ "\n";
			}

			info = info + "Todo list:\n";

			for (AbstractAction abstractAction : agent.getTodoListCopy()) {

				info = info + "  " + abstractAction.getInformationDescription()
						+ "\n";
			}

		} else {

			info = "Name: " + agent.getName() + "\n" + "Indentifier: "
					+ agent.getIdentifier() + "\n" + "Status: "
					+ agent.getState() + "\n" + "Current action: ---\n"
					+ "Current position: " + agent.getCurrentPosition() + "\n";

			info = info + "Action pool:\n";

			for (AbstractAction abstractAction : agent.getActionPoolCopy()) {

				info = info + "  " + abstractAction.getInformationDescription()
						+ "\n";
			}

			info = info + "Todo list:\n";

			for (AbstractAction abstractAction : agent.getTodoListCopy()) {

				info = info + "  " + abstractAction.getInformationDescription()
						+ "\n";
			}
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
	 * This method returns the information of a room as String.
	 * 
	 * @param room
	 *            the room
	 * @return the information
	 */
	private String getRoomInformation(Room room) {

		String info;

		info = "Identifier: " + room.getIdentifier() + "\n"
				+ "Number of doors: " + room.getDoors().size() + "\n";

		int index = 1;
		for (Door door : room.getDoors()) {

			info = info + "   " + index + ". Door: \n" + "   Identifier: "
					+ door.getIdentifier() + "\n" + "   State: "
					+ door.getState() + "\n";

			index++;
		}

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
		int index = agentList.indexOf(agent);

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
		int index = interactionCompList.indexOf(interactionComp);

		/* set index of combobox */
		if (index != -1) {
			selectComponentBox.setSelectedIndex(agentList.size() + 1 + index);
		}
	}

	/**
	 * Sets information, if time changed.
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
