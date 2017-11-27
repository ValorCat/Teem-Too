package teemtoo.tracker;

import javafx.beans.value.ObservableStringValue;
import teemtoo.event.Event;

/**
 * @since 11/17/2017
 */
public abstract class Tracker<NumType extends Number> {

    private String label;
    private Tracker previous, next;
    private boolean isEndOfChain;
    protected DataLog<NumType> log;

    public Tracker(String label) {
        this.label = label;
        this.log = new DataLog<>();
    }

    public abstract ObservableStringValue getValue();
    protected abstract boolean canHandle(Event event);
    protected abstract void handle(Event event);
    protected abstract void saveAndReset();

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
