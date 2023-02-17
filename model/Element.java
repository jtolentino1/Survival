package model;

import javafx.scene.image.ImageView;

/**
 * Abstract class "Element" which applies to everything the user sees on the screen.
 * @author Joshua Tolentino and James Khalil
 *
 */
public abstract class Element {
	
	// instance variables
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 720;
	private double xCoord;
	private double yCoord;
	private double velocity;
	private double angle;
	private ImageView image;
	
	/**
	 * Getter method for getting the x-coord of the element.
	 * @return the x-coordinate.
	 */
	public double getXCoord() {
		return xCoord;
	}
	
	/**
	 * Setter method for the x-coord of the element.
	 * @param xCoord
	 */
	protected void setXCoord(double xCoord) {
		this.xCoord = xCoord;
	}
	
	/**
	 * Getter method for getting the y-coord of the element.
	 * @return the y-coordinate.
	 */
	public double getYCoord() {
		return yCoord;
	}
	
	/**
	 * Setter method for the y-coord of the element.
	 * @param yCoord
	 */
	protected void setYCoord(double yCoord) {
		this.yCoord = yCoord;
	}
	
	/**
	 * Getter method for the velocity of the element.
	 * @return velocity (speed) of element.
	 */
	public double getVelocity() {
		return velocity;
	}
	
	/**
	 * Setter method for the velocity of the element.
	 * @param velocity
	 */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * Getter method for the angle of the element.
	 * @return angle
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * Setter method for the angle of the element. An x and y value is needed to set the angle.
	 * @param x
	 * @param y
	 */
	public void setAngle(double x, double y) {
		angle = Math.atan2((y-getYCoord()),(x-getXCoord()));
	}
	
	/**
	 * Method for moving the element in the screen.
	 */
	public void tick() {
		setXCoord(getXCoord() + Math.cos(getAngle())*getVelocity());
		setYCoord(getYCoord() + Math.sin(getAngle())*getVelocity());
		getImage().setLayoutX(getXCoord());
		getImage().setLayoutY(getYCoord());
	}
	
	/**
	 * This method links the controller with the model java file through the JavaFX image.
	 * @param image
	 */
	public abstract void linkWithController(ImageView image);
	
	/**
	 * Getter method image for the JavaFX node.
	 * @return JavaFX node.
	 */
	public ImageView getImage() {
		return image;
	}
	
	/**
	 * Setter method for the JavaFX node 
	 * @param image
	 */
	protected void setImage(ImageView image) {
		this.image = image;
	}
}
