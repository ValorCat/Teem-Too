package teemtoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Anthony Morrell
 * @since 11/17/2017
 */
public class Main extends Application {

    private static final int WIDTH = 300, HEIGHT = 200;
    private static final String FXML_NAME = "activity-tracker.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane window = FXMLLoader.load(Main.class.getResource(FXML_NAME));
        Scene scene = new Scene(window, WIDTH, HEIGHT);
        scene.setOnKeyPressed(Controller::handleKeyboard);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
