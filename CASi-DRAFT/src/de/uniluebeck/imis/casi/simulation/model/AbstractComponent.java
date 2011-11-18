package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import de.uniluebeck.imis.casi.utils.Listenable;

public abstract class AbstractComponent implements Listenable<IComponentListener> {
	private Image representation;
	private IPosition position;
	protected final String identifier;
	protected List<IComponentListener> listeners = new ArrayList<IComponentListener>();
	
	public AbstractComponent(String identifier) {
		this.identifier = identifier;
	}
	public IPosition getCurrentPosition() {
		return position;
	}

	public boolean setCurrentPosition(IPosition currentPosition) {
		this.position = position;
		return true;
	}
	
	@Override
	public void addListener(IComponentListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	@Override
	public void removeListener(IComponentListener listener) {
		listeners.remove(listener);
	}
	
	public abstract String getIdentifier();
}
