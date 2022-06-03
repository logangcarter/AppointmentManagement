package controller;

import dao.CustomerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import utilities.Warning;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for customer directory form
 */
public class CustomerDirectoryController {
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> idCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, String> countryCol;
    @FXML
    private TableColumn<Customer, String> regionCol;

    Stage stage;
    Parent scene;

    /**
     * Switches to the add customer form
     * @param event on click of the add button with a customer chosen
     * @throws IOException
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Switches to the appointment calendar form
     * @param event on click of the appointment calendar button
     * @throws IOException
     */
    @FXML
    void onActionAppointmentCalendar(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentCalendar.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes customer chosen and their appointments from database and observable list
     * @param event on click of delete button with customer chosen
     * @throws SQLException
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        CustomerDao customerDao = new CustomerDao();
        Customer deletedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        String customer = deletedCustomer.getName();
        for (int i = 0; i < Appointment.getAllAppointments().size(); i++) {
            int customerId = Appointment.getAllAppointments().get(i).getCustomerId();

            if (customerId == Customer.getCustomerIdFromName(customer)) {
                Customer.addCustomerAppointment(Appointment.getAllAppointments().get(i));
            }
        }

        boolean goAhead = Warning.displayWarningPopUp("Customer " + deletedCustomer.getName() + " and their "
                            + Customer.getCustomerAppointments().size() + " appointments will be deleted." +
                            "\nAre you sure?");

        if (goAhead) {
            customerDao.deleteCustomer(deletedCustomer);
            Warning.displayInfoPopUp("You have deleted Customer " + deletedCustomer.getName() + " and their "
                    + Customer.getCustomerAppointments().size() + " appointments.");
        }
    }

    /**
     * Switches to the modify customer form
     * @param event on clock of modify button with customer chosen
     * @throws IOException
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        Customer.setPassedCustomer(customerTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerModify.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sets up tableview on scene open
     */
    public void initialize() {
        customerTableView.setItems(Customer.getAllCustomers());

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        regionCol.setCellValueFactory(new PropertyValueFactory<>("region"));

    }
}
