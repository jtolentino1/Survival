package model;

import javafx.scene.image.ImageView;

/**
 * This class handles enemy (zombie) logic. Extends the abstract element.
 * @author Joshua Tolentino and James Khalil
 *
 */
public class Enemy extends Element{
	
	// instance variable
	private boolean danger = true;
	
	/**
	 * Default constructor.
	 */
	public Enemy() {
		
	}
	
	/**
	 * Constructor method with param vel, which sets the velocity of the enemy.
	 * @param vel which is the speed of the enemy.
	 */
	public Enemy(double vel) {
		super.setVelocity(vel);
	}
	
	/**
	 * Method version for Enemy class.
	 * Enemy is passed in variable and is then randomly spawned in the four outer corners of the map.
	 * @param enemy which is the ImageView of the enemy.
	 */
	public void linkWithController(ImageView enemy) {
		super.setImage(enemy);
		int wall = (int)(Math.random()*(3-0+1)+0);  
		if(wall == 0) {
			super.setXCoord(-50);
			super.setYCoord((Math.random()*(720-0+1)+0));
		}
		else if(wall == 1) {
			super.setXCoord(1300);
			super.setYCoord((Math.random()*(720-0+1)+0));
		}
		else if(wall == 2) {
			super.setXCoord((Math.random()*(720-0+1)+0));
			super.setYCoord(-50);
		}
		else if(wall == 3) {
			super.setXCoord((Math.random()*(720-0+1)+0));
			super.setYCoord(750);
		}
	}
	
	/**
	 * Respawns the enemy once killed, then sets the position randomly (similar to the method above).
	 */
	public void respawn() {
		if(danger == false) {
			int wall = (int)(Math.random()*(3-0+1)+0);  
			if(wall == 0) {
				super.setXCoord(-50);
				super.setYCoord((Math.random()*(720-0+1)+0));
			}
			else if(wall == 1) {
				super.setXCoord(1300);
				super.setYCoord((Math.random()*(720-0+1)+0));
			}
			else if(wall == 2) {
				super.setXCoord((Math.random()*(720-0+1)+0));
				super.setYCoord(-50);
			}
			else if(wall == 3) {
				super.setXCoord((Math.random()*(720-0+1)+0));
				super.setYCoord(750);
			}	
		}
		danger = true;
	}
	
	/**
	 * Collision detection for the enemy.
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @return whether or not the zombie is touching the x and y coordinate given.
	 */
	public boolean touching(double width,double height, double x, double y) {
		if(super.getXCoord() > x + width||
			super.getYCoord() > y + height||
			x > super.getXCoord() + 50||
			y > super.getYCoord() + 70) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if a zombie is alive and can kill.
	 * @return boolean true or false.
	 */
	public boolean isDanger() {
		return danger;
	}
	
	/**
	 * Sets whether or not a zombie is able to kill.
	 * @param danger
	 */
	public void setDanger(boolean danger) {
		this.danger = danger;
	}
}