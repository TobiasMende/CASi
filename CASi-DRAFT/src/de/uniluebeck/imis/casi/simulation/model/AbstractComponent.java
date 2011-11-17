package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Image;

public abstract class AbstractComponent {
	private Image representation;
	private IPosition position;
	private String identifier;
	
	public IPosition getCurrentPosition() {
		return position;
	}

	public boolean setCurrentPosition(IPosition currentPosition) {
		this.position = position;
		return true;
	}
}
