package com.aviad.coupons.enums;

public enum UserType {
    CUSTOMER("CUSTOMER"),
    ADMIN("ADMIN"),
    COMPANY("COMPANY");

    private String typeName;
    UserType (String typeName){
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
