package teemtoo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import teemtoo.logic.Controller;
import teemtoo.logic.DataManager;
import teemtoo.sensor.Sensor;

import java.io.IOException;
import java.net.URL;

/**
 * @since 11/17/2017
 */
public class Main extends Application {

    private static final String TITLE = "Activity Tracker Emulator";

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start point for the application.
     * @param primaryStage the main window
     */
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(getFXML(), Controller.WIDTH, Controller.HEIGHT);
        scene.setOnKeyPressed(Controller.getInstance()::handleKeyboard);
        primaryStage.setScene(scene);
        primaryStage.setTitle(TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
        getMainTimer().start();
    }

    /**
     * Parse the FXML file and build a window out of it.
     * @return a Pane built from the FXML file
     */
    private static Pane getFXML() {
        try {
            URL resource = Main.class.getResource(Controller.FXML_NAME);
            return FXMLLoader.load(resource);
        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Failed to load FXML data: " + e.getMessage());
            error.showAndWait();
            System.exit(1);
            return new Pane(); // to make the IDE happy
        }
    }

    /**
     * The main clock, which polls the sensors 60 times each second.
     * @return the main clock
     */
    private static AnimationTimer getMainTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                DataManager.getInstance().getSensors().forEach(Sensor::poll);
            }
        };
    }

}
