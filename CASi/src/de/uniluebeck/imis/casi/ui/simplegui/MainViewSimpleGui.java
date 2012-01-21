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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.controller.IUIController;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.ui.IMainView;

/**
 * This class is a JFrame and shows the main window of the simple GUI.
 * 
 * @author Moritz Bürger
 * 
 */
@SuppressWarnings("serial")
public class MainViewSimpleGui extends JFrame implements IMainView,
		ActionListener, ComponentListener {

	private static final Logger log = Logger.getLogger(MainViewSimpleGui.class
			.getName());

	private IUIController uicontroller;
	private SimulationPanel simPanel;
	private ViewMenu viewMenu;
	private InformationPanel informationPanel;

	private JSplitPane splitPane;

	public static final int WIDTH = 1020;
	public static final int HEIGHT = 650;

	@Override
	public void setUIController(IUIController controller) {

		this.uicontroller = controller;

	}

	/**
	 * Sets the simple GUI visible.
	 */
	@Override
	public void showUi() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				showUiInternal();
			}
		});
	}

	/**
	 * Really shows the GUI after using invoke later
	 */
	private void showUiInternal() {
		/** Set size of simple GUI */
		this.setSize(MainViewSimpleGui.WIDTH, MainViewSimpleGui.HEIGHT);
		/** Set minimum size */
		this.setMinimumSize(new Dimension(500, 300));
		/** Set default close operation to 'exit on close' */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/** Set the location of the frame in the center of screen */
		this.setLocationRelativeTo(null);
		/** Set layout to 'null' */
		this.setLayout(new BorderLayout());
		/** Set title of the frame */
		this.setTitle("CASi (MACK Simulator)");

		/** Set up components */
		this.setComponents();

		/** Set frame visible, paint components of the simulation */
		this.setVisible(true);
		simPanel.setVisible(true);
		this.simPanel.paintSimulationComponents(this.informationPanel);

		/** Set agents, sensor and actuators in the information combo box */
		this.informationPanel.setInformationComboBox(simPanel
				.getSimulationComponents());

		/**
		 * Set the listener (background panel of simulation panel) of the view
		 * menu
		 */
		this.viewMenu.setViewlistener(simPanel.getViewMenuListener());
		simPanel.invalidate();

		log.info("Show simple GUI");
	}

	/**
	 * Sets up the components of the simple GUI main frame.
	 */
	private void setComponents() {

		/** Set menu bar */
		JMenuBar menuBar = new JMenuBar();
		JMenu mainMenu = new JMenu("Menu");
		menuBar.setBackground(ColorScheme.BACKGROUND_GUI);

		/** Configure save item */
		// for now we dont have these things!
//		JMenuItem stopItem = new JMenuItem("Stop Simulation");
//		stopItem.setActionCommand("stop");
//		stopItem.addActionListener(this);
//		mainMenu.add(stopItem);
//		mainMenu.addSeparator();

		/** Configure close item */
		JMenuItem closeItem = new JMenuItem("Exit");
		closeItem.setActionCommand("close");
		closeItem.addActionListener(this);
		mainMenu.add(closeItem);

		/** Add menu to menu bar */
		menuBar.add(mainMenu);

		/** Add view to menu bar */
		viewMenu = new ViewMenu();
		menuBar.add(viewMenu);

		/** New SimulationPanel */
		simPanel = new SimulationPanel();
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);
		centerPanel.add(simPanel);
		centerPanel.setBorder(BorderFactory.createTitledBorder("Simulation"));
		centerPanel.setBackground(ColorScheme.BACKGROUND);

		centerPanel.addComponentListener(simPanel);

		/** New ClockViewPanel */
		ClockViewPanel clockViewPanel = new ClockViewPanel();

		/** New InformationPanel */
		informationPanel = new InformationPanel();

		splitPane = new JSplitPane();
		splitPane.setLeftComponent(centerPanel);
		splitPane.setRightComponent(informationPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(this.getWidth() - 317);
		splitPane.setBackground(ColorScheme.BACKGROUND_GUI);

		this.add(splitPane, BorderLayout.CENTER);
		this.add(menuBar, BorderLayout.NORTH);
		this.add(clockViewPanel, BorderLayout.SOUTH);

		this.addComponentListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		/**
		 * Set actions of the main menu
		 */
		if (arg0.getActionCommand().equals("close")) {

			/*
			 * sorry, there is no "save".
			 */

			System.exit(0);
		}

	}

	@Override
	public void componentHidden(ComponentEvent e) {

	}

	@Override
	public void componentMoved(ComponentEvent e) {

	}

	@Override
	public void componentResized(ComponentEvent e) {

		splitPane.setDividerLocation(this.getWidth() - 317);

	}

	@Override
	public void componentShown(ComponentEvent e) {

	}

}
