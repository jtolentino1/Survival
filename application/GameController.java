package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.*;

/**
 * This class is handles the bulk of the application. Deals with user inputs,
 * handles the timing for tick methods (which is extended from AnimationTimer), and handles
 * basic logic such as projectile firing and collision detection.
 * @author Joshua Tolentino and James Khalil
 *
 */
public class GameController extends AnimationTimer{
	
	// Instance variables that will be changed depending on the difficulty and game mode.
	private int numOfEnemies;
	private int numOfFireballs;
	private double enemyMovementSpeed;
	private double fireballVelocity;
	private double enemySize = 70;
	private double fireballSize = 30;
	
	// JavaFX Node Instance Variables
    @FXML
    private ImageView character;
    
    @FXML
    private Text paused;
    
    @FXML
    private Text scoreText;
   
    @FXML
    private Pane gamePane;
    
    @FXML
    private ImageView projectile;
    
    @FXML
    private ImageView backgroundImage;

    @FXML
    private ImageView flashImage;
    
    @FXML
    private Text cooldownText;
    
    // Additional Instance Variables
    private GameApp app;
    private double mousePosX;
    private double mousePosY;
	private double cooldownTextInt = 0;
    private boolean shot = false;
    private int score = 0;
    private boolean isPaused;
    private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    private ArrayList<EnemyProjectile> enemyProjectileList = new ArrayList<EnemyProjectile>();
    
    /**
     * This method is the primary tick method which calls other tick methods for certain
     * logic handles. All the methods inside are invoked 60 times per second to ensure a smooth
     * 60 frames per second.
     */
    public void tick() {
    	playerTick();
		projectileTick();
		moveEnemies();
		moveEnemyProjectiles();
		checkHitBox();
		updateFlashGUI();
		updateScore();
    }
    
    /**
     * This method allows for user movement. Refer to Player.java to 
     * see how the logic works.
     */
    public void playerTick() {
    	Player.getInstance().tick();
    	
    	// Also updates the cooldown for flash.
    	cooldownTextInt -= (double)1/60;
    }
    
    /**
     * This method checks if the user has fired their weapon, which then begins
     * to call on the UserProjectile tick method (if shot), which ultimately starts to move 
     * the user projectile.
     */
    public void projectileTick() {
    	if(shot == true) {
			if(!UserProjectile.getInstance().passed()) {
				UserProjectile.getInstance().tick();
				UserProjectile.getInstance().addTravel(UserProjectile.getInstance().getVelocity());
			}
			else {
				// Make bullet not move and make it invisible if shot is not true.
				UserProjectile.getInstance().setTravel(0);
				UserProjectile.getInstance().setVelocity(0);
				shot = false;
				UserProjectile.getInstance().getImage().setVisible(shot);
			}
    	}
    }
    
    /**
     * This method updates the Node for the flash icon and cooldown text. If the user flashed, countdown. If not,
     * hide cooldownText and set the opacity of the flash image to 100%.
     */
    public void updateFlashGUI() {
    	if(Player.getInstance().isFlashed()) {
    		cooldownText.setVisible(true);
    		cooldownText.setText(String.format("%.1f",cooldownTextInt));
    	}
    	else {
    		cooldownText.setVisible(false);
    		flashImage.setOpacity(1);
    		cooldownText.setText(String.format("%.1f",cooldownTextInt));
    	}
    }
    
    /**
     * This method handles the enemy movements.
     */
    public void moveEnemies() {
    	for(int index = 0; index < enemyList.size(); index++) {
    		enemyList.get(index).setAngle(Player.getInstance().getXCoord(),Player.getInstance().getYCoord());
    		enemyList.get(index).tick();
    		enemyList.get(index).getImage().setRotate(Math.toDegrees(enemyList.get(index).getAngle())+90);
    	}
    }
    
    /**
     * This method handles the movement of enemy projectiles.
     */
    public void moveEnemyProjectiles() {
    	for(int index = 0; index < enemyProjectileList.size(); index++) {
			//Check if existed for too long by seeing if they are way out of the bounds (1280 x 720).
    		if (enemyProjectileList.get(index).getXCoord() > 1330||
    			enemyProjectileList.get(index).getXCoord() < -50||
    			enemyProjectileList.get(index).getYCoord() < -50||
    			enemyProjectileList.get(index).getYCoord() > 760) {
    			enemyProjectileList.get(index).respawn();
    		}
    		else {
        		enemyProjectileList.get(index).tick();
        		enemyProjectileList.get(index).getImage().setRotate(90+Math.toDegrees(enemyProjectileList.get(index).getAngle()));
        		enemyProjectileList.get(index).addTime(0.01);
    		}
    	}
    }
    
