package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

/**
 * Creates appointment object and how to deal with their data
 */
public class Appointment {

    //VARIABLES
    private int appId;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private String visualStartDateTime;
    private String visualEndDateTime;
    private int customerId;
    private int userId;
    private String contactName;
    private int contactId;
    private static Appointment passedAppointment;
    private static ObservableList<String> allLocations = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

    /**
     * Constructor without appointment ID
     */
    public Appointment(int appId, String title, String description, String location, String type, ZonedDateTime startDateTime, ZonedDateTime endDateTime, String visualStartDateTime, String visualEndDateTime, String contactName, int customerId, int userId, int contactId) {
        this.appId = appId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.visualStartDateTime = visualStartDateTime;
        this.visualEndDateTime = visualEndDateTime;
        this.contactName = contactName;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Constructor with appointment ID
     */
    public Appointment(String title, String description, String location, String type, ZonedDateTime startDateTime, ZonedDateTime endDateTime, String visualStartDateTime, String visualEndDateTime, String contactName, int customerId, int userId, int contactId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.visualStartDateTime = visualStartDateTime;
        this.visualEndDateTime = visualEndDateTime;
        this.contactName = contactName;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }
    //GETTERS & SETTERS
    public int getAppId() {
        return appId;
    }
    public void setAppId(int appId) {
        this.appId = appId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getContactId() {
        return contactId;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public int getUserId() {
        return userId;
    }
    public String getContactName() {
        return contactName;
    }
    public String getVisualStartDateTime() {
        return visualStartDateTime;
    }
    public void setVisualStartDateTime(String visualStartDateTime) {
        this.visualStartDateTime = visualStartDateTime;
    }
    public String getVisualEndDateTime() {
        return visualEndDateTime;
    }
    public void setVisualEndDateTime(String visualEndDateTime) {
        this.visualEndDateTime = visualEndDateTime;
    }
    public static Appointment getPassedAppointment() {
        return passedAppointment;
    }
    public static void setPassedAppointment(Appointment passedAppointment) {
        Appointment.passedAppointment = passedAppointment;
    }
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public static void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);
    }

    public static ObservableList<String> getAllLocations() {
        return allLocations;
    }

    public static ObservableList<Appointment> getWeeklyAppointments() {
        return weeklyAppointments;
    }

    public static void addWeeklyAppointment(Appointment appointment) {
        weeklyAppointments.add(appointment);
    }

    public static ObservableList<Appointment> getMonthlyAppointments() {
        return monthlyAppointments;
    }

    public static void addMonthlyAppointment(Appointment appointment) {
        monthlyAppointments.add(appointment);
    }

    /**
     * Sets the location of offices to choose from
     */
    public static void setAllLocations() {
        allLocations.add("Phoenix, Arizona");
        allLocations.add("White Plains, New York");
        allLocations.add("Montreal, Canada");
        allLocations.add("London, England");
    }
}