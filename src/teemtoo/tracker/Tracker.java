package teemtoo.tracker;

import javafx.beans.value.ObservableStringValue;
import teemtoo.event.Event;

import java.util.function.Function;

/**
 * @since 11/17/2017
 */
public abstract class Tracker<NumType extends Number> {

    public static final Function<Number,String> NUMBER_FORMATTER = value -> String.format("%.1f", value.doubleValue());

    private String label;
    private Tracker previous, next;
    private boolean isEndOfChain;
    protected DataLog<NumType> log;

    public Tracker(String label, Function<Number,String> formatter, boolean showIntIfPossible) {
        this.label = label;
        this.log = new DataLog<>(formatter, showIntIfPossible);
    }

    public abstract ObservableStringValue getValue();
    protected abstract boolean canHandle(Event event);
    protected abstract void handle(Event event);
    protected abstract void saveAndReset();

    /**
     * Handle an event if it's of the right type, or otherwise forward it on through the chain.
     * @param event the event to be handled or forwarded
     */
    public void attemptToHandle(Event event) {
        if (event.isReset()) {
            saveAndReset();
            forward(event);
        } else if (canHandle(event)) {
            handle(event);
        } else {
            forward(event);
        }
    }

    /**
     * Retrieve the text to appear below the horizontal bar on the tracker screen.
     * @return the text for the UI
     */
    public String getLabel() {
        assert label != null;
        return label;
    }

    public Tracker getPrevious() {
        return previous;
    }

    public Tracker getNext() {
        return next;
    }

    public DataLog<NumType> getLog() {
        return log;
    }

    public void link(Tracker next) {
        this.next = next;
        next.previous = this;
    }

    /**
     * Mark this tracker as the end of the chain of responsibility. Events that reach the end of the chain and cannot be
     * handled are discarded.
     */
    public void setEndOfChain() {
        isEndOfChain = true;
    }

    public boolean showCalorieInput() {
        return false;
    }

    public boolean showSleepButton() {
        return false;
    }

    private void forward(Event event) {
        if (!isEndOfChain) {
            next.attemptToHandle(event);
        }
    }

}
