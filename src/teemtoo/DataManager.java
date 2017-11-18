package teemtoo;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anthony Morrell
 * @since 11/17/2017
 */
public final class DataManager {

    private AbstractTracker currentTracker;
    private AbstractTracker trackerChain;

    private static DataManager instance = new DataManager();

    private DataManager() {
        //Create Chain of Responsibility
        trackerChain = new StepTracker();
        AbstractTracker bpm = new HeartbeatTracker();
        AbstractTracker calorie = new CalorieTracker();
        AbstractTracker sleep = new SleepTracker();

        trackerChain.setNextTracker(bpm);
        bpm.setNextTracker(calorie);
        calorie.setNextTracker(sleep);

        currentTracker = trackerChain;
    }


    public void nextTracker() {
        currentTracker = currentTracker.getNextTracker();
    }

    public void previousTracker() {
        currentTracker = currentTracker.getNextTracker();
    }


    public AbstractTracker getCurrentTracker() {
        return currentTracker;
    }

    public static DataManager getInstance() {
        return instance;
    }

}
