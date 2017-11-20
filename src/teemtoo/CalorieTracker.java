package teemtoo;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import teemtoo.event.Event;

/**
 * @since 11/18/2017
 */
public class CalorieTracker extends Tracker<Integer> {

    private IntegerProperty calories;

    public CalorieTracker() {
        super("Calories Today");
        calories = new SimpleIntegerProperty(0);
    }

    @Override
    public StringExpression getTotal() {
        return calories.asString();
    }

    @Override
    public void handleData(Event event) {
        super.handleData(event);
        int intake = event.getCalorieData();
        if (intake == Event.NO_DATA) {
            forwardData(event);
        } else {
            addCalories(intake);
        }
    }

    @Override
    public void saveAndReset() {
        log.update(calories.get());
        calories.set(0);
    }

    @Override
    public boolean showCalorieInput() {
        return true;
    }

    private void addCalories(int amount) {
        calories.set(calories.get() + amount);
    }

}
