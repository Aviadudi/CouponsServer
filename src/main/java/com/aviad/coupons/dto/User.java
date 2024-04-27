package com.aviad.coupons.dto;


import com.aviad.coupons.entities.UserEntity;
import com.aviad.coupons.enums.UserType;

import java.util.Objects;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private UserType userType;
    private Integer companyId;

    public User() {
    }

    public User(int id, String username, String password, String email, UserType userType, Integer companyId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
        this.companyId = companyId;
    }

    //Ctor to register company user
    public User(String username, String password, String email, UserType userType, Integer companyId) {
        this(0, username, password, email, userType, companyId);
    }

    // Ctor to register admin or customer user
    public User(String username, String password, String email, UserType userType) {
        this(username, password, email, userType, null);
    }

    public User(UserEntity userEntity) {
        this(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getEmail(), userEntity.getUserType(), userEntity.getCompany().getId());
    }

    // Ctor to update user (just to be explicit)
    public User(String username, String password, String email) {
        this(0, username, password, email, null, null);
    }

    // Ctor for get user without the password
    public User(int id, String username, String email, UserType userType, Integer companyId) {
        this(id, username, null, email, userType, companyId);
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", companyId=" + companyId +
                '}';
    }
}
