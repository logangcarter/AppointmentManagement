package dao;

import model.Region;
import utilities.DBQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Deals with database access for first level divisions
 */
public class RegionDao {
    Statement statement = DBQuery.getStatement();

    /**
     * Gets all the first level divisions from the database
     * @throws SQLException
     */
    public void populateRegions() throws SQLException {
        String selectStatement = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";

        ResultSet rs = statement.executeQuery(selectStatement);

        Region.deleteAllRegions();
        while (rs.next()) {
           Region region = new Region(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));
           Region.addAllRegions(region);
        }
    }
}

