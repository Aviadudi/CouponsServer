package com.aviad.coupons.dto;


import com.aviad.coupons.entities.CouponEntity;

import java.util.Date;

public class Coupon {
    private int id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private String categoryName;
    private short categoryId;
    private String companyName;
    private int companyId;
    private String userName;
    private int userId;
    private float price;


    public Coupon() {
    }

    public Coupon(int id, String name, String description, Date startDate, Date endDate, int amount, String categoryName, short categoryId, String companyName, int companyId, String userName, int userId, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.companyName = companyName;
        this.companyId = companyId;
        this.userName = userName;
        this.userId = userId;
        this.price = price;
    }

    // Ctor for export data from DB
    public Coupon(int id, String name, String description, Date startDate, Date endDate, int amount, short categoryId, int companyId, int userId, float price) {
        this(id, name, description, startDate, endDate, amount, null, categoryId, null, companyId, null, userId, price);
    }


    //Ctor for export data to client from DB
    public Coupon(int id, String name, String description, java.util.Date startDate, java.util.Date endDate, int amount, String categoryName, String companyName, String userName, float price) {
        this(id, name, description, startDate, endDate, amount, categoryName, (short) 0, companyName, 0, userName, 0, price);
    }


    // Ctor for insert data to DB
    public Coupon(String name, String description, Date startDate, Date endDate, int amount, short categoryId, int companyId, int userId, float price) {
        this(0, name, description, startDate, endDate, amount, null, categoryId, null, companyId, null, userId, price);
    }

    public Coupon(CouponEntity couponEntity) {
        this(couponEntity.getId(),
                couponEntity.getName(),
                couponEntity.getDescription(),
                couponEntity.getStartDate(),
                couponEntity.getEndDate(),
                couponEntity.getAmount(),
                couponEntity.getCategory().getName(),
                couponEntity.getCategory().getId(),
                couponEntity.getCompany().getName(),
                couponEntity.getCompany().getId(),
                couponEntity.getUser().getUsername(),
                couponEntity.getUser().getId(),
                couponEntity.getPrice());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public short getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(short categoryId) {
        this.categoryId = categoryId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", categoryName='" + categoryName + '\'' +
                ", categoryId=" + categoryId +
                ", companyName='" + companyName + '\'' +
                ", companyId=" + companyId +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                ", price=" + price +
                '}';
    }
}

