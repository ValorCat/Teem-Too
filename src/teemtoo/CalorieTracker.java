package teemtoo;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
    public void handleData() {

    }

}
