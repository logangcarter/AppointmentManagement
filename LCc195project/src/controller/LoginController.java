package controller;

import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utilities.Time;
import utilities.UserSettings;
import utilities.Warning;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for the login form
 */
public class LoginController {

    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label countryDescriptorLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Button loginButton;

    Stage stage;
    Parent scene;

    /**
     * Verifies login information
     * @param event on click of login button
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionLogin(ActionEvent event) throws SQLException, IOException {
        UserDao daoObject = new UserDao();

        User.setUsername(usernameText.getText());
        User.setPassword(passwordText.getText());

        if (daoObject.confirmLogin(User.getUsername(), User.getPassword())) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AppointmentCalendar.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            User.setLoginAttempt("successful");
            User.writeToFile();

            if (Time.checkIncomingAppointments()) {
                Warning.displayInfoPopUp("Appointment " + Appointment.getPassedAppointment().getAppId() + " on "
                        + Appointment.getPassedAppointment().getStartDateTime().toLocalDate().toString() + " at "
                        + Appointment.getPassedAppointment().getStartDateTime().toLocalTime().toString() + " starts within 15 minutes.");
            }
            else {
                Warning.displayInfoPopUp("You have no upcoming appointments.");
            }

        }

        else {

            if (UserSettings.getUserLanguage().equals("français")) {
                Warning.displayErrorPopUp("Les informations de connexion ne correspondent pas à ce que nous avons dans nos fichiers.");
            }
            else {
                Warning.displayErrorPopUp("Login information does not match what we have on file.");
                User.setLoginAttempt("unsuccessful");
                User.writeToFile();
            }

        }
    }

    /**
     * Sets the timezone of user's computer and changes language to french if the users language settings are in french
     */
    public void initialize() {
        countryLabel.setText(UserSettings.getUserTimeZone().toString());
        if (UserSettings.getUserLanguage().equals("français")) {
            titleLabel.setText("Demande de planification");
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            countryDescriptorLabel.setText("De campagne:");
            loginButton.setText("Connexion");
        }
    }
}
