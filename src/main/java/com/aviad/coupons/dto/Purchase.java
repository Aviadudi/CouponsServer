package com.aviad.coupons.dto;

import com.aviad.coupons.entities.PurchaseEntity;

import java.util.Date;

public class Purchase {
    private long id;
    private int userId;
    private int couponId;
    private String couponName;
    private String couponDescription;
    private int companyId;
    private String companyName;
    private int amount;
    private Date purchaseDate;
    private float price;

    public Purchase(){}

    public Purchase(long id, int amount, Date purchaseDate, int userId, int couponId, int companyId, float price, String couponName, String couponDescription, String companyName){
        this.id = id;
        this.amount = amount;
        this.userId = userId;
        this.couponId = couponId;
        this.companyId = companyId;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.companyName = companyName;
    }

    //Ctor for export data from DB
    public Purchase(long id, int amount, Date purchaseDate, int userId, int couponId, int companyId, float price) {
        this.id = id;
        this.amount = amount;
        this.userId = userId;
        this.couponId = couponId;
        this.companyId = companyId;
        this.purchaseDate = purchaseDate;
        this.price = price;
    }

    // Ctor for insert data to DB
    public Purchase( int amount, Date purchaseDate, int userId, int couponId, int companyId, float price) {
        this(0, amount, purchaseDate, userId, couponId, companyId, price);
    }

    //Ctor for export data from DB to client
    public Purchase(long id, int amount, Date purchaseDate, int userId, String couponName, String companyName, float price){
        this.id = id;
        this.amount = amount;
        this.purchaseDate = new java.sql.Date(purchaseDate.getTime());
        this.userId = userId;
        this.couponName = couponName;
        this.companyName = companyName;
        this.price = price;
    }

    public Purchase(PurchaseEntity purchaseEntity){
        this(purchaseEntity.getId(), purchaseEntity.getAmount(), purchaseEntity.getPurchaseDate(), purchaseEntity.getUser().getId(), purchaseEntity.getCoupon().getId(), purchaseEntity.getCompany().getId(), purchaseEntity.getPrice());
    }

    public Purchase (int couponId, int amount){
        this.couponId = couponId;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", userId=" + userId +
                ", couponId=" + couponId +
                ", couponName='" + couponName + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", amount=" + amount +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}

