package teemtoo.logic;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import teemtoo.event.CalorieEvent;
import teemtoo.event.SleepEvent;
import teemtoo.event.StepEvent;
import teemtoo.tracker.DataLog;
import teemtoo.tracker.HeartRateTracker;
import teemtoo.tracker.Tracker;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @since 11/17/2017
 */
public class Controller implements Initializable {

    //Define constants (window dimensions, color, etc...)
    public static final int WIDTH = 300, HEIGHT = 200;
    public static final String FXML_NAME = "activity-tracker.fxml";
    private static final String TIME_FORMAT = "h:mm a";
    private static final int[] CALORIE_INTAKE_LEVELS = {1, 10, 50};

    private static final String DAY_MODE_COLOR = "EAEAEA";
    private static final String NIGHT_MODE_COLOR = "0A5787";
    private static final String CURRENT_VALUE_DAY_COLOR = "805C7E";
    private static final String CURRENT_VALUE_NIGHT_COLOR = "456F9A";

    //Singleton instance
    private static Controller instance;

    @FXML private Pane background;
    @FXML private Button menuButton;
    @FXML private Label clock;
    @FXML private Label currentValue;
    @FXML private Label currentLabel;
    @FXML private Separator separator;
    @FXML private Button addCaloriesButton;
    @FXML private Slider addCalorieSlider;
    @FXML private Button sleepButton;
    @FXML private ListView<String> stats;
    @FXML private Button clearButton;

    //Sleep mode and stats menu states
    private BooleanProperty inSleepMode = new SimpleBooleanProperty();
    private BooleanProperty inStatsMenu = new SimpleBooleanProperty();
    private int calorieIntakeAmount = CALORIE_INTAKE_LEVELS[1];

    private ImageView menuIcon = getImage("hamburger", 40, 40);
    private ImageView backIcon = getImage("back", 35, 35);
    private ImageView moonIcon = getImage("moon", 40, 35);
    private ImageView sunIcon = getImage("sunrise", 40, 35);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        setupClock();
        setupCalories();
        setupSleep();
        setupMenu();
        updateTracker();
        setBackgroundColor(DAY_MODE_COLOR);
        setTextColor(currentValue, CURRENT_VALUE_DAY_COLOR);
        currentLabel.visibleProperty().bind(inSleepMode.not());
        separator.visibleProperty().bind(inSleepMode.not());
    }

    //Switch to tracker to the "left"
    //(moveLeft() and moveRight() will only work if the user is not asleep)
    public void moveLeft() {
        if (!inSleepMode.get()) {
            DataManager.getInstance().previousTracker();
            updateTracker();
        }
        if (isInStatsMenu()) {
            updateStatsMenu();
        }
    }

    //Switch to tracker to the "right"
    public void moveRight() {
        if (!inSleepModeProperty().get()) {
            DataManager.getInstance().nextTracker();
            updateTracker();
        }
        if (isInStatsMenu()) {
            updateStatsMenu();
        }
    }

    //Opens stats menu
    public void toggleStatsMenu() {
        inStatsMenu.set(!isInStatsMenu());
        menuButton.setGraphic(isInStatsMenu() ? backIcon : menuIcon);
        if (isInStatsMenu()) {
            updateStatsMenu();
        }
    }

    private void updateStatsMenu() {
        DataLog log = DataManager.getInstance().getCurrentLog();
        stats.getItems().clear();
        if (DataManager.getInstance().getCurrentTracker() instanceof HeartRateTracker) // todo fix up instanceof
        {
            stats.getItems().addAll(
                    "Last minute:  " + log.getLastDataPoint(),
                    "Last 5 minutes:  " + log.getAverage(5),
                    "Last 15 minutes:  " + log.getAverage(15),
                    "Last 30 minutes:  " + log.getAverage(30)
            );
        } else {
            stats.getItems().addAll(
                    "Yesterday:  " + log.getLastDataPoint(),
                    "Last 3 days:  " + log.getAverage(3),
                    "Last week:  " + log.getAverage(7),
                    "Last month:  " + log.getAverage(30)
            );
        }
    }

    public void addCalories() {
        DataManager.getInstance().handle(new CalorieEvent(calorieIntakeAmount));
    }

    public void toggleSleepMode() {
        boolean asleep = isInSleepMode();
        sleepButton.setGraphic(asleep ? moonIcon : sunIcon);
        setTextColor(currentValue, asleep ? CURRENT_VALUE_DAY_COLOR : CURRENT_VALUE_NIGHT_COLOR);
        setBackgroundColor(asleep ? DAY_MODE_COLOR : NIGHT_MODE_COLOR);
        DataManager.getInstance().handle(new SleepEvent());
    }

    public BooleanProperty inSleepModeProperty() {
        return inSleepMode;
    }

    public void handleKeyboard(KeyEvent event) {
        Tracker current = DataManager.getInstance().getCurrentTracker();
        switch (event.getCode()) {
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            case ENTER:
                toggleStatsMenu();
                break;
            case UP:
                if (current.showCalorieInput()) {
                    addCalorieSlider.increment();
                }
                break;
            case DOWN:
                if (current.showCalorieInput()) {
                    addCalorieSlider.decrement();
                }
                break;
            case SHIFT:
                if (current.showCalorieInput()) {
                    addCalories();
                } else if (current.showSleepButton()) {
                    toggleSleepMode();
                }
                break;
            case S:
                DataManager.getInstance().handle(new StepEvent());
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
        ObservableBooleanValue areCaloriesShowing = DataManager.getInstance().showCalorieInput();
        setVisibility(addCaloriesButton, areCaloriesShowing);
        setVisibility(addCalorieSlider, areCaloriesShowing);
        addCalorieSlider.setMax(CALORIE_INTAKE_LEVELS.length - 1);
        addCalorieSlider.valueProperty().addListener((obs, old, value) -> {
            int intakeAmount = CALORIE_INTAKE_LEVELS[value.intValue()];
            addCaloriesButton.setText("+" + intakeAmount);
            calorieIntakeAmount = intakeAmount;
        });
    }

    private void setupSleep() {
        sleepButton.setGraphic(moonIcon);
        setVisibility(sleepButton, DataManager.getInstance().showSleepButton());
    }

    private void setupMenu() {
        menuButton.setGraphic(menuIcon);
        menuButton.disableProperty().bind(inSleepMode);
        setVisibility(stats, inStatsMenu);
        stats.setStyle("-fx-font: 15pt System");
    }

    private void updateTracker() {
        Tracker tracker = DataManager.getInstance().getCurrentTracker();
        currentLabel.setText(tracker.getLabel());
        currentValue.textProperty().bind(tracker.getValue());
    }

    private void setBackgroundColor(String hexColor) {
        background.setStyle("-fx-background-color: #" + hexColor);
    }

    private static void setTextColor(Node node, String hexColor) {
        node.setStyle("-fx-text-fill: #" + hexColor);
    }

    public static Controller getInstance() {
        return instance;
    }

    private static ImageView getImage(String name, int width, int height) {
        assert !name.endsWith(".png");
        ImageView view = new ImageView(new Image(getResource(name + ".png")));
        view.setFitWidth(width);
        view.setFitHeight(height);
        return view;
    }

    private static InputStream getResource(String name) {
        return Controller.class.getResourceAsStream(name);
    }

    private static void setVisibility(Node node, ObservableBooleanValue source) {
        node.visibleProperty().bind(source);
    }

    private boolean isInSleepMode() {
        return inSleepMode.get();
    }

    private boolean isInStatsMenu() {
        return inStatsMenu.get();
    }

}
