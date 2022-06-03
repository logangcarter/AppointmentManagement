package model;

import utilities.Lambda2;
import utilities.Time;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Creates user object and methods to deal with their data
 */
public class User {

    private static String username;
    private static String password;
    private static int userId;
    private static String loginAttempt;
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        User.username = username;
    }
    public static String getPassword() {
        return password;
    }
    public static void setPassword(String password) {
        User.password = password;
    }
    public static int getUserId() {
        return userId;
    }
    public static void setUserId(int userId) {
        User.userId = userId;
    }
    private static int getUserIdFromName() {
        return userId;
    }

    public static void setLoginAttempt(String loginAttempt) {
        User.loginAttempt = loginAttempt;
    }

    /**
     * Writes login report to text file
     * LAMBDAS Used Lambdas to make method easier to read
     * @throws IOException
     */
    public static void writeToFile() throws IOException {
        ZonedDateTime localDateTime = ZonedDateTime.now();
        LocalDateTime uniTime = Time.localToUniversalDateTime(localDateTime);
        ZoneId universalZoneId = ZoneId.of("UTC");
        ZonedDateTime universalZonedDateTime = uniTime.atZone(universalZoneId);
        DateTimeFormatter  dateTimeFormatter = Time.getDateTimeFormatter();
        String loginReport = String.format("Login Attempt: |%-12s| |%-20s| |%s|", loginAttempt, dateTimeFormatter.format(universalZonedDateTime), username);

        Lambda2 printLoginReport = () -> {
            File file = new File("login_activity.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(loginReport);
            printWriter.flush();
            printWriter.close();
        };
        printLoginReport.print();
    }
}
