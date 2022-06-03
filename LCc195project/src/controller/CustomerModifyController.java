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
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for modify customer form
 */
public class CustomerModifyController {
    @FXML
    private TextField idText;
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
    @FXML
    private TextField nameText;

    Stage stage;
    Parent scene;

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
     *
     * Switches to the appointment calendar form
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
     * Updates the customer in the database and observable list
     * @param event on click of save button
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        int id = Integer.parseInt(idText.getText());
        String name = nameText.getText();
        String address = addressText.getText();
        String postalCode = postalCodeText.getText();
        String phone = phoneText.getText();
        String region = regionCombo.getValue();
        Region.getRegionIdFromName(region);

        CustomerDao customerDao = new CustomerDao();
        customerDao.updateCustomer(id, name, phone, address, postalCode, Region.getLocalRegionId());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerDirectory.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Populates the fields with information from customer chosen
     */
    public void initialize() {
        idText.setText(String.valueOf(Customer.getPassedCustomer().getId()));
        nameText.setText(String.valueOf(Customer.getPassedCustomer().getName()));
        phoneText.setText(String.valueOf(Customer.getPassedCustomer().getPhone()));
        addressText.setText(String.valueOf(Customer.getPassedCustomer().getAddress()));
        postalCodeText.setText(String.valueOf(Customer.getPassedCustomer().getPostalCode()));
        countryCombo.setValue(String.valueOf(Customer.getPassedCustomer().getCountry()));
        regionCombo.setValue(String.valueOf(Customer.getPassedCustomer().getRegion()));
        countryCombo.setItems(Country.getAllCountriesNames());

        String countryName = countryCombo.getValue();
        Country.getCountryIdFromName(countryName);
        Region.findAllLocalRegions(Country.getLocalCountryId());
        regionCombo.setItems(Region.getAllLocalRegionsNames());
    }
}

