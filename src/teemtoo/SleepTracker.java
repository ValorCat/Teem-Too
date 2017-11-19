package teemtoo;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import teemtoo.event.Event;

/**
 * @author Anthony Morrell
 * @since 11/19/2017
 */
public class SleepTracker extends Tracker {

    public SleepTracker() {
        super("Time Awake");
    }

    @Override
    public StringExpression getTotal() {
        return Bindings.concat("WIP");
    }

    @Override
    public void handleData(Event event) {
        long fallAsleepTime = event.getFallAsleepTime();
        long awakenTime = event.getAwakenTime();
        if (fallAsleepTime != Event.NO_DATA) {
            // todo go to sleep
        } else if (awakenTime != Event.NO_DATA) {
            // todo wake up
        } else {
            forwardData(event);
        }
    }

}
