package teemtoo.tracker;

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
        super("Heart Rate", Tracker.NUMBER_FORMATTER, false);
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


    //The heart tracker will supposedly receive a bpm event every 2 seconds
    @Override
    protected void handle(Event event) {

        bpm.set(event.getBPMData());
    }

    @Override
    protected void saveAndReset() {
        log.update(bpm.getValue());
    }

}
