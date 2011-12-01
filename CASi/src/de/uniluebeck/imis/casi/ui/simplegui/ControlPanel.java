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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import de.uniluebeck.imis.casi.CASi;
import de.uniluebeck.imis.casi.simulation.engine.SimulationClock;

/**
 * @author Moritz Buerger
 *
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener {

	private static final Logger log = Logger.getLogger(
			ControlPanel.class.getName());
	
	public ControlPanel() {
		
		this.setBorder(BorderFactory.createTitledBorder("Control field:"));
		
		/** Set Layout */
		this.setLayout(new FlowLayout());
		
		/** Set the components */
		this.setComponents();
		
	}
	
	private void setComponents() {
		
		/** Set start button */
		JButton startButton = new JButton("Pause");
		startButton.setActionCommand("pause");
		startButton.addActionListener(this);
		this.add(startButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		/** CHeck, if start button was pressed */
		if(arg0.getActionCommand().equals("pause")) {
			
			/** Get start button */
			JButton startButton = (JButton)arg0.getSource();
			
			/** If simulation is running */
			if(!SimulationClock.getInstance().isPaused()) {
				
				log.info("Pause simulation");
				
				/** Set simulation clock paused */
				SimulationClock.getInstance().setPaused(true);
				
				/** Change text of button to "Resume"*/
				startButton.setText("Resume");
				
				/** If simulation is paused */
			} else if(SimulationClock.getInstance().isPaused()) {
				
				log.info("Resume simulation");
				
				/** Set simulation clock started */
				SimulationClock.getInstance().setPaused(false);
			
				/** Change text of button to "Pause"*/
				startButton.setText("Pause");
			}
			
		}
		
	}
}
