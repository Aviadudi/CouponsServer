package com.aviad.coupons.entities;

import com.aviad.coupons.dto.Company;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
public class CompanyEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    // Foreign key to users
    @OneToMany(mappedBy = "company")
    private List<UserEntity> employees;

    // Foreign key to coupons
    @OneToMany (mappedBy = "company")
    private List<CouponEntity> coupons;

    // Foreign key to purchases
    @OneToMany (mappedBy = "company")
    private List<PurchaseEntity> purchases;

    public CompanyEntity(){}
    public CompanyEntity(Company company){
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.phone = company.getPhone();
        this.email = company.getEmail();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserEntity> employees) {
        this.employees = employees;
    }

    public List<CouponEntity> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponEntity> coupons) {
        this.coupons = coupons;
    }

    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
    }
}
