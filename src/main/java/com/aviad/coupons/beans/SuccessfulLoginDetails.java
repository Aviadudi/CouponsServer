package com.aviad.coupons.beans;

import com.aviad.coupons.enums.UserType;

public class SuccessfulLoginDetails {
    private int id;
    private UserType userType;
    private Integer companyId;

    public SuccessfulLoginDetails() {
    }

    public SuccessfulLoginDetails(int id, UserType userType, Integer companyId) {
        this.id = id;
        this.userType = userType;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
