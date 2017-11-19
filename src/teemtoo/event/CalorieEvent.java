package teemtoo.event;

/**
 * @since 11/19/2017
 */
public class CalorieEvent extends Event {

    private int intake;

    public CalorieEvent(int intake) {
        this.intake = intake;
    }

    @Override
    public int getCalorieData() {
        return intake;
    }

}
