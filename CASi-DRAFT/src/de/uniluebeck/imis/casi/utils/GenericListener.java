package de.uniluebeck.imis.casi.utils;

import java.io.Serializable;

/**
 * Generic interface for listeners
 * 
 * @author Tobias Mende
 * 
 * @param <T>
 *            the type of the listenable object
 */
public interface GenericListener<T> extends Serializable {
	/**
	 * Method for informing the listeners about changes
	 * 
	 * @param src
	 *            the source where something was changed
	 */
	public void changed(T src);
}
