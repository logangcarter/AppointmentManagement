package utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Sets up ability to write SQL queries to databse
 */
public class DBQuery {

    private static Statement statement;

    /**
     * Creates the statement object
     * @param connection to the databse
     * @throws SQLException
     */
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    /**
     * Gets the SQL statement
     * @return statement
     */
    public static Statement getStatement() {
        return statement;
    }
}
