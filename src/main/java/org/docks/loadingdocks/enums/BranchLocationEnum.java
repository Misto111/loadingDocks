package org.docks.loadingdocks.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BranchLocationEnum {


    SOFIA("Aiko XXXL Sofia, bul. Europe 441", 42.6977, 23.3219, "bul. Europe №441", "10:00 - 20:00", "https://aiko-bg.com/media/shop_images/Aiko-varna-Shop.jpg","https://aiko-bg.com/affiliates/grad-sofia-1/", "0898305845"),
    PLOVDIV("Aiko XXXL Plovdiv", 42.1354, 24.7453, "str. Ivan Vazov №14", "10:00 - 20:00", "https://aiko-bg.com/media/shop_images/aiko-pv.png", "https://aiko-bg.com/affiliates/grad-plovdiv-06/", "0898305845"),
    VARNA("Aiko XXXL Varna", 43.2141, 27.9147, "Varna, bul. Vladislav Varnenchik №277А", "10:00 - 20:00", "https://aiko-bg.com/media/shop_images/Aiko-varna-Shop.jpg", "https://aiko-bg.com/affiliates/gr-varna/", "0898305845"),
    BURGAS("Aiko XXXL Burgas", 42.504792, 27.462636, "Burgas, bul. Ianko Komitov №8", "10:00 - 20:00", "https://aiko-bg.com/media/shop_images/Aiko-Burgas-ShopN.jpg", "https://aiko-bg.com/affiliates/grad-burgas-01/", "0898305845");

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