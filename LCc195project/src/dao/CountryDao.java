package dao;

import model.Country;
import utilities.DBConnection;
import utilities.DBQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Deals with database access for countries
 */
public class CountryDao {
    Statement statement = DBQuery.getStatement();

    /**
     * Gets countries from database
     * @throws SQLException
     */
    public void populateCountries() throws SQLException {
        String selectStatement = "SELECT Country_ID, Country FROM countries";

        ResultSet rs = statement.executeQuery(selectStatement);

        while (rs.next()) {
            Country country = new Country(rs.getInt("Country_ID"), rs.getString("Country"));
            Country.addCountryName(country.getCountryName());
            Country.addCountry(country);

        }
    }
}