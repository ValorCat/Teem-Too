package teemtoo.event;

/**
 * @since 11/19/2017
 */
public class AwakenEvent extends Event {

    private long timestamp;

    public AwakenEvent() {
        timestamp = System.currentTimeMillis();
    }

    @Override
    public long getAwakenTime() {
        return timestamp;
    }

}
