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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;

/**
 * This class represents an interaction component in the CASi simulator.
 * Interaction components are painted as rectangles, their state can be noticed
 * by the filling color.
 * 
 * @author Moritz Bürger
 * 
 */
@SuppressWarnings("serial")
public class InteractionComponentView extends ComponentView implements
		MouseListener {

	private AbstractInteractionComponent interactionComp;
	private InformationPanel infoPanel;
	private ViewSettings viewSettings;

	/**
	 * The InteractionComponentView needs the represented interaction components
	 * and the affine transform to scale the size and position.
	 * 
	 * @param interactionComp
	 * @param transform
	 */
	public InteractionComponentView(
			AbstractInteractionComponent interactionComp,
			AffineTransform transform, ViewSettings viewSettings) {

		super(interactionComp.getCentralPoint(), transform);

		this.interactionComp = interactionComp;
		
		this.viewSettings = viewSettings;

		setToolTipText(interactionComp.getIdentifier() + "::"
				+ interactionComp.getType());

	}

	/**
	 * This method sets the {@link InformationPanel} to the
	 * {@link InteractionComponentView} and adds itself as {@link MouseListener}
	 * . So the InformationPanel can react, when mouse clicks on the
	 * InteractionComponentView.
	 * 
	 * @param infoPanel
	 *            the information panel
	 */
	public void setInformationPanel(InformationPanel infoPanel) {

		this.infoPanel = infoPanel;
		this.addMouseListener(this);

	}

	/**
	 * This method paints the interaction component as a filled rectangle.
	 */
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		
		if(!viewSettings.isPaintSensors()) {
			
			return;
			
		} else {
			
			
		}

		Graphics2D g2D = (Graphics2D) g;

		Dimension dim = getSize();

		g2D.setColor(Color.BLACK);
		g2D.fillRect(1, 1, (int) dim.getWidth() - 2, (int) dim.getHeight() - 2);

		g2D.setColor(this.stateColor);
		g2D.fillRect(2, 2, (int) dim.getWidth() - 4, (int) dim.getWidth() - 4);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		infoPanel.setSelectedInteractionComponent(interactionComp);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
