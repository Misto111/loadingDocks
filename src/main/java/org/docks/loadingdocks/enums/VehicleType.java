package org.docks.loadingdocks.enums;

public enum VehicleType {

    VAN("Van"),
    CARGO_TRUCK("Cargo truck"),
    TRUCK("TRUCK"),
    TRUCK_WITH_TRAILER("Truck with trailer");

    private final String label;

    VehicleType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


    @Override
    public String toString() {
        switch (this) {
            case VAN:
                return "Van";
            case CARGO_TRUCK:
                return "Cargo truck";
            case TRUCK:
                return "Truck";
            case TRUCK_WITH_TRAILER:
                return "Truck with trailer";
            default:
                throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}
