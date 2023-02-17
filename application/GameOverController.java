package application;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * This class handles the game over screen for the game.
 * @author Joshua Tolentino and James Khalil
 *
 */
public class GameOverController {
	// JavaFX Node variables
    @FXML
    private Button returnTitleButton;
    
    @FXML
    private Pane mainPane;

    @FXML
    private Text scoreText;
    
    @FXML
    private ImageView gameOverImage;
    
    private GameApp app;

    /**
     * Sets up the game over screen, score is passed in from GameController
     * @param score
     * @throws IOException
     */
    public void setup(int score) throws IOException {
    	// loads image
        Image img = new Image(getClass().getResource("/assets/GameOverText.png").toString());
        gameOverImage.setImage(img);
        
        // makes everything invisible
        gameOverImage.setVisible(false);
    	scoreText.setVisible(false);
    	returnTitleButton.setVisible(false);
    	
    	// black background
    	mainPane.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,Insets.EMPTY)));
    	fadeIn(mainPane);
    	scoreText.setText("SCORE: "+score);
    }
    
    /**
     * Fades in the game over.
     * @param node
     */
    public void fadeIn(Node node) {
    	FadeTransition fadeTransition = new FadeTransition();
    	fadeTransition.setDuration(Duration.seconds(1.5));
    	fadeTransition.setNode(node);
    	fadeTransition.setFromValue(0);
    	fadeTransition.setToValue(1);
    	fadeTransition.play();
    	
    	// when the fading is done
    	fadeTransition.setOnFinished((ActionEvent event) -> {
        	scoreText.setVisible(true);
        	returnTitleButton.setVisible(true);
        	gameOverImage.setVisible(true);
    	});
    }
    
    /**
     * Link with the GameApp.java instance so menuView() can be called.
     * @param app
     */
    public void linkWithApp(GameApp app) {
    	this.app = app;
    }
    
    /**
     * Event listener for when the user clicks on "Return to Title Screen"
     * MenuView is then invoked.
     * @param event
     */
    @FXML
    void returnButtonClicked(ActionEvent event) {
    	app.menuView();
    }
    
}
