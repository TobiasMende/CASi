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
package de.uniluebeck.imis.casi.simulation.model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The SimulationTime provides simple time objects which are used in the
 * simulation at every point where a time is needed This class provides some
 * abilities to deal with time and date.
 * 
 * @author Tobias Mende
 * 
 */
public class SimulationTime extends Timestamp {
	/** serialization identifier */
	private static final long serialVersionUID = -7542294662718264396L;

	/**
	 * Default constructor which creates a new simulation time object using a
	 * timestamp
	 * 
	 * @param time
	 *            the timestamp
	 */
	public SimulationTime(long time) {
		super(time);
	}

	/**
	 * Constructor for creating a new simulation time parsing a date time string
	 * with a self defined pattern
	 * 
	 * @param dateTime
	 *            the string to parse
	 * @param pattern
	 *            the pattern to use
	 * @throws ParseException
	 *             if the string doesn't match the pattern
	 */
	public SimulationTime(String dateTime, String pattern)
			throws ParseException {
		super(0);
		DateFormat formatter = new SimpleDateFormat(pattern);
		Date date = formatter.parse(dateTime);
		setTime(date.getTime());

	}

	/**
	 * Constructor for creating a new simulation time using a date time string
	 * in format "MM/dd/yyyy HH:mm:ss".
	 * 
	 * @param dateTime
	 *            the string to parse
	 * @throws ParseException
	 *             if the string doesn't match the pattern
	 */
	public SimulationTime(String dateTime) throws ParseException {
		this(dateTime, "MM/dd/yyyy HH:mm:ss");
	}

	/**
	 * Method for incrementing the time by one second (1000 milliseconds)
	 */
	public void increment() {
		setTime(getTime() + 1000);
	}

	/**
	 * Method to get a TimeStap of the this SimulationTime plus some supllied
	 * seconds. Useful for relative times.
	 * 
	 * @param additionalSeconds the seconds to add to this TimeStamp
	 * @return the new TimeStamp
	 */
	public SimulationTime plus(int additionalSeconds) {
		return new SimulationTime(this.getTime() + (additionalSeconds * 1000));

	}

	/**
	 * Getter for the time as localized string
	 * 
	 * @return the time as string
	 */
	public String getLocalizedTime() {
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		return formatter.format(new Date(getTime()));
	}

	/**
	 * Getter for the localized date of this time object
	 * 
	 * @return the date as string
	 */
	public String getLocalizedDate() {
		Locale loc = Locale.getDefault();
		DateFormat formatter = null;
		if (loc.equals(Locale.GERMAN)) {
			formatter = new SimpleDateFormat("dd.MM.yyyy");
		} else {
			formatter = new SimpleDateFormat("MM/dd/yyyy");
		}

		return formatter.format(new Date(getTime()));
	}
}
