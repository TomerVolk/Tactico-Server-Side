package network;

/**
 * This class represents a two dimensional point
 * The point's cooridinates are integers
 *@author OOP Team
 */

public class Point {

	/**
	 *  current point's x coordinate
	 */
	int x;

	/**
	 *  current point's y coordinate
	 */
	int y;



	/**
	 * constructs a new point
	 * @param x : the point's x coordinate
	 * @param y : the point's y coordinate
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/** 
	 * Copy Constructor
	 * constructs a new point
	 * @param point: the point to be copied
	 */
	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
	}


	/**
	 * returns the point's x coordinate
	 */
	public int getX(){
		return this.x;
	}


	/**
	 * returns the point's y coordinate
	 */
	public int getY(){
		return this.y;
	}

	/**
	 * sets the point's x coordinate
	 * @param newX the value to be set
	 */
	public void setX(int newX){
		this.x = newX;
	}


	/**
	 * sets the point's y coordinate
	 * @param newY the value to be set
	 */
	public void setY(int newY){
		this.y = newY;
	}


	/**
	 * returns the string that represents the point.
	 */
	public String toString(){
		return "(" + this.x+"," + this.y+")\n";
	}


}
