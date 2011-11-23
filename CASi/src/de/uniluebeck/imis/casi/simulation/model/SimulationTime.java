package de.uniluebeck.imis.casi.simulation.model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The SimulationTime provides simple time objects which are used in the
 * simulation at every point where a time is needed This class provides some
 * abilities to deal with time and date.
 * 
 * @author Tobias Mende
 * 
 */
public class SimulationTime extends Timestamp {
	/*
	 * XXX Extend the implementation of the SimulationTime
	 * 
	 * - We need more abilities to parse localized time strings when
	 * constructing new time objects.
	 * 
	 * - We should provide methods for getting localized time and date string
	 */
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
}
