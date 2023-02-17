package model;

import javafx.scene.image.ImageView;

/**
 * This class handles UserProjectile logic. Extends the abstract element.
 * @author Joshua Tolentino and James Khalil
 *
 */
public class UserProjectile extends Element{
	
	// instance variables
	final static int DISTANCE = 450;
	private static UserProjectile singleton;
	private double travel;
	
	/**
	 * Singleton constructor for UserProjectile
	 * @return singleton instance of UserProjectile.
	 */
	public static UserProjectile getInstance() {
		if (singleton == null) {
			singleton = new UserProjectile();
		}
		return singleton;
	}
	
	/**
	 * Setter method for where to set the projectile based on x,y coords.
	 * @param x
	 * @param y
	 */
	public void setToCharacter(double x,double y) {
		super.setXCoord(x);
		super.setYCoord(y);
	}
	
	/**
	 * Method to check if the projectile has reached a certain distance.
	 * @return true or false.
	 */
	public boolean passed() {
		if (getTravel() > DISTANCE) return true;
		return false;
	}
	
	/**
	 * Getter method for travel.
	 * @return
	 */
	public double getTravel() {
		return travel;
	}
	
	/**
	 * Adder method for travel.
	 * @param add
	 */
	public void addTravel(double add) {
		setTravel(getTravel() + add);
	}
	
	/**
	 * Setter method for travel.
	 * @param travel
	 */
	public void setTravel(double travel) {
		this.travel = travel;
	}
	
	/**
	 * Method version for UserProjectile class.
	 * UserProjectile is passed in and set to the character position.
	 * @param character which is the ImageView of the UserProjectile.
	 */
	public void linkWithController(ImageView character) {
		super.setImage(character);
		super.setXCoord(character.getLayoutX());
		super.setYCoord(character.getLayoutY());
	}

}
