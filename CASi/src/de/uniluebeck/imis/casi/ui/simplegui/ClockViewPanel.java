/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2011 2012  Moritz B�rger, Marvin Frick, Tobias Mende
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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.engine.ISimulationClockListener;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;

/**
 * This class extends JPanel and shows the simulation time in the simple GUI.
 * 
 * @author Moritz Buerger
 *
 */
@SuppressWarnings("serial")
public class ClockViewPanel extends JPanel implements ISimulationClockListener, ChangeListener {
	
	private JLabel timeLabel;
	private JSlider slider;
	
	public ClockViewPanel() {
		
		/** Add the clock panel as listener on the simulation clock */
		SimulationClock.getInstance().addListener(this);
		
		/** Set layout to FlowLayout */
		this.setLayout(new GridLayout(1,0));
		
		/** Set the components */
		this.setComponents();
		
		/** Set preferred size */
		this.setPreferredSize(new Dimension(0, 65));
	}
	
	/**
	 * This method sets the components of the ClockViewPanel (JLabel showing the
	 * date and time, JSlider scaling the simulation time).
	 */
	private void setComponents() {
		
		/** JLabel showing the simulation time */
		this.timeLabel = new JLabel();
		this.timeLabel.setFont(new Font("sans",Font.BOLD,16));
		this.timeLabel.setBorder(BorderFactory.createTitledBorder("Date/Time:"));
		this.add(this.timeLabel);
		
		/** JSlider to set the speed*/
		this.slider = new JSlider(JSlider.HORIZONTAL,
				SimulationClock.MINIMUM_SCALE_FACTOR,
				SimulationClock.MAXIMUM_SCALE_FACTOR,
				-SimulationClock.DEFAULT_SCALE_FACTOR + 2100);
		this.slider.setMajorTickSpacing(200);
		this.slider.setMinorTickSpacing(100);
		this.slider.setSnapToTicks(true);
		this.slider.setPaintTicks(true);
		this.slider.addChangeListener(this);
		this.slider.setBorder(BorderFactory.createTitledBorder("Time scaler:"));
		this.add(this.slider);
		
		/** Add control panel */
		ControlPanel controlPanel = new ControlPanel();
		this.add(controlPanel);
	}

	@Override
	public void timeChanged(SimulationTime newTime) {

		this.timeLabel.setText(newTime.getLocalizedDate()+" - "+newTime.getLocalizedTime());
		
	}

	@Override
	public void simulationPaused(boolean pause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulationStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulationStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		
		/** Calculate the new scale factor */
		int newScaleFactor = this.slider.getValue();
		newScaleFactor = -newScaleFactor + 2100;
		
		/** Only change at values that are multiple of 100 */
		if(newScaleFactor % 100 == 0) {
			
			/** Only change if the value has changed */
			if(SimulationClock.getInstance().getScaleFactor() != 
				newScaleFactor) {
				
				CASi.SIM_LOG.info("Set simulation delay to: "+newScaleFactor);
				/** Set new value to SimulationClock */
				SimulationClock.getInstance().setScaleFactor(newScaleFactor);
				
			}
		}
		
		
	}

}
