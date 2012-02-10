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

import java.awt.Color;

/**
 * This class saves the color scheme of the simple GUI.
 * 
 * @author Moritz Bürger
 * 
 */
public class ColorScheme {

	/* Orange tones */
	/** Light orange, value: EDB14E, from the selected color scheme. */
	public static Color ORANGE_LIGHT = new Color(237, 177, 78);
	/** Very light orange, value: EDC076, from the selected color scheme. */
	public static Color ORANGE_VERY_LIGHT = new Color(237, 192, 118);
	/** Orange, value: DD941A, from the selected color scheme. */
	public static Color ORANGE = new Color(221, 148, 26);
	/** Dark orange, value: A87E37, from the selected color scheme. */
	public static Color ORANGE_DARK = new Color(168, 126, 55);
	/** Very dark orange, value: 935F09, from the selected color scheme. */
	public static Color ORANGE_VERY_DARK = new Color(147, 95, 9);

	/* Blue tones */
	/** Dark blue, value: 2B476E, from the selected color scheme. */
	public static Color BLUE_DARK = new Color(43, 71, 110);
	/** Very dark blue, value: 0A2E60, from the selected color scheme. */
	public static Color BLUE_VERY_DARK = new Color(10, 46, 96);
	/** Blue, value: 1C4D91, from the selected color scheme. */
	public static Color BLUE = new Color(28, 77, 145);
	/** Light blue, value: 4D80C6, from the selected color scheme. */
	public static Color BLUE_LIGHT = new Color(77, 128, 198);
	/** Very light blue, value: 6B91C6, from the selected color scheme. */
	public static Color BLUE_VERY_LIGHT = new Color(107, 145, 198);
	
	/** Extremely light blue, value: EEEEFF, background color. */
	public static Color BACKGROUND = new Color(238,238,255);
	/** Extremely light orange, value: F0E68C, room color. */
	public static Color ROOM = new Color(255,250,205);
	/** Special blue tone, value: C8C8FF, for the GUI background. */
	public static Color BACKGROUND_GUI = new Color(215,215,255);
	/** Special blue tone, value: 4D80C6, for the sensor/actuator monitoring area. */
	public static Color SENSOR_AREA = new Color(77, 128, 198, 15);

}
