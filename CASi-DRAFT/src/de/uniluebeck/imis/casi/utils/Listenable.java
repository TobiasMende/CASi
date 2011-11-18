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
	 * @throws NoSuchElementException if given listener doesn't exist.
	 * @param listener
	 *            Listener to be removed.
	 */
	public void removeListener(T listener);
	
}
