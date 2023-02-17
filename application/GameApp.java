package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * This class handles the switching between the three views (.fxml files).
 * @author Joshua Tolentino and James Khalil
 *
 */
public class GameApp extends Application {
	
	// Instance variables 
	private Stage mainStage;
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 720;
	
	/**
	 * This method sets up the menu.
	 */
	public void menuView() {
		FXMLLoader loader = new FXMLLoader();
		Scene scene;
		
		try {
			// loads and sets up the view.
			Parent setupView = (Parent) loader.load(new FileInputStream("view/Menu.fxml"));
			MenuController menuController = loader.getController();
			scene = new Scene(setupView,WIDTH,HEIGHT);
			menuController.setupMenu();
			menuController.linkWithApp(this);
			
			// shows the view to the user.
			mainStage.setResizable(false);
			mainStage.setScene(scene);
			mainStage.show();
		
		}
			
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method sets up the game view, where the user plays the game.
	 * This method is also invoked by MenuController.java when the user clicks the "Start Game" button.
	 * 
	 * @param difficulty which is set from the Menu ComboBox.
	 * @param gamemode which is set from the Menu ComboBox.
	 */
	public void gameView(String difficulty, String gamemode) {
		FXMLLoader loader = new FXMLLoader();
		Scene scene;

		try {
			// loads and sets up the view.
			Parent setupView = (Parent) loader.load(new FileInputStream("view/Game.fxml"));
			GameController gameController = loader.getController();
			scene = new Scene(setupView,WIDTH,HEIGHT);
			gameController.setup(difficulty,gamemode);
			gameController.linkWithApp(this);
			
			// adds key events (key pressed, and key released) for user control.
			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> gameController.keyAction(event));
			scene.addEventFilter(KeyEvent.KEY_RELEASED, event -> gameController.release(event));
			
			// shows the view to the user.
			mainStage.setResizable(false);
			mainStage.setScene(scene);
			mainStage.show();
		}
			
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method sets up the game over screen, where the user sees their score.
	 * Score is passed from GameController. As soon as the user dies in GameView, the this method is invoked by GameController.
	 * 
	 * @param score which is the final score of the user.
	 */
	public void gameOver(int score) {
		FXMLLoader loader = new FXMLLoader();
		Scene scene;
		
		try {
			// loads and sets up the view.
			Parent setupView = (Parent) loader.load(new FileInputStream("view/GameOver.fxml"));
			GameOverController gameOverController = loader.getController();
			gameOverController.setup(score);
			gameOverController.linkWithApp(this);
			scene = new Scene(setupView,WIDTH,HEIGHT);
			
			// shows the view to the user.
			mainStage.setResizable(false);
			mainStage.setScene(scene);
			mainStage.show();
		}
			
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method is called when the application is started, and calls on menuView() 
	 * which displays the menu for the user.
	 */
	@Override
	public void start(Stage primaryStage) {
		this.mainStage = primaryStage;
		menuView();
	}
	
	/**
	 * Starting default code for JavaFX applications.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}