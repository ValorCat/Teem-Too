package teemtoo;

import javafx.beans.binding.StringExpression;
import teemtoo.event.Event;

/**
 * @since 11/17/2017
 */
public abstract class Tracker {

    private String label;
    private Tracker prev, next;
    private boolean endOfChain;

    public Tracker(String label) {
        this.label = label;
    }

    public abstract StringExpression getTotal();
    public abstract void handleData(Event event);

    public String getLabel() {
        assert label != null;
        return label;
    }

    protected void forwardData(Event event) {
        if (!endOfChain) {
            next.handleData(event);
        }
    }

    public Tracker getPrevious() {
        return prev;
    }

    public Tracker getNext() {
        return next;
    }

    public void link(Tracker next) {
        this.next = next;
        next.prev = this;
    }

    public void setEndOfChain() {
        endOfChain = true;
    }

    public boolean showCalorieInput() {
        return false;
    }

    public boolean showSleepButton() {
        return false;
    }

}
