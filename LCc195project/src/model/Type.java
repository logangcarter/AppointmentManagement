package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Deals with the types object for the Types/Month report
 */
public class Type {
    private String type;
    private int januaryApps;
    private int februaryApps;
    private int marchApps;
    private int aprilApps;
    private int mayApps;
    private int juneApps;
    private int julyApps;
    private int augustApps;
    private int septemberApps;
    private int octoberApps;
    private int novemberApps;
    private int decemberApps;
    private static ObservableList<Type> allTypesAndMonths = FXCollections.observableArrayList();
    private static ArrayList<String> allTypeNames = new ArrayList<String>();

    public Type(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getJanuaryApps() {
        return januaryApps;
    }
    public void setJanuaryApps(int januaryApps) {
        this.januaryApps = januaryApps;
    }
    public int getFebruaryApps() {
        return februaryApps;
    }
    public void setFebruaryApps(int februaryApps) {
        this.februaryApps = februaryApps;
    }
    public int getMarchApps() {
        return marchApps;
    }
    public void setMarchApps(int marchApps) {
        this.marchApps = marchApps;
    }
    public int getAprilApps() {
        return aprilApps;
    }
    public void setAprilApps(int aprilApps) {
        this.aprilApps = aprilApps;
    }
    public int getMayApps() {
        return mayApps;
    }
    public void setMayApps(int mayApps) {
        this.mayApps = mayApps;
    }
    public int getJuneApps() {
        return juneApps;
    }
    public void setJuneApps(int juneApps) {
        this.juneApps = juneApps;
    }
    public int getJulyApps() {
        return julyApps;
    }
    public void setJulyApps(int julyApps) {
        this.julyApps = julyApps;
    }
    public int getAugustApps() {
        return augustApps;
    }
    public void setAugustApps(int augustApps) {
        this.augustApps = augustApps;
    }
    public int getSeptemberApps() {
        return septemberApps;
    }
    public void setSeptemberApps(int septemberApps) {
        this.septemberApps = septemberApps;
    }
    public int getOctoberApps() {
        return octoberApps;
    }
    public void setOctoberApps(int octoberApps) {
        this.octoberApps = octoberApps;
    }
    public int getNovemberApps() {
        return novemberApps;
    }
    public void setNovemberApps(int novemberApps) {
        this.novemberApps = novemberApps;
    }
    public int getDecemberApps() {
        return decemberApps;
    }
    public void setDecemberApps(int decemberApps) {
        this.decemberApps = decemberApps;
    }

    public static ObservableList<Type> getAllTypesAndMonths() {
        return allTypesAndMonths;
    }
    public static void addToTypesAndMonths(Type type) {
        allTypesAndMonths.add(type);
    }
    public static ArrayList<String> getAllTypeNames() {
        return allTypeNames;
    }
}
