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
package de.uniluebeck.imis.casi.utils;

import java.util.NoSuchElementException;

/**
 * Interface for adding and removing generic listeners to a listenable subject.
 * 
 * @author Tobias Mende
 * @param <T>
 *            Listener interface for defined events
 */
public interface Listenable<T> {
	/**
	 * Adds a listener to the listenable subject.
	 * 
	 * @param listener
	 *            Listener to be added.
	 */
	public void addListener(T listener);

	/**
	 * Removes a listener from the listenable subject.
	 * 
	 * @throws NoSuchElementException
	 *             if given listener doesn't exist.
	 * @param listener
	 *            Listener to be removed.
	 */
	public void removeListener(T listener);

}
