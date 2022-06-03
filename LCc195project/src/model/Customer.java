package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates customer object and methods to deal with their data
 */
public class Customer {

    //VARIABLES
    private int id;
    private String name;
    private String phone;
    private String address;
    private String postalCode;
    private String country;
    private String region;
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<String> allCustomerNames = FXCollections.observableArrayList();
    private static ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
    private static Customer passedCustomer;
    private static int localCustomerId;
    private static String localCustomerName;

    /**
     * Constructor with ID
     */
    public Customer(int id, String name, String address, String postalCode, String phone, String country, String region) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.region = region;
    }

    /**
     * Constructor without ID
     */
    public Customer(String name, String address, String postalCode, String phone, String country, String region) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.region = region;
    }

    // GETTERS and SETTERS
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public static Customer getPassedCustomer() {
        return passedCustomer;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }
    public static void addCustomer(Customer newCustomer) {
        allCustomers.add(newCustomer);
    }
    public static void setPassedCustomer(Customer passedCustomer) {
        Customer.passedCustomer = passedCustomer;
    }
    public static ObservableList<Appointment> getCustomerAppointments() {
        return customerAppointments;
    }
    public static void addCustomerAppointment(Appointment appointment) {
        customerAppointments.add(appointment);
    }

    public static ObservableList<String> getAllCustomerNames() {
        allCustomerNames.clear();
        for (int i = 0; i < allCustomers.size(); i++) {
            allCustomerNames.add(allCustomers.get(i).getName());
        }
        return allCustomerNames;
    }

    public static int getCustomerIdFromName(String name) {
        for (int i = 0; i < allCustomers.size(); i++) {
            if (allCustomers.get(i).getName().equals(name)) {
                localCustomerId = allCustomers.get(i).getId();
                break;
            }
        }
        return localCustomerId;
    }
    public static String getCustomerNameFromId(int id) {
        for (int i = 0; i < allCustomers.size(); i++) {
            if (allCustomers.get(i).getId() == id) {
                localCustomerName = allCustomers.get(i).getName();
                break;
            }
        }
        return localCustomerName;
    }
}
