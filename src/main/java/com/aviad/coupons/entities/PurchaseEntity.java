package com.aviad.coupons.entities;

import com.aviad.coupons.dto.Purchase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchases")
public class PurchaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "purchase_date", nullable = false)
    private Date purchaseDate;

    @Column(name = "total_price", nullable = false)
    private float price;

    @ManyToOne (fetch = FetchType.LAZY)
    private CompanyEntity company;

    @ManyToOne (fetch = FetchType.LAZY)
    private CouponEntity coupon;

    @ManyToOne (fetch = FetchType.LAZY)
    private UserEntity user;

    public PurchaseEntity(){}

    public PurchaseEntity(Purchase purchase){
        this.id = purchase.getId();
        this.amount = purchase.getAmount();
        this.purchaseDate = purchase.getPurchaseDate();
        this.price = purchase.getPrice();
        this.company = new CompanyEntity();
        this.company.setId(purchase.getCompanyId());
        this.coupon = new CouponEntity();
        this.coupon.setId(purchase.getCouponId());
        this.user = new UserEntity();
        this.user.setId(purchase.getUserId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
