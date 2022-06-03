package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates customer Location object and methods to deal with their data
 */
public class CustomerLocation {

    private String locationName;
    private int numberOfCustomers;
    private static ObservableList<CustomerLocation> customersLocation = FXCollections.observableArrayList();
    private static ObservableList<String> allLocations = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public CustomerLocation(String countryName, int numberOfCustomers) {
        this.locationName = countryName;
        this.numberOfCustomers = numberOfCustomers;
    }

    public String getLocationName() {
        return locationName;
    }
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }
    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    public static ObservableList<CustomerLocation> getCustomersLocation() {
        return customersLocation;
    }
    public static void addCustomersLocation(CustomerLocation customerLocation) {
        customersLocation.add(customerLocation);
    }

    public static ObservableList<String> getAllLocations() {
        return allLocations;
    }
    public static void addLocation(String location) {
        allLocations.add(location);
    }
}