    /**
     * This method checks the hit boxes for every instance of collision in the game.
     */
    public void checkHitBox() {
    	
    	// enemy hits player
    	for(int index = 0; index < enemyList.size(); index++) {
    		if(enemyList.get(index).isDanger() &&
    				enemyList.get(index).touching(50, 50, Player.getInstance().getXCoord(), Player.getInstance().getYCoord())){
        			fadeOut();
        			stop();
        	}	
    		else {
    			enemyList.get(index).respawn();
    			enemyList.get(index).getImage().setVisible(true);
    		}
    	}	
    	
    	// hit enemy with bullet and increment score
    	for(int index = 0; index < enemyList.size(); index++) {
    		if(enemyList.get(index).isDanger() && shot && 
    			enemyList.get(index).touching(12, 12, UserProjectile.getInstance().getXCoord(), UserProjectile.getInstance().getYCoord())){  
    			
    			// plays the kill sound if enemy is hit
				AudioClip gunshot = new AudioClip(getClass().getResource("/assets/Kill.mp3").toExternalForm());
				gunshot.setVolume(0.025);
				gunshot.play();
				
				// score is incremented by 10 and the enemy is then reset
    			score += 10;
    			enemyList.get(index).setDanger(false);
    			enemyList.get(index).getImage().setVisible(false);
    			UserProjectile.getInstance().setTravel(0);
				UserProjectile.getInstance().setVelocity(0);
				shot = false;
				UserProjectile.getInstance().getImage().setVisible(shot);
        	}
    	}
    	
    	// enemy projectile hits player
    	for(int index = 0; index < enemyProjectileList.size(); index++) {
    		// check if hit player
    		if(enemyProjectileList.get(index).touching
    			(50, 50, Player.getInstance().getXCoord(), Player.getInstance().getYCoord())){
        		fadeOut();
        		stop();
        	}	
    	}	
    }
    
    /**
     * This method handles score time logic. This means score is incremented by 1 per tick, which happens 60 times per second.
     */
    public void updateScore() {
    	score += 1;
    	scoreText.setText("SCORE: "+score);
    }
    
    /**
     * This method transitions between the GameView screen and the GameOver screen through a fade transition.
     * This is invoked when the user collides with either a zombie or an enemy projectile.
     */
    public void fadeOut() {
    	FadeTransition fadeTransition = new FadeTransition(); 
    	
    	// play the game over sound effect
		AudioClip gunshot = new AudioClip(getClass().getResource("/assets/GameOver.mp3").toExternalForm());
		gunshot.setVolume(0.050);
		gunshot.play();
		
		// set the game over fade out animation.
    	fadeTransition.setDuration(Duration.seconds(2));
    	fadeTransition.setNode(gamePane);
    	fadeTransition.setFromValue(1);
    	fadeTransition.setToValue(0);
    	fadeTransition.play();
    	fadeTransition.setOnFinished((ActionEvent event) -> {
    		// call the gameOver method in GameApp.
        	app.gameOver(score);
    	});
    }
    
    /**
     * This method links GameApp.java with this class. This way, GameApp.gameOver() can be invoked.
     * @param app which is the instance of GameApp.
     */
    public void linkWithApp(GameApp app) {
    	this.app = app;
    }
    
