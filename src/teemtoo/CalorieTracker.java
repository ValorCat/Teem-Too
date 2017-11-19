package teemtoo;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import teemtoo.event.Event;

/**
 * @author Anthony Morrell
 * @since 11/19/2017
 */
public class CalorieTracker extends Tracker {

    private IntegerProperty calories;

    public CalorieTracker() {
        super("Calorie Intake Today");
        calories = new SimpleIntegerProperty();
    }

    @Override
    public StringExpression getTotal() {
        return calories.asString();
    }

    @Override
    public void handleData(Event event) {
        int intake = event.getCalorieData();
        if (intake == Event.NO_DATA) {
            forwardData(event);
        } else {
            addCalories(intake);
        }
    }

    private void addCalories(int amount) {
        calories.set(calories.get() + amount);
    }

}
