package teemtoo;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import teemtoo.event.Event;

/**
 * @author Anthony Morrell
 * @since 11/19/2017
 */
public class StepTracker extends Tracker {

    private IntegerProperty steps;

    public StepTracker() {
        super("Steps Today");
        steps = new SimpleIntegerProperty();
    }

    @Override
    public StringExpression getTotal() {
        return steps.asString();
    }

    @Override
    public void handleData(Event event) {
        if (event.isStep()) {
            addStep();
        } else {
            forwardData(event);
        }
    }

    private void addStep() {
        steps.set(steps.get() + 1);
    }

}
