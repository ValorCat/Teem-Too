package teemtoo;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anthony Morrell
 * @since 11/17/2017
 */
public final class DataManager {

    private int currentTracker;
    private List<Tracker> trackers;

    private static DataManager instance = new DataManager();

    private DataManager() {
        this.currentTracker = 0;
        // todo create individual classes for each tracker and add to chain of responsibility
        this.trackers = new ArrayList<>();
        trackers.add(new Tracker() {
            private IntegerProperty steps = new SimpleIntegerProperty();
            public String getLabel() { return "Steps Today"; }
            public StringExpression getTotal() { return steps.asString(); }
        });
        trackers.add(new Tracker() {
            private DoubleProperty bpm = new SimpleDoubleProperty(80);
            public String getLabel() { return "Heart Rate"; }
            public StringExpression getTotal() { return bpm.asString(); }
        });
        trackers.add(new Tracker() {
            private IntegerProperty calories = new SimpleIntegerProperty();
            public String getLabel() { return "Calorie Intake Today"; }
            public StringExpression getTotal() { return calories.asString(); }
        });
        trackers.add(new Tracker() {
            public String getLabel() { return "Last Sleep Time"; }
            public StringExpression getTotal() { return Bindings.concat("WIP"); }
        });
    }

    public void nextTracker() {
        currentTracker++;
        if (currentTracker >= trackers.size()) {
            currentTracker = 0;
        }
    }

    public void previousTracker() {
        currentTracker--;
        if (currentTracker < 0) {
            currentTracker = trackers.size() - 1;
        }
    }

    public Tracker getCurrentTracker() {
        assert !trackers.isEmpty() && currentTracker > 0 && currentTracker < trackers.size();
        return trackers.get(currentTracker);
    }

    public static DataManager getInstance() {
        return instance;
    }

}
