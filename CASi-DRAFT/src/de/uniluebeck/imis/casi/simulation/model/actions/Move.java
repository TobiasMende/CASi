package de.uniluebeck.imis.casi.simulation.model.actions;

import de.uniluebeck.imis.casi.simulation.model.*;

public class Move extends AbstractAction {

	private Agent whoMoves;
	private IPosition whereTo;
	
	public Move(Agent whoMoves, IPosition whereTo){
		this.whoMoves = whoMoves;
		this.whereTo = whereTo;
	}
	
	@Override
	public STATE internalPerform(){
		
		//TODO: tell the Agent to do something smart!
		
		return STATE.COMPLETED;

	}

}
