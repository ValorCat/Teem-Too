package teemtoo;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import teemtoo.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/17/2017
 */
public final class DataManager {

    private Tracker chain;
    private ObjectProperty<Tracker> current;
    private List<Sensor> sensors;

    private static DataManager instance = new DataManager();

    private DataManager() {
        setupTrackers();
        setupSensors();
        current = new SimpleObjectProperty<>(chain);
    }

    public void handleData(Event event) {
        chain.handleData(event);
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

    public ObjectProperty<Tracker> currentTrackerProperty() {
        return current;
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
        Tracker steps = new StepTracker();
        Tracker heart = new HeartRateTracker();
        Tracker calories = new CalorieTracker();
        Tracker sleep = new SleepTracker();

        steps.link(heart);
        heart.link(calories);
        calories.link(sleep);
        sleep.link(steps);
        sleep.setEndOfChain();

        chain = steps;
    }

    private void setupSensors() {
        sensors = new ArrayList<>();
        sensors.add(new Pedometer());
        sensors.add(new PulseReader());
    }

    public static DataManager getInstance() {
        return instance;
    }

}