    /**
     * This method is the initial setup for the game. This is called from GameApp.
     * @param difficulty which adjusts enemy speed, number of projectiles, speed or projetiles.
     * @param gamemode which adjusts whether or not zombies appear, projectiles appear, or both appear.
     * @throws FileNotFoundException
     */
    public void setup(String difficulty, String gamemode) throws FileNotFoundException {
    	// all the necessary assets needed to load
    	Image imgUsr = new Image(getClass().getResource("/assets/User.png").toString());
    	Image enemyBullet = new Image(getClass().getResource("/assets/FireBall.png").toString());
    	Image bullet = new Image(getClass().getResource("/assets/Bullet.png").toString());
    	Image background = new Image(getClass().getResourceAsStream("/assets/GameBackground.png"));
    	Image imgEnemy = new Image(getClass().getResourceAsStream("/assets/Enemy.png"));
    	Image imgFlash = new Image(getClass().getResourceAsStream("/assets/Flash.png"));
    	
    	// sets all the assets 
    	backgroundImage.setPreserveRatio(false);
    	backgroundImage.setImage(background);
    	character.setImage(imgUsr);
    	projectile.setImage(bullet);
    	flashImage.setImage(imgFlash);
    	
    	// passes the difficulty and gamemode to enemy setup
    	enemySetup(difficulty,gamemode);
    	
    	// handles the singleton instances of Player and UserProjectile
    	Player.getInstance().linkWithController(character);
    	UserProjectile.getInstance().linkWithController(projectile);
    	Player.getInstance().reset();
    	
    	// creates the enemies and projectiles
    	createEnemies(numOfEnemies, imgEnemy);
    	createEnemyProjectile(numOfFireballs,enemyBullet);
    	
    	// starts AnimationTimer concurrently.
    	start();
    }
    
    /**
     * This method sets up the enemies and enemy projectiles.
     * @param difficulty which adjusts enemy speed, number of projectiles, speed or projetiles.
     * @param gamemode which adjusts whether or not zombies appear, projectiles appear, or both appear.
     */
    private void enemySetup(String difficulty, String gamemode) {
    	// all numbers are may seem arbitrary but have been fine tuned to give off proper difficulty.
    	
    	// easy case
    	if(difficulty.equalsIgnoreCase("Easy")) {
    		numOfEnemies = 3;
    		numOfFireballs = 3;
    		enemyMovementSpeed = 0.75;
    		fireballVelocity = 4;	
    	}
    	
    	// normal case
    	else if(difficulty.equalsIgnoreCase("Normal")) {
    		numOfEnemies = 5;
    		numOfFireballs = 5;
    		enemyMovementSpeed = 1.75;
    		fireballVelocity = 5;
    	}
    	
    	// hard case
    	else if(difficulty.equalsIgnoreCase("Hard")) {
    		numOfEnemies = 7;
    		numOfFireballs = 8;
    		enemyMovementSpeed = 3;
    		fireballVelocity = 6;
    	}
    	
    	// zombies only
    	if(gamemode.equalsIgnoreCase("Zombies Only")) {
    		numOfFireballs = 0;
    	}
    	
    	// fireballs only, has extra conditionals
    	else if(gamemode.equalsIgnoreCase("Fireballs Only")) {
    		numOfEnemies = 0;
    		
    		// additional fireballs must be added to keep this gamemode exciting.
    		
    		if(difficulty.equalsIgnoreCase("Easy")) {
        		numOfFireballs += 7;
    		}
    		if(difficulty.equalsIgnoreCase("Normal")) {
        		numOfFireballs += 12;
    		}
    		if(difficulty.equalsIgnoreCase("Hard")) {
    			numOfFireballs += 14;
        		fireballVelocity += 1;
    		}
    	}
    }
    
    /**
     * This method creates the zombies on screen. Limited by numOfEnemies.
     * @param amount which is based of numOfEnemies.
     * @param image which is just the ImageView for the zombie.
     */
    private void createEnemies(int amount, Image image) {
    	for(int index = 0; index < amount; index++) {
    		// sets image
    		ImageView imageView = new ImageView();
    		imageView.setFitHeight(enemySize);
    		imageView.setFitWidth(enemySize);
    		imageView.setImage(image);
    		
    		// sets movement speed and adds the zombie onto the gamePane to be visible.
    		gamePane.getChildren().add(imageView);
    		enemyList.add(new Enemy(enemyMovementSpeed));
    		enemyList.get(index).linkWithController(imageView);
    	}
    }
    
    /**
     * This method creates the enemy projectiles. Limited by numOfFireballs.
     * @param amount which is based of numOfFireballs.
     * @param image which is just the ImageView for the zombie.
     */
    private void createEnemyProjectile(int amount, Image image) {
    	for(int index = 0; index < amount; index++) {
    		// sets image
    		ImageView imageView = new ImageView();
    		imageView.setFitHeight(fireballSize);
    		imageView.setFitWidth(fireballSize);
    		imageView.setImage(image);
    		
    		// sets projectile speed and adds the projectile onto the gamePane to be visible.
    		gamePane.getChildren().add(imageView);
    		enemyProjectileList.add(new EnemyProjectile(fireballVelocity));
    		enemyProjectileList.get(index).linkWithController(imageView);
    		enemyProjectileList.get(index).respawn();
    	}
    }
    
