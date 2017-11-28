package teemtoo;

import teemtoo.event.Event;
import teemtoo.event.StepEvent;

/**
 * @since 11/19/2017
 */
public class Pedometer implements Sensor {

    // this class contains simulation code

    @Override
    public void poll() {
        if (Math.random() < .005) {
            Event step = new StepEvent();
            update(step);
        }
    }

}
