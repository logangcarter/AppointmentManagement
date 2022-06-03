package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates region object and methods to deal with their data
 */
public class Region {
    //Variables
    private int regionId;
    private String regionName;
    private int countryId;
    private static int localRegionId = 0;
    private static String localRegionName;
    private static ObservableList<Region> allRegions = FXCollections.observableArrayList();
    private static ObservableList<Region> allLocalRegions = FXCollections.observableArrayList();
    private static ObservableList<String> allLocalRegionsNames = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public Region(int regionId, String regionName, int countryId) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.countryId = countryId;
    }


    //GETTERS and SETTERS for variables
    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }


    //GETTERS and SETTERS for lists
    public static ObservableList<Region> getAllRegions() {
        return allRegions;
    }
    public static ObservableList<Region> getAllLocalRegions() {
        return allLocalRegions;
    }
    public static ObservableList<String> getAllLocalRegionsNames() {
        return allLocalRegionsNames;
    }
    public static void addAllRegions(Region region) {
        allRegions.add(region);
    }
    public static void addAllLocalRegions(Region region) {
        allLocalRegions.add(region);
    }
    public static void addAllLocalRegionsNames(String regionName) {
        allLocalRegionsNames.add(regionName);
    }
    public static int getLocalRegionId() {
        return localRegionId;
    }
    public static void setLocalRegionId(int localRegionId) {
        Region.localRegionId = localRegionId;
    }
    public static String getLocalRegionName() {
        return localRegionName;
    }
    public static void setLocalRegionName(String localRegionName) {
        Region.localRegionName = localRegionName;
    }


    /**
     * clears allRegions list
     */
    public static void deleteAllRegions() {
        allRegions.clear();
    }

    /**
     * clears allLocalRegions list
     */
    public static void deleteAllLocalRegions() {
        allLocalRegions.clear();
    }

    /**
     * clears allLocalRegionNames list
     */
    public static void deleteAllLocalRegionsNames() {
        allLocalRegionsNames.clear();
    }

    /**
     * Gets all the local regions and populates local region and local region names list
     * @param countryId country to get regions from
     */
    public static void findAllLocalRegions(int countryId) {
        Region.deleteAllLocalRegions();
        Region.deleteAllLocalRegionsNames();
        for (int i = 0; i < allRegions.size(); i++) {
            Region region = allRegions.get(i);
            if (region.getCountryId() == countryId) {
                allLocalRegions.add(region);
                allLocalRegionsNames.add(region.getRegionName());
            }
        }
    }

    /**
     * gets region id from name
     * @param regionName to get id from
     */
    public static void getRegionIdFromName(String regionName) {
        for (int i = 0; i < allRegions.size(); i++) {
            Region region = allRegions.get(i);
            if (region.getRegionName().equals(regionName)) {
                localRegionId = region.getRegionId();
                break;
            }
        }
    }

    /**
     * gets region name from id
     * @param regionId to get name from
     */
    public static void getRegionNameFromId(int regionId) {
        for (int i = 0; i < allRegions.size(); i++) {
            Region region = allRegions.get(i);
            if (region.getRegionId() == regionId) {
                localRegionName = region.getRegionName();
                break;
            }
        }
    }

    /**
     * Gets the country from the region
     * @param regionName to get country from
     */
    public static void getCountryIdFromRegionName(String regionName) {
        for (int i = 0; i < allRegions.size(); i++) {
            Region region = allRegions.get(i);
            if (region.getRegionName().equals(regionName)) {
                Country.setLocalCountryId(region.getCountryId());
                break;

            }
        }
    }
}
