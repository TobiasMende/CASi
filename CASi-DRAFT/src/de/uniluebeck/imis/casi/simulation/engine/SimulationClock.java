package de.uniluebeck.imis.casi.simulation.engine;

import java.util.ArrayList;

import de.uniluebeck.imis.casi.utils.GenericListener;
import de.uniluebeck.imis.casi.utils.Listenable;

/**
 * Simulation Clock maps real time on simulated time
 * @author Tobias Mende
 *
 */
public class SimulationClock implements Listenable<GenericListener<SimulationClock>>{
	private static SimulationClock instance;
	private Integer simulationStartTime; //TODO use real time and date
	private double scaleFactor = 1.0;
	private boolean started;
	/** Listeners are informed about every tick of the clock */
	private ArrayList<GenericListener<SimulationClock>> listeners = new ArrayList<GenericListener<SimulationClock>>();
	private SimulationClock() {
		
	}
	
	public static SimulationClock getInstance() {
		if(instance == null) {
			instance = new SimulationClock();
		}
		return instance;
	}
	
	public void init(int startTime) throws IllegalStateException {
		if(started) {
			throw new IllegalStateException("Can't init clock after starting");
		}
		simulationStartTime = startTime;
	}
	
	public void start() throws IllegalStateException {
		if(started) {
			throw new IllegalStateException("Simulation was already started");
		} else if(simulationStartTime == null) {
			throw new IllegalStateException("Can't start before initialization");
		}
		// TODO start Timer task
		started = true;
	}

	@Override
	public void addListener(GenericListener<SimulationClock> listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeListener(GenericListener<SimulationClock> listener) {
		listeners.remove(listener);
	}

}
