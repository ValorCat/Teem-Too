package teemtoo.event;

/**
 * @author Anthony Morrell
 * @since 11/19/2017
 */
public class HeartEvent extends Event {

    private double data;

    public HeartEvent(double data) {
        this.data = data;
    }

    @Override
    public double getBPMData() {
        return data;
    }

}
