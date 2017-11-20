package teemtoo;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ObservableStringValue;
import teemtoo.event.Event;
import teemtoo.event.ResetEvent;

import java.util.concurrent.TimeUnit;

/**
 * @since 11/18/2017
 */
public class SleepTracker extends Tracker<Long> {

    private static final int MIN_SLEEP_THRESHOLD = 5000;

    private LongProperty lastFallAsleepTime;
    private LongProperty lastDuration;
    private BooleanProperty inSleepMode;

    public SleepTracker() {
        super("Sleep Duration");
        lastFallAsleepTime = new SimpleLongProperty(-1);
        lastDuration = new SimpleLongProperty();
        inSleepMode = new SimpleBooleanProperty();
        Controller.getInstance().inSleepModeProperty().bindBidirectional(inSleepMode);
    }

    @Override
    public ObservableStringValue getValue() {
        return Bindings.createStringBinding(() -> {
            if (inSleepMode.get()) {
                // currently asleep
                return "ZZZ...";
            } else if (lastFallAsleepTime.get() == -1) {
                // no prior sleep data
                return "---";
            } else {
                // has slept before
                long duration = lastDuration.get();
                long hours = TimeUnit.MILLISECONDS.toHours(duration);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
                return String.format("%2d:%02d", hours, minutes - TimeUnit.MINUTES.toMinutes(hours));
            }
        }, lastFallAsleepTime, lastDuration, inSleepMode);
    }

    @Override
    protected boolean canHandle(Event event) {
        return event.getSleepTimestamp() != Event.NO_DATA;
    }

    @Override
    protected void handle(Event event) {
        boolean asleep = inSleepMode.get();
        if (asleep) {
            long duration = System.currentTimeMillis() - lastFallAsleepTime.get();
            if (duration > MIN_SLEEP_THRESHOLD) {
                lastDuration.set(duration);
                // todo add confirmation dialog
                DataManager.getInstance().handle(new ResetEvent());
            }
        } else {
            lastFallAsleepTime.set(event.getSleepTimestamp());
        }
        inSleepMode.set(!asleep);
    }

    @Override
    protected void saveAndReset() {
        log.update(lastDuration.get());
    }

    @Override
    public boolean showSleepButton() {
        return true;
    }

}
