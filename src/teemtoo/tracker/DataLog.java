package teemtoo.tracker;

import java.util.Deque;
import java.util.LinkedList;
import java.util.OptionalDouble;
import java.util.function.Function;

/**
 * @since 11/18/2017.
 */
public class DataLog<NumType extends Number> {

    private static final int FULL_SIZE = 30;
    private static final String EMPTY_VALUE = "---";

    private Deque<NumType> month;
    private Function<Number,String> formatter;
    private boolean showIntIfPossible;

    public DataLog(Function<Number, String> formatter, boolean showIntIfPossible) {
        this.month = new LinkedList<>();
        this.formatter = formatter;
        this.showIntIfPossible = showIntIfPossible;
    }

    private String format(Number value) {
        String output = formatter.apply(value);
        if (showIntIfPossible && output.endsWith(".0")) {
            output = output.substring(0, output.length() - 2);
        }
        return output;
    }

    public String getLastDay() {
        return month.isEmpty() ? EMPTY_VALUE : format(month.getLast());
    }

    public String getAverage(int numDays) {
        OptionalDouble average = getAverageRaw(numDays);
        return average.isPresent() ? format(average.getAsDouble()) : EMPTY_VALUE;
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
