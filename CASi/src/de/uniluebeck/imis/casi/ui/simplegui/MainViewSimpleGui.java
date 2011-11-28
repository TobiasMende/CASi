package de.uniluebeck.imis.casi.ui.simplegui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.controller.IUIController;
import de.uniluebeck.imis.casi.ui.IMainView;

/**
 * This class extends JFrame and shows the main window of the simple GUI.
 * 
 * @author Moritz Buerger
 *
 */
@SuppressWarnings("serial")
public class MainViewSimpleGui extends JFrame implements IMainView, ActionListener {
	
	private IUIController uicontroller;
	private SimulationPanel simPanel;
	
	public static final int WIDTH = 1020;
	public static final int HEIGHT = 650;
	
	public MainViewSimpleGui() {
		
		/** Set size of simple GUI */
		this.setSize(MainViewSimpleGui.WIDTH, MainViewSimpleGui.HEIGHT);
		/** Set default close operation to 'exit on close' */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/** Set the location of the frame in the center of screen */
		this.setLocationRelativeTo(null);
		/** Set layout to 'null'*/
		this.setLayout(new BorderLayout()); // BorderLayout for testing
		/** Set title of the frame */
		this.setTitle("CASi (MACK Simulator)");
		
		/** Set up components */
		this.setComponents();
	}
	
	/**
	 * Sets up the components of the simple GUI main frame.
	 */
	private void setComponents() {
		
		/** Set menu bar */
		JMenuBar menuBar = new JMenuBar();
		JMenu mainMenu = new JMenu("Menu");
		
		/** Configure save item */
		JMenuItem stopItem = new JMenuItem("Stop Simulation");
		stopItem.setActionCommand("stop");
		stopItem.addActionListener(this);
		mainMenu.add(stopItem);
		mainMenu.addSeparator();
		
		/** Configure save item */
		JMenuItem saveItem = new JMenuItem("Save...");
		saveItem.setActionCommand("save");
		saveItem.addActionListener(this);
		mainMenu.add(saveItem);
		
		/** Configure load item */
		JMenuItem loadItem = new JMenuItem("Load...");
		loadItem.setActionCommand("load");
		loadItem.addActionListener(this);
		mainMenu.add(loadItem);
		mainMenu.addSeparator();
		
		/** Configure close item */
		JMenuItem closeItem = new JMenuItem("Exit");
		closeItem.setActionCommand("close");
		closeItem.addActionListener(this);
		mainMenu.add(closeItem);
		
		/** Add menu to menu bar */
		menuBar.add(mainMenu);
		
		/** New SimulationPanel */
		simPanel = new SimulationPanel();
		
		this.add(simPanel,BorderLayout.CENTER);
		this.add(menuBar,BorderLayout.NORTH);
	}

	@Override
	public void setUIController(IUIController controller) {

		this.uicontroller = controller;
		
	}
	
	/**
	 * Sets the simple GUI visible.
	 */
	@Override
	public void showUi() {
		
		this.setVisible(true);
		CASi.SIM_LOG.info("Show simple GUI");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand().equals("stop")) {
			
			/*
			 * TODO Stop option
			 */
			
		} else if(arg0.getActionCommand().equals("save")) {
			
			/*
			 * TODO Save option
			 */
			
		} else if(arg0.getActionCommand().equals("load")) {
		
			
			/*
			 * TODO Load option
			 */
			
		} else if(arg0.getActionCommand().equals("close")) {
			
			/*
			 * TODO Want to save?
			 */
			
			System.exit(0);
		}
		
	}

}
