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

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import de.uniluebeck.imis.casi.CASi;

/**
 * This class extends JMenu. This menu contains options to show or hide several
 * components of the simulation.
 * 
 * @author Moritz Bürger
 * 
 */
@SuppressWarnings("serial")
public class ViewMenu extends JMenu {

	private JRadioButtonMenuItem paintDoorLabelsItem, paintSensorLabelsItem,
			paintRoomLabelsItem, paintSensorMonitoringAreaItem,
			paintDoorCentralPointsItem, paintRoomCentralPointsItem, paintSensorsItem;

	/**
	 * The constructor calls the super contructor and set up the components of
	 * the view menu.
	 */
	public ViewMenu() {

		super("View");

		paintDoorLabelsItem = new JRadioButtonMenuItem("Show door labels");
		paintDoorLabelsItem.setSelected(CASi.DEV_MODE);
		paintDoorLabelsItem.setActionCommand("paintDoorLabels");
		add(paintDoorLabelsItem);

		paintSensorLabelsItem = new JRadioButtonMenuItem(
				"Show sensor/actuator labels");
		paintSensorLabelsItem.setSelected(CASi.DEV_MODE);
		paintSensorLabelsItem.setActionCommand("paintSensorLabels");
		add(paintSensorLabelsItem);

		paintRoomLabelsItem = new JRadioButtonMenuItem("Show room labels");
		paintRoomLabelsItem.setSelected(CASi.DEV_MODE);
		paintRoomLabelsItem.setActionCommand("paintRoomLabels");
		add(paintRoomLabelsItem);

		paintSensorMonitoringAreaItem = new JRadioButtonMenuItem(
				"Show sensor/actuator area");
		paintSensorMonitoringAreaItem.setSelected(CASi.DEV_MODE);
		paintSensorMonitoringAreaItem
				.setActionCommand("paintSensorMonitoringArea");
		add(paintSensorMonitoringAreaItem);

		paintDoorCentralPointsItem = new JRadioButtonMenuItem(
				"Show door points");
		paintDoorCentralPointsItem.setSelected(CASi.DEV_MODE);
		paintDoorCentralPointsItem.setActionCommand("paintDoorCentralPoints");
		add(paintDoorCentralPointsItem);

		paintRoomCentralPointsItem = new JRadioButtonMenuItem(
				"Show room points");
		paintRoomCentralPointsItem.setSelected(CASi.DEV_MODE);
		paintRoomCentralPointsItem.setActionCommand("paintRoomCentralPoints");
		add(paintRoomCentralPointsItem);
		
		paintSensorsItem = new JRadioButtonMenuItem(
				"Show sensors/actuators");
		paintSensorsItem.setSelected(CASi.DEV_MODE);
		paintSensorsItem.setActionCommand("paintSensors");
		add(paintSensorsItem);
	}

	/**
	 * Sets the action listener, the background panel of the simulation panel.
	 * 
	 * @param actionlistener
	 *            the action listener to set
	 */
	public void setViewlistener(ActionListener actionlistener) {

		paintDoorLabelsItem.addActionListener(actionlistener);
		paintSensorLabelsItem.addActionListener(actionlistener);
		paintRoomLabelsItem.addActionListener(actionlistener);
		paintSensorMonitoringAreaItem.addActionListener(actionlistener);
		paintDoorCentralPointsItem.addActionListener(actionlistener);
		paintRoomCentralPointsItem.addActionListener(actionlistener);
		paintSensorsItem.addActionListener(actionlistener);
	}

}
