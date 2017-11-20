package teemtoo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import teemtoo.event.CalorieEvent;
import teemtoo.event.SleepEvent;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @since 11/17/2017
 */
public class Controller implements Initializable {

    private static final String TIME_FORMAT = "h:mm a";
    private static final int[] CALORIE_INTAKE_LEVELS = {1, 10, 50};
    private static final String NIGHT_MODE_BG = "5F9EA0";

    private static Controller instance;

    @FXML private Pane background;
    @FXML private Button menuButton;
    @FXML private Label clock;
    @FXML private Label currentTotal;
    @FXML private Label currentLabel;
    @FXML private Button addCaloriesButton;
    @FXML private Slider addCalorieSlider;
    @FXML private Button sleepButton;

    private BooleanProperty inSleepMode = new SimpleBooleanProperty();
    private int calorieIntakeAmount = CALORIE_INTAKE_LEVELS[1];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        setupClock();
        setupCalories();
        setupSleep();
        menuButton.setGraphic(getImage("hamburger.png", 40, 40));
        displayTracker(DataManager.getInstance().getCurrentTracker());
    }

    public void moveLeft() {
        DataManager data = DataManager.getInstance();
        data.previousTracker();
        displayTracker(data.getCurrentTracker());
    }

    public void moveRight() {
        DataManager data = DataManager.getInstance();
        data.nextTracker();
        displayTracker(data.getCurrentTracker());
    }

    public void addCalories() {
        DataManager.getInstance().handleData(new CalorieEvent(calorieIntakeAmount));
    }

    public void toggleSleepMode() {
        DataManager.getInstance().handleData(new SleepEvent());
    }

    public BooleanProperty inSleepModeProperty() {
        return inSleepMode;
    }

    public static void handleKeyboard(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                instance.moveLeft();
                break;
            case RIGHT:
                instance.moveRight();
                break;
        }
    }

    private void setupClock() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(TIME_FORMAT);
        KeyFrame frame1 = new KeyFrame(Duration.seconds(0), e -> clock.setText(LocalTime.now().format(format)));
        KeyFrame frame2 = new KeyFrame(Duration.seconds(1));
        Timeline timeline = new Timeline(frame1, frame2);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setupCalories() {
        addCaloriesButton.visibleProperty().bind(DataManager.getInstance().showCalorieInput());
        addCalorieSlider.visibleProperty().bind(DataManager.getInstance().showCalorieInput());
        addCalorieSlider.setMax(CALORIE_INTAKE_LEVELS.length - 1);
        addCalorieSlider.valueProperty().addListener((obs, old, value) -> {
            int intakeAmount = CALORIE_INTAKE_LEVELS[value.intValue()];
            addCaloriesButton.setText("+" + intakeAmount);
            calorieIntakeAmount = intakeAmount;
        });
    }

    private void setupSleep() {
        sleepButton.setGraphic(getImage("moon.png", 40, 35));
        sleepButton.visibleProperty().bind(DataManager.getInstance().showSleepButton());
        inSleepMode.addListener((obs, old, asleep) -> {
            if (asleep) {
                background.setStyle("-fx-background-color: #" + NIGHT_MODE_BG);
            } else {
                background.setStyle("-fx-background-color: #FFFFFF");
            }
        });
    }

    private void displayTracker(Tracker tracker) {
        currentLabel.setText(tracker.getLabel());
        currentTotal.textProperty().bind(tracker.getTotal());
    }

    public static Controller getInstance() {
        return instance;
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
