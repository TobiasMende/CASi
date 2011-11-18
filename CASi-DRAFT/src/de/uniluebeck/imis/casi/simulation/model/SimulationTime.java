package de.uniluebeck.imis.casi.simulation.model;

import java.sql.Timestamp;

public class SimulationTime extends Timestamp {
	
	private static final long serialVersionUID = -7542294662718264396L;
	
	public SimulationTime(long time) {
		super(time);
		// TODO Auto-generated constructor stub
	}
	
	public SimulationTime(String time) {
		this(Timestamp.valueOf(time).getTime());
	}
	
	public void increment() {
		setTime(getTime()+1);
	}



	

}
