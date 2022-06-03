package dao;

import model.*;
import utilities.DBQuery;
import utilities.Time;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deals with SQL access for appointments
 */
public class AppointmentDao {
    Statement statement = DBQuery.getStatement();

    /**
     * Gets all the appointments from the database
     * @throws SQLException
     */
    public void populateAppointments() throws SQLException {
        DateTimeFormatter dateTimeFormatter = Time.getDateTimeFormatter();

        String selectStatement = "SELECT Appointment_ID, " +
                "Title, " +
                "Description, " +
                "Location, " +
                "Type, " +
                "Start, " +
                "End, " +
                "Customer_ID, " +
                "User_ID, " +
                "Contact_ID " +
                "from appointments";

        ResultSet rs = statement.executeQuery(selectStatement);

        while ( rs.next()) {
            LocalDate localStartDate = Time.convertToLocalDateViaInstant(rs.getDate("Start"));
            LocalDate localEndDate = Time.convertToLocalDateViaInstant(rs.getDate("End"));
            LocalTime localStartTime = Time.convertToLocalTimeViaInstant(rs.getTime("Start"));
            LocalTime localEndTime = Time.convertToLocalTimeViaInstant(rs.getTime("End"));

            ZonedDateTime localStartDateTime = Time.universalToLocalDateTime(localStartDate.atTime(localStartTime));
            ZonedDateTime localEndDateTime = Time.universalToLocalDateTime(localEndDate.atTime(localEndTime));

            rs.getTimestamp("Start");
            Appointment appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    localStartDateTime,
                    localEndDateTime,
                    dateTimeFormatter.format(localStartDateTime),
                    dateTimeFormatter.format(localEndDateTime),
                    Contact.getContactNameFromId(rs.getInt("Contact_ID")),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID"));
            Appointment.addAppointment(appointment);
        }
    }

    /**
     * Gets the appointment ID of the last inserted appointment
     * @return appointmentId
     * @throws SQLException
     */
    public int getAppointmentId() throws SQLException {
        String selectMaxIdStatement = "SELECT MAX(Appointment_ID) Appointment_ID FROM appointments";

        ResultSet rs = statement.executeQuery(selectMaxIdStatement);

        rs.next();
        return rs.getInt("Appointment_ID");
    }

    /**
     * Adds a new appointment into the database and observable list
     * @param newAppointment appointment to be added
     * @throws SQLException
     */
    public void insertNewAppointment(Appointment newAppointment) throws SQLException {
        String insertStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) "
                + "VALUES ('"
                + newAppointment.getTitle() + "', '"
                + newAppointment.getDescription() + "', '"
                + newAppointment.getLocation() + "', '"
                + newAppointment.getType() + "', '"
                + Time.localToUniversalDateTime(newAppointment.getStartDateTime()) + "', '"
                + Time.localToUniversalDateTime(newAppointment.getEndDateTime()) + "', '"
                + newAppointment.getCustomerId() + "', '"
                + User.getUserId() + "', '"
                + newAppointment.getContactId() + "')";

        //adds to database
        statement.execute(insertStatement);
        //Adds to observable list
        newAppointment.setAppId(getAppointmentId());
        Appointment.addAppointment(newAppointment);
    }

    /**
     * Updates appointment information in database and observable list
     * @param appointmentId value to be updated
     * @param title value to be updated
     * @param description value to be updated
     * @param location value to be updated
     * @param type value to be updated
     * @param startDateTime value to be updated
     * @param endDateTime value to be updated
     * @param customerId value to be updated
     * @param contactId value to be updated
     * @throws SQLException
     */
    public void updateAppointment(int appointmentId,
                                  String title,
                                  String description,
                                  String location,
                                  String type,
                                  ZonedDateTime startDateTime,
                                  ZonedDateTime endDateTime,
                                  int customerId,
                                  int contactId) throws SQLException {
        //Updates database
        String updateStatement = "UPDATE appointments " +
                "SET Title = '" + title +
                "', Description = '" + description +
                "', Location = '" + location +
                "', Type = '" + type +
                "', Start = '" + Time.localToUniversalDateTime(startDateTime) +
                "', End = '" + Time.localToUniversalDateTime(endDateTime) +
                "', Customer_ID = '" + customerId +
                "', Contact_ID = '" + contactId +
                "' WHERE Appointment_ID = " + appointmentId + ";";
        statement.execute(updateStatement);

        // Updates local observable list for use in current session
        DateTimeFormatter dateTimeFormatter = Time.getDateTimeFormatter();
        Appointment.getPassedAppointment().setTitle(title);
        Appointment.getPassedAppointment().setDescription(description);
        Appointment.getPassedAppointment().setLocation(location);
        Appointment.getPassedAppointment().setType(type);
        Appointment.getPassedAppointment().setStartDateTime(startDateTime);
        Appointment.getPassedAppointment().setEndDateTime(endDateTime);
        Appointment.getPassedAppointment().setVisualStartDateTime(dateTimeFormatter.format(startDateTime));
        Appointment.getPassedAppointment().setVisualEndDateTime(dateTimeFormatter.format(endDateTime));
        Appointment.getPassedAppointment().setCustomerId(customerId);
        Appointment.getPassedAppointment().setContactId(contactId);
    }

    /**
     * Deletes an appointment from database and observable list
     * @param appointment appointment to be deleted
     * @throws SQLException
     */
    public void deleteAppointment(Appointment appointment) throws SQLException {
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = " + appointment.getAppId();

        //Removes from database
        statement.execute(deleteStatement);

        //Removes from observable list for use in current session
        Appointment.getAllAppointments().remove(appointment);
    }
}
