package teemtoo.event;

/**
 * @author Anthony Morrell
 * @since 11/19/2017
 */
public class FallAsleepEvent extends Event {

    private long timestamp;

    public FallAsleepEvent() {
        timestamp = System.currentTimeMillis();
    }

    @Override
    public long getFallAsleepTime() {
        return timestamp;
    }

}
