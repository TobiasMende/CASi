/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2012 2012  Moritz Bürger, Marvin Frick, Tobias Mende
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButtonMenuItem;

import de.uniluebeck.imis.casi.CASi;

/**
 * This class saves information about, what is shown in the simple GUI. Is is a
 * {@link ActionListener} of the {@link ViewMenu}.
 * 
 * @author Moritz Bürger
 * 
 */
public class ViewSettings implements ActionListener {

	/** Boolean to adjust the information showed in the GUI */
	private boolean paintDoorLabels, paintSensorLabels, paintRoomLabels,
			paintSensorMonitoringArea, paintDoorCentralPoints,
			paintRoomCentralPoints, paintSensors;

	private SimulationPanel simulationPanel;

	/**
	 * The {@link ViewSettings} needs the {@link SimulationPanel} to repaint the
	 * components.
	 * 
	 * @param simulationPanel
	 */
	public ViewSettings(SimulationPanel simulationPanel) {

		this.simulationPanel = simulationPanel;

		paintDoorLabels = CASi.DEV_MODE;
		paintSensorLabels = CASi.DEV_MODE;
		paintRoomLabels = CASi.DEV_MODE;
		paintSensorMonitoringArea = CASi.DEV_MODE;
		paintDoorCentralPoints = CASi.DEV_MODE;
		paintRoomCentralPoints = CASi.DEV_MODE;
		paintSensors = true;

	}

	/**
	 * Sets the booleans according to the selection.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		JRadioButtonMenuItem clickedItem = (JRadioButtonMenuItem) arg0
				.getSource();

		if (arg0.getActionCommand().equals("paintDoorLabels")) {

			paintDoorLabels = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintSensorLabels")) {

			paintSensorLabels = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintRoomLabels")) {

			paintRoomLabels = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintSensorMonitoringArea")) {

			paintSensorMonitoringArea = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintDoorCentralPoints")) {

			paintDoorCentralPoints = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintRoomCentralPoints")) {

			paintRoomCentralPoints = clickedItem.isSelected();

		} else if (arg0.getActionCommand().equals("paintSensors")) {

			paintSensors = clickedItem.isSelected();
		}

		simulationPanel.paintScaledSimulation();
	}

	/**
	 * @return the paintDoorLabels
	 */
	public boolean isPaintDoorLabels() {
		return paintDoorLabels;
	}

	/**
	 * @return the paintSensorLabels
	 */
	public boolean isPaintSensorLabels() {
		return paintSensorLabels;
	}

	/**
	 * @return the paintRoomLabels
	 */
	public boolean isPaintRoomLabels() {
		return paintRoomLabels;
	}

	/**
	 * @return the paintSensorMonitoringArea
	 */
	public boolean isPaintSensorMonitoringArea() {
		return paintSensorMonitoringArea;
	}

	/**
	 * @return the paintDoorCentralPoints
	 */
	public boolean isPaintDoorCentralPoints() {
		return paintDoorCentralPoints;
	}

	/**
	 * @return the paintRoomCentralPoints
	 */
	public boolean isPaintRoomCentralPoints() {
		return paintRoomCentralPoints;
	}

	/**
	 * @return the paintSensors
	 */
	public boolean isPaintSensors() {
		return paintSensors;
	}

}
