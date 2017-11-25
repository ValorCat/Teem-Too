package teemtoo.tracker;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * @since 11/18/2017.
 */
public class DataLog<NumType extends Number> {

    private static final int FULL_SIZE = 30;

    private Deque<NumType> month = new LinkedList<>();

    public Optional<NumType> getLastDay() {
        return Optional.ofNullable(month.isEmpty() ? null : month.getLast());
    }

    public OptionalDouble getAverage(int numDays) {
        assert numDays > 0 && numDays <= 30;
        return month.stream()
                .limit(numDays)
                .mapToDouble(Number::doubleValue)
                .average();
    }

    public void update(NumType value) {
        if (isFull()) {
            month.removeLast();
        }
        month.addFirst(value);
    }

    private boolean isFull() {
        return month.size() == FULL_SIZE;
    }

}
