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

    private Deque<NumType> data;
    private Function<Number,String> formatter;
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

    public String getLastDataPoint() {
        return data.isEmpty() ? EMPTY_VALUE : format(data.getLast());
    }

    public String getAverage(int period) {
        OptionalDouble average = getAverageRaw(period);
        return average.isPresent() ? format(average.getAsDouble()) : EMPTY_VALUE;
    }

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
