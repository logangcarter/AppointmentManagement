package controller;

import dao.CustomerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Region;

import java.sql.SQLException;
import java.io.IOException;

/**
 * Controller for the customer add form
 */
public class CustomerAddController {
    @FXML
    private TextField nameText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField postalCodeText;
    @FXML
    private TextField phoneText;
    @FXML
    private ComboBox<String> countryCombo;
    @FXML
    private ComboBox<String> regionCombo;

    Stage stage;
    Parent scene;

    /**
     * Switches to the customer directory form
     * @param event on click of cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerDirectory.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Saves the new customer to the database and observable list
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        String name = nameText.getText();
        String address = addressText.getText();
        String postalCode = postalCodeText.getText();
        String phone = phoneText.getText();
        String country = countryCombo.getValue();
        String region = regionCombo.getValue();
        Region.getRegionIdFromName(region);

        CustomerDao customerDao = new CustomerDao();

        Customer newCustomer = new Customer(name, address, postalCode, phone, country, region);
        customerDao.insertNewCustomer(newCustomer);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerDirectory.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Populates the region combo with regions in the country
     * @param event on country chosen from country combo box
     */
    @FXML
    void onActionCountryChosen(ActionEvent event) {
        String countryName = countryCombo.getValue();
        Country.getCountryIdFromName(countryName);
        Region.findAllLocalRegions(Country.getLocalCountryId());
        regionCombo.setItems(Region.getAllLocalRegionsNames());
    }

    /**
     * Sets up the countries combo
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        countryCombo.setItems(Country.getAllCountriesNames());
    }
}

