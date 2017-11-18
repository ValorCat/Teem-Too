package teemtoo;


import javafx.beans.binding.StringExpression;

/**
 * Created by Daniel on 11/18/2017.
 */
public abstract class AbstractTracker {

    private AbstractTracker nextTracker = null;
    protected DataLog log;
    private String label;
    private StringExpression total;

    public abstract void handleData(Object t);

    public AbstractTracker(String l)
    {
        label = l;
    }

    private void forwardData(Object data) { nextTracker.handleData(data);}

    public AbstractTracker getNextTracker() { return nextTracker; }

    public void setNextTracker(AbstractTracker t) { nextTracker = t; }


    public String getLabel() { return label; }

    public StringExpression getTotal() { return total; }


}
