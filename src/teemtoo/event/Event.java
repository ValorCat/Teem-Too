package teemtoo.event;

/**
 * @since 11/19/2017
 */
public class Event {

    public static final int NO_DATA = -1;

    public boolean isStep() {
        return false;
    }

    public double getBPMData() {
        return NO_DATA;
    }

    public int getCalorieData() {
        return NO_DATA;
    }

    public long getFallAsleepTime() {
        return NO_DATA;
    }

    public long getAwakenTime() {
        return NO_DATA;
    }

}
