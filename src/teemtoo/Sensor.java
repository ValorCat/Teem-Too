package teemtoo;

import teemtoo.event.Event;

/**
 * @author Anthony Morrell
 * @since 11/19/2017
 */
public interface Sensor {

    void poll();

    default void update(Event event) {
        DataManager.getInstance().handleData(event);
    }

}
