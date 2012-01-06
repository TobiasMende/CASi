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

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

/**
 * The {@link ComboBoxRenderer} renders the {@link JComboBox} of the
 * {@link InformationPanel} to show an separator between agents and interaction
 * components.
 * 
 * @author Moritz Bürger
 * 
 */
@SuppressWarnings("serial")
public class ComboBoxRenderer extends JLabel implements ListCellRenderer {

	public static final String SEPERATOR = "M19Rt2798g251R47G93u5z07962L";

	private JSeparator separator;

	/**
	 * The constructor initializes the {@link JSeparator}.
	 */
	public ComboBoxRenderer() {

		setOpaque(true);
		setBorder(new EmptyBorder(1, 1, 1, 1));
		separator = new JSeparator(JSeparator.HORIZONTAL);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		String str = (value == null) ? "" : value.toString();
		if (ComboBoxRenderer.SEPERATOR.equals(str)) {
			return separator;
		}
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setFont(list.getFont());
		setText(str);
		return this;
	}

}
