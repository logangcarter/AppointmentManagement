package utilities;


import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * Functional interface for Lambda expression
 */
public interface Lambda {
    void switchScene(ActionEvent event, String string) throws IOException;
}



