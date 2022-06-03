package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates contact object and methods to deal with their data
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String contactEmail;
    private static int localContactId;
    private static String localContactName;
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ObservableList<String> allContactNames = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allContactsAppointments = FXCollections.observableArrayList();
    private static ObservableList<String> allContactsAppointmentsNames = FXCollections.observableArrayList();


    /**
     * constructor
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public int getContactId() {
        return contactId;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public String getContactEmail() {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }
    public static void addContact(Contact newContact) {
        allContacts.add(newContact);
    }
    public static ObservableList<String> getAllContactNames() {
        return allContactNames;
    }
    public static void addContactName(String contactName) {
        allContactNames.add(contactName);
    }
    public static int getLocalContactId() {
        return localContactId;
    }
    public static void setLocalContactId(int localContactId) {
        Contact.localContactId = localContactId;
    }
    public static ObservableList<Appointment> getAllContactsAppointments() {
        return allContactsAppointments;
    }
    public static void addContactAppointment(Appointment appointment) {
        allContactsAppointments.add(appointment);
    }

    /**
     * Gets contact id from the name
     * @param name to get id from
     * @return contactId
     */
    public static int getContactIdFromName(String name) {
        for (int i = 0; i < allContacts.size(); i++) {
            if (allContacts.get(i).getContactName().equals(name)) {
                localContactId = allContacts.get(i).getContactId();
                break;
            }
        }
        return localContactId;
    }

    /**
     * Gets contact name from id
     * @param id to get name from
     * @return contactName
     */
    public static String getContactNameFromId(int id) {
        for (int i = 0; i < allContacts.size(); i++) {
            if (allContacts.get(i).getContactId() == id) {
                localContactName = allContacts.get(i).getContactName();
                break;
            }
        }
        return localContactName;
    }
}