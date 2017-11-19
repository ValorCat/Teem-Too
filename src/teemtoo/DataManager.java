package teemtoo;

/**
 * @author Anthony Morrell
 * @since 11/17/2017
 */
public final class DataManager {

    private Tracker chain;
    private Tracker current;

    private static DataManager instance = new DataManager();

    private DataManager() {
        setupChain();
        current = chain;
    }

    private void setupChain() {
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

    public void nextTracker() {
        current = current.getNext();
    }

    public void previousTracker() {
        current = current.getPrevious();
    }

    public Tracker getCurrentTracker() {
        return current;
    }

    public static DataManager getInstance() {
        return instance;
    }

}
