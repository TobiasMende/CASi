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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import de.uniluebeck.imis.casi.simulation.model.AbstractInteractionComponent;

/**
 * @author Moritz B&uuml;rger
 * 
 */
@SuppressWarnings("serial")
public class InteractionComponentView extends ComponentView {

	private AbstractInteractionComponent interactionComp;

	public InteractionComponentView(AbstractInteractionComponent comp,
			AffineTransform transform) {
		super(comp.getCentralPoint(), transform);
		interactionComp = comp;
		setToolTipText(interactionComp.getIdentifier() + "::"
				+ interactionComp.getType());

	}


	/**
	 * Overrides the paint-method to paint the interaction component as a 6x6
	 * filled rectangle.
	 */
	public void paint(Graphics g) {

		Graphics2D g2D = (Graphics2D) g;

		g2D.setColor(Color.BLACK);
		g2D.fillRect(1, 1, 6, 6);
		g2D.setColor(this.stateColor);
		g2D.fillRect(2, 2, 4, 4);
	}

}
