package teemtoo;

import teemtoo.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/17/2017
 */
public final class DataManager {

    private Tracker chain, current;
    private List<Sensor> sensors;

    private static DataManager instance = new DataManager();

    private DataManager() {
        setupTrackers();
        setupSensors();
        current = chain;
    }

    public void handleData(Event event) {
        chain.handleData(event);
    }

    public void nextTracker() {
        current = current.getNext();
    }

    public void previousTracker() {
        current = current.getPrevious();
    }

    public Tracker getCurrentTracker() {
        return current;
    }

    public List<Sensor> getSensors() {
        return sensors;
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
