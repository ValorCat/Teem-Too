package teemtoo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableStringValue;
import teemtoo.event.Event;

/**
 * @since 11/18/2017
 */
public class CalorieTracker extends Tracker<Integer> {

    private IntegerProperty calories;

    public CalorieTracker() {
        super("Calories Today");
        calories = new SimpleIntegerProperty();
    }

    @Override
    public ObservableStringValue getValue() {
        return calories.asString();
    }

    @Override
    protected boolean canHandle(Event event) {
        return event.getCalorieData() != Event.NO_DATA;
    }

    @Override
    protected void handle(Event event) {
        calories.set(calories.get() + event.getCalorieData());
    }

    @Override
    protected void saveAndReset() {
        log.update(calories.get());
        calories.set(0);
    }

    @Override
    public boolean showCalorieInput() {
        return true;
    }

}
