package entities;

import java.io.Serializable;

public class Car {

    private int id;

    private String vendor;

    private String model;

    private String yearOfIssue;

    private long price;

    private String engineVolume;

    private String transmission;

    private static int currentCarId;
    private static String currentCarVendor;
    private static String currentCarModel;
    private static long currentCarPrice;
    private static String currentCarVolume;
    private static String currentCarYearOfIssue;
    private static String currentCarTransmission;

    public static String getCurrentCarTransmission() {
        return currentCarTransmission;
    }

    public static void setCurrentCarTransmission(String currentCarTransmission) {
        Car.currentCarTransmission = currentCarTransmission;
    }

    public static String getCurrentCarVolume() {
        return currentCarVolume;
    }

    public static void setCurrentCarVolume(String currentCarVolume) {
        Car.currentCarVolume = currentCarVolume;
    }

    public static String getCurrentCarYearOfIssue() {
        return currentCarYearOfIssue;
    }

    public static void setCurrentCarYearOfIssue(String currentCarYearOfIssue) {
        Car.currentCarYearOfIssue = currentCarYearOfIssue;
    }

    public static long getCurrentCarPrice() {
        return currentCarPrice;
    }

    public static void setCurrentCarPrice(long currentCarPrice) {
        Car.currentCarPrice = currentCarPrice;
    }

    public static String getCurrentCarVendor() {
        return currentCarVendor;
    }

    public static void setCurrentCarVendor(String currentCarVendor) {
        Car.currentCarVendor = currentCarVendor;
    }

    public static String getCurrentCarModel() {
        return currentCarModel;
    }

    public static void setCurrentCarModel(String currentCarModel) {
        Car.currentCarModel = currentCarModel;
    }

    public static int getCurrentCarId() {
        return currentCarId;
    }

    public static void setCurrentCarId(int currentCarId) {
        Car.currentCarId = currentCarId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car() {
    }

    public int getId() {
        return id;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(String yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(String engineVolume) {
        this.engineVolume = engineVolume;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
}
