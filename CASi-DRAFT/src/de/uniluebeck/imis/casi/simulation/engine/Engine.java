package de.uniluebeck.imis.casi.simulation.engine;

import java.util.Collection;

public class Engine {
	private static Engine instance;
	
	private Engine() {
		
	}
	
	public static Engine getInstance() {
		if(instance == null) {
			instance = new Engine();
		}
		return instance;
	}
}
