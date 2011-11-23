package de.uniluebeck.imis.casi.simulation.engine;

import java.util.ArrayList;
import java.util.Timer;

import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.utils.Listenable;

/**
 * Simulation Clock maps real time on simulated time
 * @author Tobias Mende
 *
 */
public class SimulationClock implements Listenable<ISimulationClockListener> {
	private static SimulationClock instance;
	private SimulationTime simulationStartTime;
	private double scaleFactor = 1.0;
	private boolean started;
	private boolean paused;
	private Timer timer;
	/** Listeners are informed about every tick of the clock */
	private ArrayList<ISimulationClockListener> listeners = new ArrayList<ISimulationClockListener>();
	private SimulationClock() {
		
	}
	
	public static SimulationClock getInstance() {
		if(instance == null) {
			instance = new SimulationClock();
		}
		return instance;
	}
	
	public void init(SimulationTime startTime) throws IllegalStateException {
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
		//timer = new Timer("CASi-System-Clock", true);
		started = true;
	}
	
	public void stop() throws IllegalStateException {
		if(!started) {
			throw new IllegalStateException("Simulation isn't started yet.");
		}
		timer.cancel();
		// TODO implement
		started = false;
	}
	
	public synchronized void setPaused(boolean pause) {
		paused = pause;
	}
	
	public synchronized boolean isPaused() {
		return paused;
	}


	@Override
	public synchronized void addListener(ISimulationClockListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public synchronized void removeListener(ISimulationClockListener listener) {
		listeners.remove(listener);
	}

}
