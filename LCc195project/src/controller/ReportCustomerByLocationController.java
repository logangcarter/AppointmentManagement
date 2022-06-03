package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CustomerLocation;
import java.io.IOException;

/**
 * Controller for the report customer by location form
 */
public class ReportCustomerByLocationController {
    @FXML
    private TableView<CustomerLocation> customerLocationTableView;
    @FXML
    private TableColumn<CustomerLocation, String> locationCol;
    @FXML
    private TableColumn<CustomerLocation, Integer> customerCol;

    Stage stage;
    Parent scene;

    /**
     * Switches to the appointment calendar form
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
     * Sets up the table view with locations and number of customers
     */
    public void initialize() {
        customerLocationTableView.setItems(CustomerLocation.getCustomersLocation());

        locationCol.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("numberOfCustomers"));
    }
}