package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creats country object and methods to deal with their data
 */
public class Country {
    //VARIABLES
    private int countryId;
    private String countryName;
    private static int localCountryId;
    private static String localCountryName;
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<String> allCountriesNames = FXCollections.observableArrayList();

    /**
     * contructor
     */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    //GETTERS and SETTERS
    public int getCountryId() { return countryId; }
    public String getCountryName() {
        return countryName;
    }
    public static int getLocalCountryId() {
        return localCountryId;
    }
    public static void setLocalCountryId(int localCountryId) {
        Country.localCountryId = localCountryId;
    }
    public static void addCountry(Country newCountry) {
        allCountries.add(newCountry);
    }
    public static ObservableList<String> getAllCountriesNames() {
        return allCountriesNames;
    }
    public static void addCountryName(String newCountryName) {
        allCountriesNames.add(newCountryName);
    }
    public static String getLocalCountryName() {
        return localCountryName;
    }

    /**
     * Gets country id from name
     * @param countryName to get country from
     */
    public static void getCountryIdFromName(String countryName) {
        for (int i = 0; i < allCountries.size(); i++) {
            Country country = allCountries.get(i);
            if (country.getCountryName().equals(countryName)) {
                localCountryId = country.getCountryId();
                break;
            }
        }
    }

    /**
     * Gets country name from id
     * @param countryId to get name from
     */
    public static void getCountryNameFromId(int countryId) {
        for (int i = 0; i < allCountries.size(); i++) {
            Country country = allCountries.get(i);
            if (country.getCountryId() == countryId) {
                localCountryName = country.getCountryName();
                break;
            }
        }
    }
}
