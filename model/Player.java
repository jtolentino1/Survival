package model;

import javafx.scene.image.ImageView;

/**
 * This class handles user logic. Extends the abstract element.
 * @author Joshua Tolentino and James Khalil
 *
 */
public class Player extends Element{
	
	// instance variables
	private double velocityX;
	private double velocityY;
	private boolean flashed;
	private double flashCooldown;
	private static Player singleton;
	
	/**
	 * Singleton constructor for Player.
	 * @return singleton instance.
	 */
	public static Player getInstance() {
		if (singleton == null) {
			singleton = new Player();
		}
		return singleton;
	}
	
	/**
	 * Method that resets the player coordinates and flash cooldown.
	 */
	public void reset() {
		setVelocityX(0.0);
		setVelocityY(0.0);
		setFlashCooldown(0.0);
		setFlashed(false);
	}
	
	/**
	 * Method that updates the movement of the user. Handles bounds detection so user does not move away from the bounds.
	 */
	public void tick() {
		// cooldown for flash (16 second cooldown)
		if(isFlashed() && getFlashCooldown() <= 0) {
			super.tick();
			setFlashCooldown(16);
		}
		
		// bounds detection, numbers are not magic numbers as they are tested to ensure the smoothest bounds detection.
		else {
			setXCoord(getXCoord()+getVelocityX());
			setYCoord(getYCoord()+getVelocityY());		
			
			if(getXCoord()<=0) {
				setXCoord(0);
			}
			if(getXCoord()>=1195) {
				setXCoord(1195);
			}
			if(getYCoord()<=0) {
				setYCoord(0);
			}
			if(getYCoord()>=640) {
				setYCoord(640);
			}
			getImage().setLayoutX(getXCoord());
			getImage().setLayoutY(getYCoord());
			
			// handles more flash cooldown
			if(getFlashCooldown() > 0) {
				decreaseFlashCooldown((double)1/60);
			}
			if(getFlashCooldown() <= 0) {
				flashed = false; 
				setFlashCooldown(0);
			}
		}
	}
	
	/**
	 * Method version for Player class.
	 * User is passed in as a variable which would have its X,Y coords manipulated.
	 * @param character which is the Image of the user.
	 */
	public void linkWithController(ImageView character) {
		super.setImage(character);
		super.setXCoord(character.getLayoutX());
		super.setYCoord(character.getLayoutY());
	}
	
	/** 
	 * Getter method for velocity xcoord.
	 * @return velocityX
	 */
	public double getVelocityX() {
		return velocityX;
	}
	
	/**
	 * Setter method for the velocity xcoord.
	 * @param velocityX
	 */
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	
	/**
	 * Getter method for the velocity ycoord.
	 * @return velocityY
	 */
	public double getVelocityY() {
		return velocityY;
	}
	
	/**
	 * Setter method for the velocity ycoord.
	 * @param velocityY
	 */
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	
	/**
	 * Getter method for the boolean on whether or not user flashed.
	 * @return flashed
	 */
	public boolean isFlashed() {
		return flashed;
	}
	
	/**
	 * Setter method for the boolean on whether or not user flashed.
	 * @param flashed
	 */
	public void setFlashed(boolean flashed) {
		this.flashed = flashed;
	}
	
	/**
	 * Getter method for flash cooldown.
	 * @return flashCooldown
	 */
	public double getFlashCooldown() {
		return flashCooldown;
	}
	
	/** 
	 * Setter method for flash cooldown.
	 * @param flashCooldown
	 */
	public void setFlashCooldown(double flashCooldown) {
		this.flashCooldown = flashCooldown;
	}
	
	/**
	 * Method to decrease flash cooldown.
	 * @param decrease amount to decrease the cooldown by.
	 */
	public void decreaseFlashCooldown(double decrease) {
		flashCooldown -= decrease;
	}
	
}
