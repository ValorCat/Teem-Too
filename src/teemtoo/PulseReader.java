package teemtoo;

import teemtoo.event.HeartEvent;

import java.util.Random;

/**
 * @author Anthony Morrell
 * @since 11/19/2017
 */
public class PulseReader implements Sensor {

    // this class contains simulation code

    private static Random random = new Random();
    private int pollCounter = 0;

    @Override
    public void poll() {
        pollCounter++;
        if (pollCounter == 90) {
            update(new HeartEvent(getHeartRate()));
            pollCounter = 0;
        }
    }

    private double getHeartRate() {
        return 80 + random.nextGaussian() * 8;
    }

}
