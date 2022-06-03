package dao;

import model.CustomerLocation;
import model.Type;
import utilities.DBQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Deals with databse access for the report information
 */
public class ReportDao {
    Statement statement = DBQuery.getStatement();

    /**
     * Gets all the distinct locations from database
     * @throws SQLException
     */
    public void populateLocations() throws SQLException {
        CustomerLocation.getAllLocations().clear();
        String selectStatement = "SELECT DISTINCT Location FROM appointments";
        ResultSet rs = statement.executeQuery(selectStatement);
        while (rs.next()) {
            CustomerLocation.addLocation(rs.getString("Location"));
        }

    }

    /**
     * Gets the count of customers per location from database
     * @throws SQLException
     */
    public void populateCustomerLocations() throws SQLException {
        CustomerLocation.getCustomersLocation().clear();
        for (int i = 0; i < CustomerLocation.getAllLocations().size(); i++) {
            String selectStatement = "SELECT COUNT(*) FROM appointments WHERE Location ='" + CustomerLocation.getAllLocations().get(i) + "'";
            ResultSet rs = statement.executeQuery(selectStatement);

            rs.next();
            int numberOfCustomers = rs.getInt("COUNT(*)");

            CustomerLocation customerLocation = new CustomerLocation(CustomerLocation.getAllLocations().get(i), numberOfCustomers);
            CustomerLocation.addCustomersLocation(customerLocation);


        }
    }

    /**
     * Gets distinct types from databse
     * @throws SQLException
     */
    public void populateTypeNames() throws SQLException {
        Type.getAllTypesAndMonths().clear();
        String selectStatement = "SELECT DISTINCT Type FROM appointments";

        ResultSet rs = statement.executeQuery(selectStatement);

        while (rs.next()) {
            Type type = new Type(rs.getString("Type"));
            Type.addToTypesAndMonths(type);
        }
    }

    /**
     * Gets number of appointments per month and type from databse
     * @throws SQLException
     */
    public void populateTypes() throws SQLException {
        String selectStatement1 = "SELECT MONTH(Start), Type from appointments";
        ResultSet rs = statement.executeQuery(selectStatement1);
        for (int i = 0; i < Type.getAllTypesAndMonths().size(); i++) {
            while (rs.next()) {
                int month = rs.getInt("Month(Start)");
                String type = rs.getString("Type");

                switch (month) {
                    case 1:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setJanuaryApps(Type.getAllTypesAndMonths().get(j).getJanuaryApps()+1);
                                break;
                            }
                        }
                        break;
                    case 2:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setFebruaryApps(Type.getAllTypesAndMonths().get(j).getFebruaryApps()+1);
                                break;
                            }
                        }
                        break;
                    case 3:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setMarchApps(Type.getAllTypesAndMonths().get(j).getMarchApps()+1);
                                break;
                            }
                        }
                        break;
                    case 4:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setAprilApps(Type.getAllTypesAndMonths().get(j).getAprilApps()+1);
                                break;
                            }
                        }
                        break;
                    case 5:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setMayApps(Type.getAllTypesAndMonths().get(j).getMayApps()+1);
                                break;
                            }
                        }
                        break;
                    case 6:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setJuneApps(Type.getAllTypesAndMonths().get(j).getJuneApps()+1);
                                break;
                            }
                        }
                        break;
                    case 7:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setJulyApps(Type.getAllTypesAndMonths().get(j).getJulyApps()+1);
                                break;
                            }
                        }
                        break;
                    case 8:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setAugustApps(Type.getAllTypesAndMonths().get(j).getAugustApps()+1);
                                break;
                            }
                        }
                        break;
                    case 9:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setSeptemberApps(Type.getAllTypesAndMonths().get(j).getSeptemberApps()+1);
                                break;
                            }
                        }
                        break;
                    case 10:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setOctoberApps(Type.getAllTypesAndMonths().get(j).getOctoberApps()+1);
                                break;
                            }
                        }
                        break;
                    case 11:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setNovemberApps(Type.getAllTypesAndMonths().get(j).getNovemberApps()+1);
                                break;
                            }
                        }
                        break;
                    case 12:
                        for (int j = 0; j < Type.getAllTypesAndMonths().size(); j++) {
                            if (type.equals(Type.getAllTypesAndMonths().get(j).getType())) {
                                Type.getAllTypesAndMonths().get(j).setDecemberApps(Type.getAllTypesAndMonths().get(j).getDecemberApps()+1);
                                break;
                            }
                        }
                        break;
                }
            }
        }
    }
}

