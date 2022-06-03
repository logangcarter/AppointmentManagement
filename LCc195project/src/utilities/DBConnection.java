package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Deals with connection to the databse
 */
public class DBConnection {

    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ07Xyx";

    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    //Driver and connection interface reference
    private static final String MYSQLDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    //Username and Password
    private static final String username = "U07Xyx";
    private static final String password = "53689159417";

    /**
     * Begins the connection to the databse
     * @return returns the connection
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLDriver);
            try {
                connection = DriverManager.getConnection(jdbcURL, username, password);
                System.out.println("Connection successful");
            }
            catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        catch(ClassNotFoundException e) {
            System.out.println(e.getMessage() + ": Cant find");
        }
        return connection;
    }

    /**
     * Gets the connection to the databse
     * @return connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Closes the connection to the database
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed.");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
