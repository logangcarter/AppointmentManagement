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
 * Controller for the Appointment modify form
 */
public class AppointmentModifyController {

    @FXML
    private TextField userNameText;
    @FXML
    private TextField userIdText;
    @FXML
    private TextField appIdText;
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
     * Switches to the appointment calendar form
     * @param event on click of cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentCalendar.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sets the ContactId field
     * @param event on contact chosen from combo box
     */
    @FXML
    void onActionContactChosen(ActionEvent event) {
        String contact = contactCombo.getValue();
        contactIdText.setText(String.valueOf(Contact.getContactIdFromName(contact)));
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
     * Updates the changed appointment in the database and observable list
     * @param event on click of save button
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        int appointmentId = Integer.parseInt(appIdText.getText());
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationCombo.getValue();
        String type = typeText.getText();
        int customerId = Integer.parseInt(customerIdText.getText());
        int contactId = Integer.parseInt(contactIdText.getText());

        //Changes time inputs into LocalDateTime
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String startHour = String.valueOf(startHourCombo.getValue());
        String startMinute = String.valueOf(startMinuteCombo.getValue());
        String endHour = String.valueOf(endHourCombo.getValue());
        String endMinute = String.valueOf(endMinuteCombo.getValue());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        LocalTime startTime = LocalTime.parse(startHour + ":" + startMinute + ":" + "00", formatter);
        LocalTime endTime = LocalTime.parse(endHour + ":" + endMinute + ":" + "00", formatter);
        ZonedDateTime startDateTime = startDate.atTime(startTime).atZone(UserSettings.getUserTimeZone());
        ZonedDateTime endDateTime = endDate.atTime(endTime).atZone(UserSettings.getUserTimeZone());


        if (Time.checkBusinessHours(startDateTime, endDateTime)) {

            if (Time.checkForOverLapping(startDateTime, endDateTime)) {
                AppointmentDao appointmentDao = new AppointmentDao();
                appointmentDao.updateAppointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, contactId);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/AppointmentCalendar.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
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
     * Formats hours
     * @return hourFormat
     */
    public DateTimeFormatter hourFormat() {
        DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("HH");
        return hourFormat;
    }

    /**
     * formats minutes
     * @return minuteFormat
     */
    public DateTimeFormatter minuteFormat() {
        DateTimeFormatter minuteFormat = DateTimeFormatter.ofPattern("mm");
        return minuteFormat;
    }

    /**
     * Sets up the text boxes with information of appointment chosen to modify
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

        appIdText.setText(String.valueOf(Appointment.getPassedAppointment().getAppId()));
        titleText.setText(String.valueOf(Appointment.getPassedAppointment().getTitle()));
        descriptionText.setText(String.valueOf(Appointment.getPassedAppointment().getDescription()));
        appIdText.setText(String.valueOf(Appointment.getPassedAppointment().getAppId()));
        locationCombo.setValue(String.valueOf(Appointment.getPassedAppointment().getLocation()));
        customerNameCombo.setValue(String.valueOf(Customer.getCustomerNameFromId(Appointment.getPassedAppointment().getCustomerId())));
        customerIdText.setText(String.valueOf(Appointment.getPassedAppointment().getCustomerId()));
        contactCombo.setValue(String.valueOf(Contact.getContactNameFromId(Appointment.getPassedAppointment().getContactId())));
        contactIdText.setText(String.valueOf(Appointment.getPassedAppointment().getContactId()));
        typeText.setText(String.valueOf(Appointment.getPassedAppointment().getType()));
        startDatePicker.setValue((Appointment.getPassedAppointment().getStartDateTime()).toLocalDate());
        endDatePicker.setValue((Appointment.getPassedAppointment().getEndDateTime()).toLocalDate());
        startHourCombo.setValue((Appointment.getPassedAppointment().getStartDateTime()).toLocalTime().format(hourFormat()));
        endHourCombo.setValue((Appointment.getPassedAppointment().getEndDateTime()).toLocalTime().format(hourFormat()));
        startMinuteCombo.setValue((Appointment.getPassedAppointment().getStartDateTime()).toLocalTime().format(minuteFormat()));
        endMinuteCombo.setValue((Appointment.getPassedAppointment().getEndDateTime()).toLocalTime().format(minuteFormat()));
    }
}