    /**
     * Event listener for when the user presses a key.
     * @param key which is just the key pressed.
     */
	public void keyAction(KeyEvent key) {
		// checks if the game is not paused.
		if(!isPaused) {
			// movement keys. sets the direction velocity by 3 or -3.
			if(key.getCode().toString().equalsIgnoreCase("W")) {
				Player.getInstance().setVelocityY(-3);
			}
			if(key.getCode().toString().equalsIgnoreCase("S")) {
				Player.getInstance().setVelocityY(3);
			}
			if(key.getCode().toString().equalsIgnoreCase("A")) {
				Player.getInstance().setVelocityX(-3);
			}
			if(key.getCode().toString().equalsIgnoreCase("D")) {
				Player.getInstance().setVelocityX(3);
			}
			// ability key, which is just for flash.
			if(key.getCode().toString().equalsIgnoreCase("F")&&!Player.getInstance().isFlashed()) {
				Player.getInstance().setVelocity(200);
				Player.getInstance().setFlashed(true);
				
				// haves the opacity from 100% to 50% to show it is under cooldown.
				flashImage.setOpacity(0.5);
				cooldownText.setVisible(true);
				
				// plays sound and sets timer to 16.
				AudioClip flashSFX = new AudioClip(getClass().getResource("/assets/FlashSFX.mp3").toExternalForm());
				flashSFX.setVolume(0.50);
				flashSFX.play();
				cooldownTextInt = 16;
			}	
		}
		
		// pause the game by presing ESC.
		if(key.getCode().toString().equalsIgnoreCase("ESCAPE")) {
			paused.setVisible(!paused.isVisible());
			if(paused.isVisible()) {
				isPaused = true;
				stop();
			}
			else {
				isPaused = false;
				start();	
			}
		}
	}
	
	/**
	 * Event listener for when the user moves their mouse. When mouse is moved,
	 * the player sprite rotates and faces where the mouse is. 
	 * @param mouse which is quite literally the mouse.
	 */
    @FXML
    void mouseMoved(MouseEvent mouse) {
    	if(!isPaused) {
        	mousePosX = mouse.getX();
        	mousePosY = mouse.getY();
        	Player.getInstance().setAngle( mouse.getX(), mouse.getY());
        	Player.getInstance().getImage().setRotate(Math.toDegrees(Player.getInstance().getAngle())+90);	
    	}
    }
    
    /**
     * Event listener for when the user clicks to shoots. When the user is clicked,
     * a projectile is fired at the direction of the mouse.
     * @param mouse which is quite literally the mouse.
     */
    @FXML
    void mouseClicked(MouseEvent mouse) {
    	if(!isPaused && shot == false) {
    		// plays gun sound
			AudioClip gunshot = new AudioClip(getClass().getResource("/assets/Gunshot.mp3").toExternalForm());
			gunshot.setVolume(0.025);
			gunshot.play();
			shot = true;
			
			// makes the projectile move
			UserProjectile.getInstance().setVelocity(30);
			UserProjectile.getInstance().setToCharacter(Player.getInstance().getXCoord(), Player.getInstance().getYCoord());
			UserProjectile.getInstance().getImage().setVisible(shot);
			UserProjectile.getInstance().setAngle(mousePosX,mousePosY);	
    	}
    }
    
    /**
     * Event listener for when the user releases the key they are pressing. The user sprite is then prompted to stop moving.
     * If this method is not implemented, the user sprite will always continue to move whatever the last direction the player pressed.
     * @param key which is just the key pressed.
     */
	public void release(KeyEvent key) {
		// Remove Velocity
		if(key.getCode().toString().equalsIgnoreCase("W")) {
			Player.getInstance().setVelocityY(0);
		}
		if(key.getCode().toString().equalsIgnoreCase("S")) {
			Player.getInstance().setVelocityY(0);
		}
		if(key.getCode().toString().equalsIgnoreCase("A")) {
			Player.getInstance().setVelocityX(0);
		}
		if(key.getCode().toString().equalsIgnoreCase("D")) {
			Player.getInstance().setVelocityX(0);
		}
	}
	
	/**
	 * From ApplicationTimer, just handles the ticks to be invoked 60 times per second.
	 */
	@Override
	public void handle(long arg0) {
		tick();	
	}	
}