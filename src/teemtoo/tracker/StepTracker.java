package teemtoo.tracker;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableStringValue;
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
    public ObservableStringValue getValue() {
        return steps.asString();
    }

    @Override
    protected boolean canHandle(Event event) {
        return event.isStep();
    }

    @Override
    protected void handle(Event event) {
        steps.set(steps.get() + 1);
    }

    @Override
    protected void saveAndReset() {
        log.update(steps.get());
        steps.set(0);
    }

}
