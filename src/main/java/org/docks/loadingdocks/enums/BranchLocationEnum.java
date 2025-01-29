package org.docks.loadingdocks.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BranchLocationEnum {


    SOFIA("Sofia", 42.6977, 23.3219, "", "10:00 - 20:00", "","", "0898"),
    PLOVDIV("Plovdiv", 42.1354, 24.7453, "", "10:00 - 20:00", "", "", "0898"),
    VARNA("Varna", 43.2141, 27.9147, "", "10:00 - 20:00", "", "", "0898"),
    BURGAS("Burgas", 42.504792, 27.462636, "", "10:00 - 20:00", "", "", "0898");

    private final String name;
    private final double lat;
    private final double lng;
    private final String address;
    private final String workingHours;
    private final String imageUrl;
    private final String infoUrl;
    private final String phone;



    BranchLocationEnum(String name, double lat, double lng, String address, String workingHours, String imageUrl, String infoUrl, String phone) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.workingHours = workingHours;
        this.imageUrl = imageUrl;
        this.infoUrl = infoUrl;
        this.phone = phone;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhone() {
        return phone;
    }

    @JsonCreator
    public static BranchLocationEnum fromString(String name) {
        for (BranchLocationEnum branch : BranchLocationEnum.values()) {
            if (branch.name.equalsIgnoreCase(name)) {
                return branch;
            }
        }
        throw new IllegalArgumentException("Unknown branch: " + name);
    }

    @Override
    public String toString() {
        switch (this) {
            case SOFIA:
                return "Sofia 1";
            case PLOVDIV:
                return "Plovdiv";
            case VARNA:
                return "Varna";
            case BURGAS:
                return "Burgas";
            default:
                throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}