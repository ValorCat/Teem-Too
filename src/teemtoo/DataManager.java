package teemtoo;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import teemtoo.event.Event;

import java.util.Arrays;
import java.util.List;

/**
 * @since 11/17/2017
 */
public final class DataManager {

    private static DataManager instance = new DataManager();

    private ObjectProperty<Tracker> current;
    private Tracker chain;
    private List<Sensor> sensors;

    private DataManager() {
        setupTrackers();
        sensors = Arrays.asList(new Pedometer(), new PulseReader());
        current = new SimpleObjectProperty<>(chain);
    }

    public void handle(Event event) {
        chain.attemptToHandle(event);
    }

    public void nextTracker() {
        current.set(current.get().getNext());
    }

    public void previousTracker() {
        current.set(current.get().getPrevious());
    }

    public Tracker getCurrentTracker() {
        return current.get();
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public ObservableBooleanValue showCalorieInput() {
        return Bindings.createBooleanBinding(() -> current.get().showCalorieInput(), current);
    }

    public ObservableBooleanValue showSleepButton() {
        return Bindings.createBooleanBinding(() -> current.get().showSleepButton(), current);
    }

    private void setupTrackers() {
        StepTracker steps = new StepTracker();
        HeartRateTracker heart = new HeartRateTracker();
        CalorieTracker calories = new CalorieTracker();
        SleepTracker sleep = new SleepTracker();

        steps.link(heart);
        heart.link(calories);
        calories.link(sleep);
        sleep.link(steps);
        sleep.setEndOfChain();
        chain = steps;
    }

    public static DataManager getInstance() {
        return instance;
    }

}
