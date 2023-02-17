package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;

/**
 * Creates the menu to pick game difficulty,
 * game mode, and start the game itself
 * @author James Khalil and Joshua Tolentino
 *
 */
public class MenuController {
	
	// JavaFX Node Instance Variables
    @FXML
    private Pane viewPane;

    @FXML
    private Button startGameButton;
    
    @FXML
    private ComboBox<String> difficulty;
    
    @FXML
    private ComboBox<String> gamemode;
    
    @FXML
    private MediaView videoView;
    
    @FXML
    private ImageView titleImage;
    
    //Instance variables
    private GameApp app;
    private MediaPlayer vid;
    private AudioClip menuSong;

    /**
     * Starts the game when clicked
     * @param event
     */
    @FXML
    void startGameButtonClickeed(ActionEvent event) {
    	app.gameView(difficulty.getValue(),gamemode.getValue());
    	vid.stop();
    	menuSong.stop();
    }
    
    /**
     * Link with the GameApp.java instance so gameView() can be called.
     * @param app
     */
    void linkWithApp(GameApp app) {
    	this.app = app;
    }
    
    /**
     * Creates all the buttons and 
     * text in the menu
     */
    public void setupMenu() {
    	gamemode.getItems().addAll("Fireballs Only","Zombies Only","Default");
    	difficulty.getItems().addAll("Easy","Normal","Hard");
    	difficulty.setValue("Normal");
    	gamemode.setValue("Default");
        
        Image img = new Image(getClass().getResource("/assets/Title.png").toString());
        titleImage.setImage(img);
    	
        vid = new MediaPlayer(new Media(this.getClass().getResource("/assets/MenuVideo.mp4").toString()));
        vid.setCycleCount(MediaPlayer.INDEFINITE);
        vid.play();
        videoView.setMediaPlayer(vid);
        
		menuSong = new AudioClip(getClass().getResource("/assets/MenuSong.mp3").toExternalForm());
		menuSong.setCycleCount(MediaPlayer.INDEFINITE);
		menuSong.setVolume(0.50);
		menuSong.play();
    }
}
