package dao;

import model.User;
import utilities.DBQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Deals with database access for Users
 */
public class UserDao {
    Statement statement = DBQuery.getStatement();

    /**
     * Verifies login information
     * @param username Username entered
     * @param password Password entered
     * @return boolean if login credentials match what is in databse
     * @throws SQLException
     */
    public boolean confirmLogin(String username, String password) throws SQLException {
        String selectStatement = "SELECT * FROM users WHERE User_Name='" + username +
                "' AND Password='" + password + "'";

        ResultSet rs = statement.executeQuery(selectStatement);

        if (rs.next()) {
            User.setUserId(rs.getInt("User_ID"));
            return true;
        }
        else {
            return false;
        }
    }
}
