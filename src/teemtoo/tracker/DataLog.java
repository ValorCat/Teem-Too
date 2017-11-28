package teemtoo.tracker;

import java.util.Deque;
import java.util.LinkedList;
import java.util.OptionalDouble;

/**
 * @since 11/18/2017.
 */
public class DataLog<NumType extends Number> {

    private static final int FULL_SIZE = 30;
    private static final String EMPTY_VALUE = "---";

    private Deque<NumType> month = new LinkedList<>();

    public String getLastDay() {
        return month.isEmpty() ? EMPTY_VALUE : month.getLast().toString();
    }

    public String getAverage(int numDays) {
        OptionalDouble average = getAverageRaw(numDays);
        String output;
        if (average.isPresent()) {
            output = String.format("%.1f", average.getAsDouble());
            if (output.endsWith(".0")) {
                output = output.substring(0, output.length() - 2);
            }
        } else {
            output = EMPTY_VALUE;
        }
        return output;
    }

    public void update(NumType value) {
        if (isFull()) {
            month.removeLast();
        }
        month.addFirst(value);
    }

    private OptionalDouble getAverageRaw(int numDays) {
        assert numDays > 0 && numDays <= 30;
        return month.stream()
                .limit(numDays)
                .mapToDouble(Number::doubleValue)
                .average();
    }

    private boolean isFull() {
        return month.size() == FULL_SIZE;
    }

}
