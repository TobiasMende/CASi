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
package de.uniluebeck.imis.casi.logging;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Helper class to format a Logger with HTML.
 * 
 * @author Folke Will, Tobias Mende
 * 
 */
public class HTMLFormatter extends java.util.logging.Formatter {
	/** the silent flag */
	private boolean silent;
	
	/**
	 * Constructor 
	 * @param silent if silent, no information about class paths and method names is printed
	 */
	public HTMLFormatter(boolean silent) {
		this.silent = silent;
	}
	@Override
	public String format(LogRecord rec) {
		StringBuffer r = new StringBuffer();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(
				DateFormat.SHORT, DateFormat.MEDIUM);

		String dateStr = dateFormat.format(new Date(rec.getMillis()));
		String className = rec.getSourceClassName();
		String methodName = rec.getSourceMethodName();
		String message = rec.getMessage().replaceAll("[\"]", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		message = rec.getMessage().replaceAll("(\r\n|\r|\n|\n\r)", "<br>").replaceAll("(\t)", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		r.append("<div class='log_entry'>");
		if(!silent) {
			r.append("<span class='log_class'>" + className + "</span> ");
			r.append("<span class='log_method'>(" + methodName + ")</span>:<br />");
		}
		r.append("<span class='log_date'>" + dateStr + "</span><br />");
		r.append("<span class='log_entry");
		if (rec.getLevel().intValue() <= Level.CONFIG.intValue())
			r.append(" log_fine");
		else if (rec.getLevel().intValue() <= Level.INFO.intValue())
			r.append(" log_default");
		else
			r.append(" log_error");
		r.append("'>" + message + "</span>");
		r.append("</div>");
		return r.toString();
	}

	@Override
	public String getHead(Handler h) {
		StringBuffer r = new StringBuffer();
		r.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN"
				+ "\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		r.append("<html lang='en' xml:lang='en'"
				+ "xmlns='http://www.w3.org/1999/xhtml'>");
		r.append("<head>");
		r.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> ");
		r.append("<title>CASi: ");
		if(silent) {
			r.append("Simulation Log");
		} else {
			r.append("Development Log");
		}
		r.append("</title>");
		r.append("<style type='text/css'>");
		r.append("body,html {font-family:Verdana,Helvetica, sans-serif; font-size:10pt;}");
		r.append(".log_error {color:#822618;}");
		r.append(".log_default {color:#000000;}");
		r.append(".log_fine {color:#14690A;}");
		r.append(".log_entry {width:100%; margin-bottom:10px}");
		r.append(".log_class {color:#27003E; font-size:110%;}");
		r.append(".log_method {color: #979797; font-size:105%;}");
		r.append(".log_date {width: 10em; font-size: 8pt; color:#666666;}");
		r.append("</style>");
		r.append("</head>");
		r.append("<body>");
		return r.toString();
	}

	@Override
	public String getTail(Handler h) {
		return "</body></html>";
	}
}
