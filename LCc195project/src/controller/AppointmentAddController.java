package controller;

import dao.AppointmentDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utilities.Lambda;
import utilities.Time;
import utilities.UserSettings;
import utilities.Warning;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controller for the appointment add form.
 */

public class AppointmentAddController {

    @FXML
    private TextField userNameText;
    @FXML
    private TextField userIdText;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField titleText;
    @FXML
    private ComboBox<String> contactCombo;
    @FXML
    private TextField contactIdText;
    @FXML
    private ComboBox<String> locationCombo;
    @FXML
    private TextField typeText;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private ComboBox<String> startHourCombo;
    @FXML
    private TextField customerIdText;
    @FXML
    private ComboBox<String> customerNameCombo;
    @FXML
    private ComboBox<String> startMinuteCombo;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> endHourCombo;
    @FXML
    private ComboBox<String> endMinuteCombo;
    Stage stage;
    Parent scene;

    /**
     * LAMBDAS here allows me to use this switch scene expression in multiple places
     * Changes the scene
     * @param event on click
     * @param string location destination scene
     */
    Lambda switchScene = (ActionEvent event, String string) -> {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(string));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    /**
     * Will take you back to Appointment Calendar form
     * @param event on click of cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        switchScene.switchScene(event, "/view/AppointmentCalendar.fxml");
    }

    /**
     * Saves a new appointment to database and observable list
     * @param event on click of Save button
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException, SQLException {
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationCombo.getValue();
        String type = typeText.getText();
        int customerId = Integer.parseInt(customerIdText.getText());
        int contactId = Integer.parseInt(contactIdText.getText());
        String contactName = contactCombo.getValue();

        //Changes time inputs into LocalDateTime
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String startHour = String.valueOf(startHourCombo.getValue());
        String startMinute = String.valueOf(startMinuteCombo.getValue());
        String endHour = String.valueOf(endHourCombo.getValue());
        String endMinute = String.valueOf(endMinuteCombo.getValue());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        DateTimeFormatter dateTimeFormatter = Time.getDateTimeFormatter();

        LocalTime startTime = LocalTime.parse(startHour + ":" + startMinute + ":" + "00", formatter);
        LocalTime endTime = LocalTime.parse(endHour + ":" + endMinute + ":" + "00", formatter);
        ZonedDateTime startDateTime = startDate.atTime(startTime).atZone(UserSettings.getUserTimeZone());
        ZonedDateTime endDateTime = endDate.atTime(endTime).atZone(UserSettings.getUserTimeZone());
        String visualStartDateTime = dateTimeFormatter.format(startDateTime);
        String visualEndDateTime = dateTimeFormatter.format(endDateTime);


        if (Time.checkBusinessHours(startDateTime, endDateTime)) {
            if (Time.checkForOverLapping(startDateTime, endDateTime)) {
                AppointmentDao appointmentDao = new AppointmentDao();
                Appointment newAppointment = new Appointment(title, description, location, type, startDateTime, endDateTime, visualStartDateTime, visualEndDateTime, contactName, customerId, User.getUserId(), contactId);
                appointmentDao.insertNewAppointment(newAppointment);

                switchScene.switchScene(event, "/view/AppointmentCalendar.fxml");
            }
            else {
                Warning.displayErrorPopUp("Appointment time conflicts with other existing appointment.");
            }
        }
        else {
            Warning.displayErrorPopUp("Appointment times are not within business hours.");
        }

    }

    /**
     * Sets the customerID field
     * @param event on customer chosen from combo box
     */
    @FXML
    void onActionCustomerChosen(ActionEvent event) {
        String customer = customerNameCombo.getValue();
        customerIdText.setText(String.valueOf(Customer.getCustomerIdFromName(customer)));
    }

    /**
     * Sets the ContactId field
     * @param event on contact chosen from combo box
     */
    @FXML
    void onActionContactChosen(ActionEvent event) {
        String contact = contactCombo.getValue();
        contactIdText.setText(String.valueOf(Contact.getContactIdFromName(contactCombo.getValue())));
    }

    /**
     * Sets up list view when scene is opened
     */
    public void initialize() {
        userNameText.setText(User.getUsername());
        userIdText.setText(String.valueOf(User.getUserId()));
        startHourCombo.setItems(Time.getAllHours());
        endHourCombo.setItems(Time.getAllHours());
        startMinuteCombo.setItems(Time.getAllMinutes());
        endMinuteCombo.setItems(Time.getAllMinutes());
        contactCombo.setItems(Customer.getAllCustomerNames());
        locationCombo.setItems(Appointment.getAllLocations());
        contactCombo.setItems(Contact.getAllContactNames());
        customerNameCombo.setItems(Customer.getAllCustomerNames());
    }

}
