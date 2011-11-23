package de.uniluebeck.imis.casi.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A collection of tools that provide some additional functionalities.
 * 
 * @author Tobias Mende
 * 
 */
public final class Tools {

	/**
	 * Makes a deep copy of an object. The object and its subcomponents must
	 * implement the {@link Serializable}-Interface
	 * 
	 * @param o
	 *            the object to clone
	 * @return the clone
	 * @throws IOException if an error occurs during serialization
	 * @throws ClassNotFoundException if the class of a serializable object cannot be found.
	 */
	public static Object deepCopy(Object o) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}
}
