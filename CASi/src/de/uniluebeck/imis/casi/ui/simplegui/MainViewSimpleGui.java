package de.uniluebeck.imis.casi.ui.simplegui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

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
public class MainViewSimpleGui extends JFrame implements IMainView {
	
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
		
		/** New SimulationPanel */
		simPanel = new SimulationPanel();
		
		this.add(simPanel,BorderLayout.CENTER);
		
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

}
