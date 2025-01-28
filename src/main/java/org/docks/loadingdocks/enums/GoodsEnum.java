package org.docks.loadingdocks.enums;

public enum GoodsEnum {
    CABINET_FURNITURE,
    UPHOLSTERED_FURNITURE,
    GARDEN_FURNITURE,
    MATTRESSES;


    //Ваадин автоматично ще вземе toString() метода
    @Override
    public String toString() {
        switch (this) {
            case CABINET_FURNITURE:
                return "Cabinet furniture";
            case UPHOLSTERED_FURNITURE:
                return "Upholster furniture";
            case GARDEN_FURNITURE:
                return "Garden furniture";
            case MATTRESSES:
                return "Mattresses";
            default:
                throw new IllegalArgumentException("Unexpected value: " + this);
        }
    }
}
