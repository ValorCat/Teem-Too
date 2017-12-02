package teemtoo.logic;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import teemtoo.event.Event;
import teemtoo.event.ResetEvent;
import teemtoo.sensor.Pedometer;
import teemtoo.sensor.PulseReader;
import teemtoo.sensor.Sensor;
import teemtoo.tracker.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * @since 11/17/2017
 */
public final class DataManager {

    private static final LocalTime RESET_TIME = LocalTime.MIDNIGHT;
    private static DataManager instance = new DataManager();

    private ObjectProperty<Tracker> current;
    private Tracker chain;
    private List<Sensor> sensors;

    private DataManager() {
        setupTrackers();
        sensors = Arrays.asList(new Pedometer(), new PulseReader());
        current = new SimpleObjectProperty<>(chain);
    }

    /**
     * Poll the sensors and check if it's time to reset.
     */
    public void update() {
        sensors.forEach(Sensor::poll);
        if (LocalTime.now().equals(RESET_TIME)) {
            handle(new ResetEvent());
        }
    }

    /**
     * Pass an event into the chain of responsibility to be processed by its appropriate tracker.
     * @param event the event to be processed
     */
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

    public DataLog getCurrentLog() {
        return current.get().getLog();
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
        assert instance != null;
        return instance;
    }

}
