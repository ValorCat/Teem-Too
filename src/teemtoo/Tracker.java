package teemtoo;

import javafx.beans.binding.StringExpression;

/**
 * @author Anthony Morrell
 * @since 11/17/2017
 */
public interface Tracker {

    String getLabel();
    StringExpression getTotal();

    void handleData();
    void forwardData();
}
