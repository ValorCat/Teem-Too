package teemtoo;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import teemtoo.event.Event;

/**
 * @since 11/18/2017
 */
public class StepTracker extends Tracker<Integer> {

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
        super.handleData(event);
        if (event.isStep()) {
            addStep();
        } else {
            forwardData(event);
        }
    }

    @Override
    public void saveAndReset() {
        log.update(steps.get());
        steps.set(0);
    }

    private void addStep() {
        steps.set(steps.get() + 1);
    }

}
