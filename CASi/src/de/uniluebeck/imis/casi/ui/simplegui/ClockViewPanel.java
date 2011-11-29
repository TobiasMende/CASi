/*  	CASi Context Awareness Simulation Software
 *   Copyright (C) 2011 2012  Moritz Bürger, Marvin Frick, Tobias Mende
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
import java.awt.FlowLayout;

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
		this.setLayout(new FlowLayout());
		
		/** JLabel showing the simulation time */
		this.timeLabel = new JLabel();
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
		this.add(this.slider);
		
		this.setPreferredSize(new Dimension(0, 35));
	}

	@Override
	public void timeChanged(SimulationTime newTime) {

		this.timeLabel.setText(newTime.toString());
		
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
