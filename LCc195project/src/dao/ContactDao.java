package dao;

import model.Contact;
import utilities.DBQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Deals with database access for contacts
 */
public class ContactDao {
    Statement statement = DBQuery.getStatement();

    /**
     * Gets all the contacts from the database
     * @throws SQLException
     */
    public void populateContacts() throws SQLException {
        String selectStatement = "SELECT Contact_ID, Contact_Name, Email from contacts";

        ResultSet rs = statement.executeQuery(selectStatement);

        while (rs.next()) {
            Contact contact = new Contact(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email"));
            Contact.addContact(contact);
            Contact.addContactName(rs.getString("Contact_Name"));
        }
    }



}
