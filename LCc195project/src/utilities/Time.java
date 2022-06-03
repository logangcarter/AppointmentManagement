package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Deals with time manipulation
 */
public class Time {
    private static String dateTimeFormat = "dd-MM-yyyy HH:mm z";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    private static ObservableList<String> allHours = FXCollections.observableArrayList();
    private static ObservableList<String> allMinutes = FXCollections.observableArrayList();
    public static DecimalFormat decimalFormat;


    public static DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
    public static void setDecimalFormat() {
        decimalFormat = new DecimalFormat("00");
    }

    /**
     * Sets all the hours to be options in setting appointments
     */
    public static void setAllHours() {
        setDecimalFormat();
        for (int i = 1; i < 24; i++) {
            String format = Time.decimalFormat.format(i);
            allHours.add(format);
        }
    }

    public static ObservableList<String> getAllHours() {
        return allHours;
    }

    /**
     * Sets all minutes to be options in setting appointments
     */
    public static void setAllMinutes() {
        setDecimalFormat();
        for (int i = 0; i < 60; i++) {
            String format = Time.decimalFormat.format(i);
            allMinutes.add(format);
        }
    }

    public static ObservableList<String> getAllMinutes() {
        return allMinutes;
    }

    /**
     * Converts date to localDate
     * @param dateToConvert
     * @return LocalDate
     */
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Converts time to local time
     * @param timeToConvert
     * @return LocalTime
     */
    public static LocalTime convertToLocalTimeViaInstant(java.sql.Time timeToConvert) {
        return Instant.ofEpochMilli(timeToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * Changes Local ZonedDateTime to universal
     * @param localZonedDateTime
     * @return localDateTime in UTC
     */
    public static LocalDateTime localToUniversalDateTime(ZonedDateTime localZonedDateTime) {
        ZoneId universalZoneId = ZoneId.of("UTC");

        ZonedDateTime universalZonedDateTime = localZonedDateTime.withZoneSameInstant(universalZoneId);

        return universalZonedDateTime.toLocalDateTime();
    }

    /**
     * Changes ZonedDateTime in UTC to ZonedDateTime in local timezone
     * @param universalDateTime
     * @return ZonedDateTime in local time zone
     */
    public static ZonedDateTime universalToLocalDateTime(LocalDateTime universalDateTime) {
        ZoneId universalZoneId = ZoneId.of("UTC");

        ZonedDateTime universalZonedDateTime = universalDateTime.atZone(universalZoneId);
        ZonedDateTime localZonedDateTime = universalZonedDateTime.withZoneSameInstant(UserSettings.getUserTimeZone());

        return localZonedDateTime;
    }

    /**
     * Checks if the appointment times are within businesss hours
     * @param localStartDateTime
     * @param localEndDateTime
     * @return boolean is it within business hours?
     */
    public static boolean checkBusinessHours(ZonedDateTime localStartDateTime, ZonedDateTime localEndDateTime) {
        ZoneId easternZoneId = ZoneId.of("US/Eastern");
        LocalTime easternOpen = LocalTime.of(7, 59);
        LocalTime easternClose = LocalTime.of(22, 01);
        boolean isInBusinessHours;

        ZonedDateTime easternZonedStartDateTime = localStartDateTime.withZoneSameInstant(easternZoneId);
        ZonedDateTime easternZonedEndDateTime = localEndDateTime.withZoneSameInstant(easternZoneId);

        LocalTime easternLocalStartTime = easternZonedStartDateTime.toLocalTime();
        LocalTime easternLocalEndTime = easternZonedEndDateTime.toLocalTime();
        LocalDate easternLocalStartDate = easternZonedStartDateTime.toLocalDate();
        LocalDate easternLocalEndDate = easternZonedEndDateTime.toLocalDate();


        if ( (easternLocalStartTime.isAfter(easternOpen)) && (easternLocalStartTime.isBefore(easternClose))
                &&  (easternLocalEndTime.isAfter(easternOpen)) && (easternLocalEndTime.isBefore(easternClose))
                && (easternLocalStartDate.equals(easternLocalEndDate))) {
            isInBusinessHours = true;
        }
        else {
            isInBusinessHours = false;
        }

        return isInBusinessHours;
    }

    /**
     * Checks if appointment times interfere with other appointment times
     * @param localStartDateTime
     * @param localEndDateTime
     * @return boolean does it interfere?
     */
    public static boolean checkForOverLapping(ZonedDateTime localStartDateTime,ZonedDateTime localEndDateTime) {
        for (int i = 0; i < Appointment.getAllAppointments().size(); i++) {
            System.out.println(localStartDateTime + " " + localStartDateTime.isEqual(Appointment.getAllAppointments().get(i).getStartDateTime()));
            if (      //Is the start before and after another appointment
                    (((localStartDateTime.isBefore(Appointment.getAllAppointments().get(i).getEndDateTime()))
                    && (localStartDateTime.isAfter(Appointment.getAllAppointments().get(i).getStartDateTime())))
                        //Is the end before or after another appointment
                    || ((localEndDateTime.isBefore(Appointment.getAllAppointments().get(i).getEndDateTime()))
                    && (localEndDateTime.isAfter(Appointment.getAllAppointments().get(i).getStartDateTime())))
                        //Are the appointment times the same as another appointment
                    || (localStartDateTime.isEqual(Appointment.getAllAppointments().get(i).getStartDateTime()))
                        //Is the appointment before the start and after the end of another
                    || (((localStartDateTime.isBefore(Appointment.getAllAppointments().get(i).getStartDateTime()))
                    && (localEndDateTime.isAfter(Appointment.getAllAppointments().get(i).getEndDateTime())))))
                        // make sure it is not the same appointment who wants to change the time
                    && (Appointment.getAllAppointments().get(i).getAppId() != Appointment.getPassedAppointment().getAppId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if there is an appointment within 15minutes
     * @return boolean is there an appointment within 15 minutes?
     */
    public static boolean checkIncomingAppointments() {
        ZonedDateTime dateTimeNow = ZonedDateTime.now();

        for (int i = 0; i < Appointment.getAllAppointments().size(); i++) {
            Appointment appointment = Appointment.getAllAppointments().get(i);
            Appointment.setPassedAppointment(appointment);
                //if the appointment -15min is before the current time AND the end of the appointment is after the current time
            if ((appointment.getStartDateTime().minusMinutes(15).isBefore(dateTimeNow) ) && (appointment.getEndDateTime().isAfter(dateTimeNow))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the date given is in the current week
     * @param localDateTime
     * @return boolean is it in the current week?
     */
    public static boolean checkDateForCurrentWeek(LocalDateTime localDateTime) {
        Date appointmentDate = java.sql.Date.valueOf(localDateTime.toLocalDate());

        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar appointmentCalendar = Calendar.getInstance();
        appointmentCalendar.setTime(appointmentDate);
        int appointmentWeek = appointmentCalendar.get(Calendar.WEEK_OF_YEAR);
        int appointmentYear = appointmentCalendar.get(Calendar.YEAR);

        if ((week == appointmentWeek)&& (year == appointmentYear)) {
            return true;
        }
    return false;
    }

    /**
     * Checks if given date is in given month
     * @param localDateTime
     * @return boolean is it in current month?
     */
    public static boolean checkDateForCurrentMonth(LocalDateTime localDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentMonth = currentDateTime.getMonthValue();
        int appointmentMonth = localDateTime.getMonthValue();

        if (currentMonth == appointmentMonth) {
            return true;
        }
        return false;
    }
}

