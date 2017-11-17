package teemtoo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author Anthony Morrell
 * @since 11/17/2017
 */
public class Controller implements Initializable {

    private static final String TIME_FORMAT = "h:mm a";

    @FXML private Label clock;
    @FXML private Button menuButton;
    @FXML private Button leftButton;
    @FXML private Button rightButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupClock();
        menuButton.setGraphic(getImage("hamburger.png", 30, 30));
    }

    public void moveLeft() {

    }

    public void moveRight() {

    }

    private void setupClock() {
        // todo ensure clock displays correctly when hour has 2 digits
        DateTimeFormatter format = DateTimeFormatter.ofPattern(TIME_FORMAT);
        KeyFrame frame1 = new KeyFrame(Duration.seconds(0), e -> clock.setText(LocalTime.now().format(format)));
        KeyFrame frame2 = new KeyFrame(Duration.seconds(1));
        Timeline timeline = new Timeline(frame1, frame2);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private static ImageView getImage(String name, int width, int height) {
        ImageView view = new ImageView(new Image(getResource(name)));
        view.setFitWidth(width);
        view.setFitHeight(height);
        return view;
    }

    private static InputStream getResource(String name) {
        return Controller.class.getResourceAsStream(name);
    }

}
