package de.uniluebeck.imis.casi.simulation.model;

import java.awt.Point;
import java.util.Collection;
import java.util.Vector;

public class Wall {
	private Point start;
	private Point end;
	
	private final Collection<Door> doors = new Vector<Door>();
	
	public Wall(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	
	public void addDoor(Door door) {
		doors.add(door);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doors == null) ? 0 : doors.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wall other = (Wall) obj;
		if (doors == null) {
			if (other.doors != null)
				return false;
		} else if (!doors.equals(other.doors))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
	
	
}
