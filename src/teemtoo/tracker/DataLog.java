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

    /* We don't need random access, so a linked list implementation is sufficient. */
    private Deque<NumType> data;

    /* Formats the data for viewing. For example, sleep duration data is stored internally as longs but is displayed
     * in digital clock format: h:mm:ss. */

    private Function<Number,String> formatter;

    /* Determines whether the data should be truncated to an integer when it has no fractional component. This makes
     * step and calorie data look much easier on the eyes. For example, 5.0 => 5, but 5.1 would remain as is. */
    private boolean showIntIfPossible;

    public DataLog(Function<Number, String> formatter, boolean showIntIfPossible) {
        this.data = new LinkedList<>();
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

    /**
     * Retrieve the most recent log entry.
     * @return the most recent log entry.
     */
    public String getLastDataPoint() {
        return data.isEmpty() ? EMPTY_VALUE : format(data.getLast());
    }


    /**
     * Compute the mean across a number of log entries.
     * @param period the number of entries to consider, beginning from the most recent. Cannot exceed DataLog.FULL_SIZE.
     * @return the mean
     */
    public String getAverage(int period) {
        assert period > 0 && period <= FULL_SIZE;

        OptionalDouble average = getAverageRaw(period);
        return average.isPresent() ? format(average.getAsDouble()) : EMPTY_VALUE;
    }

    /**
     * Add a new entry to the log, removing old data if at maximum capacity.
     * @param value the entry to insert into the log.
     */
    public void update(NumType value) {
        if (isFull()) {
            data.removeLast();
        }
        data.addFirst(value);
    }

    private OptionalDouble getAverageRaw(int period) {
        assert period > 0 && period <= 30;
        return data.stream()
                .limit(period)
                .mapToDouble(Number::doubleValue)
                .average();
    }

    private boolean isFull() {
        return data.size() == FULL_SIZE;
    }

}
