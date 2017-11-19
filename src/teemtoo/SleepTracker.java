package teemtoo;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;

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
    public void handleData() {

    }

}
