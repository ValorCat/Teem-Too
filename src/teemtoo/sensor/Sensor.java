package teemtoo.sensor;

import teemtoo.event.Event;
import teemtoo.logic.DataManager;

/**
 * @since 11/19/2017
 */
public interface Sensor {

    void poll();

    default void update(Event event) {
        DataManager.getInstance().handle(event);
    }

}
