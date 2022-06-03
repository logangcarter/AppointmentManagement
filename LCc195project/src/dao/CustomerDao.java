package dao;

import model.Appointment;
import model.Country;
import model.Customer;
import model.Region;
import utilities.DBQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Deals with database access for customers
 */
public class CustomerDao {
    Statement statement = DBQuery.getStatement();

    /**
     * Adds new customer into database and observable list
     * @param newCustomer customer to be added
     * @throws SQLException
     */
    public void insertNewCustomer(Customer newCustomer) throws SQLException {
        Region.getRegionIdFromName(newCustomer.getRegion());

        String insertStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) "
                + "VALUES ('"
                + newCustomer.getName() + "', '"
                + newCustomer.getAddress() + "', '"
                + newCustomer.getPostalCode() + "', '"
                + newCustomer.getPhone() + "', "
                + Region.getLocalRegionId() + ")";

        //Adds to database
        statement.execute(insertStatement);
        //Adds to observable list for viewing during same session
        newCustomer.setId(getCustomerId());
        Customer.addCustomer(newCustomer);

    }

    /**
     * Gets the customer id of the last customer insterted
     * @return customerId
     * @throws SQLException
     */
    public int getCustomerId() throws SQLException {
        String selectMaxIdStatement = "SELECT MAX(Customer_ID) Customer_ID FROM customers";

        ResultSet rs = statement.executeQuery(selectMaxIdStatement);

        rs.next();
        return rs.getInt("Customer_ID");
    }

    /**
     * Gets all customers from database
     * @throws SQLException
     */
    public void populateCustomers() throws SQLException {
        String selectStatement = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers";

        ResultSet rs = statement.executeQuery(selectStatement);

        while (rs.next()) {
            Region.getRegionNameFromId(rs.getInt("Division_ID"));
            Region.getCountryIdFromRegionName(Region.getLocalRegionName());
            Country.getCountryNameFromId(Country.getLocalCountryId());
            Customer customer = new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"),
                    rs.getString("Phone"), Country.getLocalCountryName(), Region.getLocalRegionName());
            Customer.addCustomer(customer);

        }
    }

    /**
     * Updates customer to database and observable list
     * @param customerId value to be updated
     * @param name value to be updated
     * @param phone value to be updated
     * @param address value to be updated
     * @param postalCode value to be updated
     * @param regionId value to be updated
     * @throws SQLException
     */
    public void updateCustomer(int customerId, String name, String phone, String address, String postalCode, int regionId) throws SQLException {
        //Updates database
        String updateStatement = "UPDATE customers " +
                "SET Customer_Name = '" + name +
                "', Address = '" + address +
                "', Postal_Code = '" + postalCode +
                "', Phone = '" + phone +
                "', Division_Id = '" + regionId +
                "' WHERE Customer_ID = " + customerId + ";";
        statement.execute(updateStatement);

        // Updates local observable list for use in current session
        Customer.getPassedCustomer().setName(name);
        Customer.getPassedCustomer().setAddress(address);
        Customer.getPassedCustomer().setPostalCode(postalCode);
        Customer.getPassedCustomer().setPhone(phone);

        Region.getRegionNameFromId(regionId);
        Region.getCountryIdFromRegionName(Region.getLocalRegionName());
        Country.getCountryNameFromId(Country.getLocalCountryId());
        Customer.getPassedCustomer().setCountry(Country.getLocalCountryName());
        Customer.getPassedCustomer().setRegion(Region.getLocalRegionName());


    }

    /**
     * Deletes customer from database and observable list
     * @param customer
     * @throws SQLException
     */
    public void deleteCustomer(Customer customer) throws SQLException {
        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = " + customer.getId();
        String deleteStatement2 ="DELETE FROM appointments WHERE Customer_ID = " + customer.getId();

        for (int i = Appointment.getAllAppointments().size() - 1; i >= 0 ; i--) {
            if (customer.getId() == Appointment.getAllAppointments().get(i).getCustomerId()) {
                Appointment.getAllAppointments().remove(i);
            }
        }

        //Removes from database
        statement.execute(deleteStatement2);
        statement.execute(deleteStatement);

        //Removes from observable list for use in current session
        Customer.getAllCustomers().remove(customer);
    }
}
