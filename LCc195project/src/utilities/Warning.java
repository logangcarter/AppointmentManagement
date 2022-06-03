package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Deals with pop ups
 */
public class Warning {
    /**
     * Displays a warning message
     * @param displayMessage
     * @return boolean result of decision
     */
    public static boolean displayWarningPopUp(String displayMessage) {
        boolean goAhead;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(displayMessage);

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            goAhead = true;
        }
        else {
            goAhead = false;
        }
        return goAhead;
    }

    /**
     * Displays error pop up
     * @param displayMessage
     */
    public static void displayErrorPopUp(String displayMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(displayMessage);


        Optional<ButtonType> result = alert.showAndWait();
    }

    /**
     * Displays informational pop up
     * @param displayMessage
     */
    public static void displayInfoPopUp(String displayMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(displayMessage);


        Optional<ButtonType> result = alert.showAndWait();
    }

}
