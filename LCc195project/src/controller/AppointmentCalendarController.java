package controller;

import dao.AppointmentDao;
import dao.ReportDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utilities.Time;
import utilities.Warning;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Controller for the appointment calendar form
 */
public class AppointmentCalendarController {

    @FXML
    private Button customerButton;
    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button modifyAppointmentButton;
    @FXML
    private Button deleteAppointmentButton;
    @FXML
    private RadioButton weekRadio;
    @FXML
    private RadioButton monthRadio;
    @FXML
    private RadioButton allRadio;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> idCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> contactCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startDateTimeCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endDateTimeCol;
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;
    @FXML
    private TableColumn<Appointment, Integer> userIdCol;
    Stage stage;
    Parent scene;


    /**
     * Switches to add appointment form
     * @param event on click of add button
     * @throws IOException
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Moves to the customer directory form
     * @param event on click of customer directory button
     * @throws IOException
     */
    @FXML
    void onActionCustomerDirectory(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerDirectory.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Delete's an appointment from database and observable list
     * @param event on click on delete button with appointment chosen
     * @throws SQLException
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        AppointmentDao appointmentDao = new AppointmentDao();
        Appointment deletedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        appointmentDao.deleteAppointment(deletedAppointment);
        Warning.displayInfoPopUp("Appointment " + deletedAppointment.getAppId()
                + " of type " + deletedAppointment.getType() + " has been deleted.");
    }

    /**
     * Moves to the modify appointment form with the appointment chosen
     * @param event on click of modify with an appointment chosen
     * @throws IOException
     */
    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        Appointment.setPassedAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentModify.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * moves to the report form and populates the report observable list with current data
     * @param event on click of the Type/ month button
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionTypeMonth(ActionEvent event) throws IOException, SQLException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportAppointmentTypes.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        ReportDao reportDao = new ReportDao();
        reportDao.populateTypeNames();
        reportDao.populateTypes();
    }

    /**
     * Moves to the report of appointments by customer form
     * @param event on click of appointments by customer button
     * @throws IOException
     */
    @FXML
    void onActionAppointmentsByCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportCustomerAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Moves to the report of number of customers per country form
     * @param event on click of number by country button
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionNumberByCountry(ActionEvent event) throws IOException, SQLException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportCustomersByLocation.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        ReportDao reportDao = new ReportDao();
        reportDao.populateLocations();
        reportDao.populateCustomerLocations();
    }

    /**
     * Sort the tableview by all appointments
     * @param event on click of all radio button
     */
    @FXML
    void onActionSortByAll(ActionEvent event) {
        appointmentTableView.setItems(Appointment.getAllAppointments());

        idCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("visualStartDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("visualEndDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * Sort the tableview by appointments this month
     * @param event on click of this month radio button
     */
    @FXML
    void onActionSortByMonth(ActionEvent event) {
        appointmentTableView.setItems(Appointment.getMonthlyAppointments());

        idCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("visualStartDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("visualEndDateTime"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * Sorts the appointments by this week
     * @param event On click of the this week radio button
     */
    @FXML
    void onActionSortByWeek(ActionEvent event) {
        appointmentTableView.setItems(Appointment.getWeeklyAppointments());

        idCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("visualStartDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("visualEndDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * Sets up the tableview to show all appointments and readies the sorted lists when scene is opened
     */
    public void initialize() {
        appointmentTableView.setItems(Appointment.getAllAppointments());

        idCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("visualStartDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("visualEndDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        Appointment.getWeeklyAppointments().clear();
        Appointment.getMonthlyAppointments().clear();
        for (int i = 0; i < Appointment.getAllAppointments().size(); i++) {
            if (Time.checkDateForCurrentWeek(Appointment.getAllAppointments().get(i).getStartDateTime().toLocalDateTime())) {
                Appointment.addWeeklyAppointment(Appointment.getAllAppointments().get(i));
            }
            if (Time.checkDateForCurrentMonth(Appointment.getAllAppointments().get(i).getStartDateTime().toLocalDateTime())) {
                Appointment.addMonthlyAppointment(Appointment.getAllAppointments().get(i));
            }
        }

    }
}
