package model;

import javafx.scene.image.ImageView;

/**
 * This class handles enemy projectile (fireball) logic. Extends the abstract element.
 * @author Joshua Tolentino and James Khalil
 *
 */
public class EnemyProjectile extends Element{
	// instance variable
	private double time = 0;
	
	/**
	 * Default constructor.
	 */
	public EnemyProjectile(){
	}
	
	/**
	 * Constructor that sets the velocity of the enemy projectile.
	 * @param vel speed of the projectile.
	 */
	public EnemyProjectile(double vel) {
		super.setVelocity(vel);
	}
	
	/**
	 * Method version for EmemyProjectile class.
	 * EnemyProjectile is passed in variable and is then randomly spawned in the four outer corners of the map.
	 * @param enemy which is the ImageView of the EnemyProjectile.
	 */
	public void linkWithController(ImageView enemy) {
		super.setImage(enemy);
	}
	
	/**
	 * Sets the fireballs 
	 */
	public void respawn() {
		int wall = (int)(Math.random()*(3-0+1)+0);  
		if(wall == 0) {
			super.setXCoord(-40);
			super.setYCoord(Math.random()*(HEIGHT-0+1)+0);
			super.setAngle(WIDTH,Math.random()*(HEIGHT-0+1)+0);
		}
		else if(wall == 1) {
			super.setXCoord(1300);
			super.setYCoord(Math.random()*(HEIGHT-0+1)+0);
			super.setAngle(0,Math.random()*(HEIGHT-0+1)+0);
		}
		else if(wall == 2) {
			super.setXCoord((Math.random()*(WIDTH-0+1)+0));
			super.setYCoord(-40);
			super.setAngle(Math.random()*(WIDTH-0+1)+0,HEIGHT);
		}
		else if(wall == 3) {
			super.setXCoord((Math.random()*(WIDTH-0+1)+0));
			super.setYCoord(750);
			super.setAngle(Math.random()*(WIDTH-0+1)+0,0);
		}
	}
	
	/**
	 * Method for collision detection.
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @return true or false based on if it is touching the given x,y coordinate given.
	 */
	public boolean touching(double width,double height, double x, double y) {
		if(super.getXCoord() > x + width||
			super.getYCoord() > y + height||
			x > super.getXCoord() + 20||
			y > super.getYCoord() + 20) {
			return false;
		}
		return true;
		
	}
	
	/**
	 * Getter method for the time instance variable.
	 * @return time.
	 */
	public double getTime() {
		return time;
	}
	
	/**
	 * Method to add more to the time.
	 * @param add which is the time amount.
	 */
	public void addTime(double add) {
		setTime(getTime() + add);
	}
	
	/**
	 * Setter method for the time.
	 * @param time
	 */
	public void setTime(double time) {
		this.time = time;
	}	
}
