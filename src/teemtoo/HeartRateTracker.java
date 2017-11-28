package teemtoo;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableStringValue;
import teemtoo.event.Event;

/**
 * @since 11/17/2017
 */
public class HeartRateTracker extends Tracker<Double> {

    private DoubleProperty bpm;

    public HeartRateTracker() {
        super("Heart Rate");
        bpm = new SimpleDoubleProperty(80);
    }

    @Override
    public ObservableStringValue getValue() {
        return Bindings.format("%.1f", bpm);
    }

    @Override
    protected boolean canHandle(Event event) {
        return event.getBPMData() != Event.NO_DATA;
    }

    @Override
    protected void handle(Event event) {
        // todo determine best way to calculate bpm
        bpm.set(event.getBPMData());
    }

    @Override
    protected void saveAndReset() {
        // todo determine how to save average bpm
    }

}
