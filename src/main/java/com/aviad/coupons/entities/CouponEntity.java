package com.aviad.coupons.entities;

import com.aviad.coupons.dto.Coupon;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "coupons")
public class CouponEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "price", nullable = false)
    private float price;

    // Foreign key to users
    @ManyToOne (fetch = FetchType.LAZY)
    private UserEntity user;

    //Foreign key to category
    @ManyToOne (fetch = FetchType.LAZY)
    private CategoryEntity category;

    //Foreign key to company
    @ManyToOne (fetch = FetchType.LAZY)
    private CompanyEntity company;

    // Foreign key purchases
    @OneToMany (mappedBy = "coupon" , fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchases;

    public CouponEntity(){}

    public CouponEntity(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.description = coupon.getDescription();
        this.startDate = coupon.getStartDate();
        this.endDate = coupon.getEndDate();
        this.amount = coupon.getAmount();
        this.price = coupon.getPrice();
        this.category = new CategoryEntity();
        this.category.setId(coupon.getCategoryId());
        this.company = new CompanyEntity();
        this.company.setId(coupon.getCompanyId());
        this.user = new UserEntity();
        this.user.setId(coupon.getUserId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
    }
}
