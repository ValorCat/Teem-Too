package teemtoo.event;

/**
 * @since 11/19/2017
 */
public class SleepEvent extends Event {

    private long timestamp;

    public SleepEvent() {
        timestamp = System.currentTimeMillis();
    }

    @Override
    public long getSleepTimestamp() {
        return timestamp;
    }

}
