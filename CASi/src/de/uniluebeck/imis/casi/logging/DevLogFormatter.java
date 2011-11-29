/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
 */
package de.uniluebeck.imis.casi.logging;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Simple log formatter for formatting the development log files in non
 * productive mode.
 * 
 * @author Tobias Mende
 * 
 */
public class DevLogFormatter extends Formatter {

	@Override
	public String format(LogRecord rec) {
		StringBuffer r = new StringBuffer();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(
				DateFormat.SHORT, DateFormat.MEDIUM);
		String dateStr = dateFormat.format(new Date(rec.getMillis()));
		r.append(dateStr);
		r.append(": " + rec.getSourceClassName());
		r.append(":" + rec.getSourceMethodName() + " \t ");
		r.append(rec.getLevel().getLocalizedName());
		r.append("\t\t" + rec.getMessage());
		r.append("\n");

		return r.toString();
	}

}
