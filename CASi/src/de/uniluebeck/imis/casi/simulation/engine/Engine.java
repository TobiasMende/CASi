package de.uniluebeck.imis.casi.simulation.engine;

import de.uniluebeck.imis.casi.communication.ICommunicationHandler;
import de.uniluebeck.imis.casi.simulation.model.SimulationTime;
import de.uniluebeck.imis.casi.simulation.model.World;

public class Engine implements ISimulationClockListener{
	private static Engine instance;
	private ICommunicationHandler communicationHandler;
	private World world;
	private boolean started;
	
	private Engine() {
		SimulationClock.getInstance().addListener(this);
		//just here
	}
	
	public static Engine getInstance() {
		if(instance == null) {
			instance = new Engine();
		}
		return instance;
	}
	
	public ICommunicationHandler getCommunicationHandler() {
		return communicationHandler;
	}
	
	public void setCommunicationHandler(
			ICommunicationHandler communicationHandler) {
		this.communicationHandler = communicationHandler;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public void start() throws IllegalStateException{
		if(started)
			return;
		if(communicationHandler == null)
			throw new IllegalStateException("CommunicationHandler must be set");
		if(world == null)
			throw new IllegalStateException("World must be set");
		SimulationClock.getInstance().init(world.getStartTime());
		SimulationClock.getInstance().start();
		started = true;
	}

	@Override
	public void timeChanged(SimulationTime newTime) {
		world.setCurrentTime(newTime);
	}
}
