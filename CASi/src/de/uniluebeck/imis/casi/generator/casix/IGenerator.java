package de.uniluebeck.imis.casi.generator.casix;

import java.util.Collection;

import de.uniluebeck.imis.casi.simulation.model.AbstractComponent;

public interface IGenerator {

	public Collection<AbstractComponent> genObjectFromXML();
}
