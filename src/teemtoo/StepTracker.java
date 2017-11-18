package teemtoo;

/**
 * Created by Daniel on 11/18/2017.
 */
public class StepTracker extends AbstractTracker {

    public Integer currentSteps;


    public StepTracker()
    {
        super("Today's Steps");
    }

    @Override
    public void handleData(Object s)
    {
        Integer steps = (Integer) s;
        currentSteps += steps;
    }



}
