package de.uniluebeck.imis.casi.simulation.model;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;

import de.uniluebeck.imis.casi.simulation.factory.WorldFactory;

/**
 * The representation for a door that can be added to a wall
 * @author Tobias Mende
 *
 */
public class Door extends AbstractComponent{
	/** A prefix for the identifier of this door	 */
	public static final String ID_PREFIX = "door-";
	/**the id for serialization */
	private static final long serialVersionUID = 8551792658587147027L;
	/** The default size for doors */
	public static final int DEFAULT_DOOR_SIZE = 1;
	/** The default door offset, if <code>-1</code> the door will be centered */
	public static final int DEFAULT_DOOR_OFFSET = -1;
	/** The id counter */
	private static int id;
	/** The offset from the startpoint of the containing wall */
	private int offset;
	/** The size of the door */
	private int size;
	/** The wall that contains this door */
	private Wall wall;
	/** The identifier */
	private int identifier;
	
	/**
	 * Creates a door with a given identifier
	 * @param identifier the identifier
	 */
	private Door(int identifier) throws InvalidParameterException{
		super(ID_PREFIX+identifier);
		if(WorldFactory.findDoorForIdentifier(ID_PREFIX+identifier) != null) {
			throw new InvalidParameterException("There is a door with this identifier yet.");
		}
		WorldFactory.addDoor(this);
	}
	/**
	 * Constructor for a door with a given offset of the walls start point
	 * @param offset the offset from the start point of the containing wall
	 * @param size the size of the wall (must be positive)
	 */
	public Door(int offset, int size) {
		this(id);
		id++;
		this.offset = offset;
		this.size = Math.abs(size);
	}
	/**
	 * Constructor for a default door that is automatically centered on the wall and has a default size of DEFAULT_DOOR_SIZE
	 */
	public Door() {
		this(DEFAULT_DOOR_OFFSET, DEFAULT_DOOR_SIZE);
	}

	/**
	 * Getter for the offset of the wall
	 * @return the offset or <code>-1</code> if the door should be automatically positioned
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Getter for the size of the door
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Setter for the wall that contains this door
	 * @param wall
	 */
	public void setWall(Wall wall) {
		this.wall = wall;
	}
	
	/** 
	 * Getter for the integer value of the identifier for this door
	 * @return the identifier as integer
	 */
	public int getIntIdentifier() {
		return identifier;
	}

	
	@Override
	public boolean contains(IPosition position) {
		return contains(position.getCoordinates());
	}

	@Override
	public boolean contains(Point2D point) {
		return getShapeRepresentation().ptLineDist(point) == 0;
	}

	@Override
	public Line2D getShapeRepresentation() {
		Point2D wallVector = wall.getNormalizedWallVector();
		Point2D centralPoint = getCentralPoint();
		double startEndOffset = ((double)size)/2;
		// Calculating offset vectors
		Point2D startOffsetVector = new Point2D.Double(wallVector.getX()*(-startEndOffset), wallVector.getY()*(-startEndOffset));
		Point2D endOffsetVector = new Point2D.Double(wallVector.getX()*startEndOffset, wallVector.getY()*startEndOffset);
		// Calculating start end end points
		Point2D startPoint = new Point2D.Double(centralPoint.getX()+startOffsetVector.getX(), centralPoint.getY()+startOffsetVector.getY());
		Point2D endPoint = new Point2D.Double(centralPoint.getX()+endOffsetVector.getX(), centralPoint.getY()+endOffsetVector.getY());
		return new Line2D.Double(startPoint, endPoint);
	}

	@Override
	public Point2D getCentralPoint() {
		if(offset < 0) {
			return wall.getCentralPoint();
		}
		Point2D wallVector = wall.getNormalizedWallVector();
		// The offset of the central point:
		double centralOffset = ((double)offset) + ((double)size)/2;
		// A vector with the wall direction and the length of the offset
		Point2D doorStartOffset = new Point2D.Double(wallVector.getX()*centralOffset, wallVector.getY()*centralOffset);
		// Add start point as begin for the vector
		return new Point2D.Double(wall.getStartPoint().getX()+doorStartOffset.getX(), wall.getStartPoint().getY()+doorStartOffset.getY());
	}
	
	@Override
	public Point2D getCoordinates() {
		return getCentralPoint();
	}
	
	/**
	 * Method for getting the number of doors in the simulation
	 * @return The static identifier counter
	 */
	public static int getNumberOfDoors() {
		return id;
	}
	

}
