package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Controller for the report customer appointments form
 */
public class ReportContactAppointmentsController {
    @FXML
    private ComboBox<String> contactCombo;
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

    Stage stage;
    Parent scene;

    /**
     * Switches to the appointments calendar form
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(javafx.event.ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentCalendar.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sets the tableview to show all appointments or customer chosen
     * @param event on customer chosen from combo box
     */
    @FXML
    void onActionContactChosen(ActionEvent event) {
        String contact = contactCombo.getValue();
        Contact.getAllContactsAppointments().clear();
        for (int i = 0; i < Appointment.getAllAppointments().size(); i++) {
            int contactId = Appointment.getAllAppointments().get(i).getContactId();

            if (contactId == Contact.getContactIdFromName(contact)) {
                Contact.addContactAppointment(Appointment.getAllAppointments().get(i));
            }
        }
        appointmentTableView.setItems(Contact.getAllContactsAppointments());

        idCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("visualStartDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("visualEndDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * Sets customers in combo box on scene open
     */
    public void initialize() {
        contactCombo.setItems(Contact.getAllContactNames());
    }
}