package teemtoo;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import teemtoo.event.Event;

/**
 * @author Anthony Morrell
 * @since 11/19/2017
 */
public class HeartRateTracker extends Tracker {

    private DoubleProperty bpm;

    public HeartRateTracker() {
        super("Heart Rate");
        bpm = new SimpleDoubleProperty();
    }

    @Override
    public StringExpression getTotal() {
        return bpm.asString();
    }

    @Override
    public void handleData(Event event) {
        double data = event.getBPMData();
        if (data == Event.NO_DATA) {
            forwardData(event);
        } else {
            updateBPM(data);
        }
    }

    private void updateBPM(double newData) {
        // todo determine best way to calculate bpm
        bpm.set(newData);
    }

}
