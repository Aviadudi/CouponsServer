package com.aviad.coupons.entities;

import com.aviad.coupons.dto.User;
import com.aviad.coupons.enums.UserType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Foreign key to company
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyEntity company;

    // Foreign key to coupons
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CouponEntity> coupons;

    // Foreign key to purchases
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchases;

    public UserEntity() {
    }

    public UserEntity(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.userType = user.getUserType();
        this.company = new CompanyEntity();
        setCompanyId(user);
    }

    private void setCompanyId(User user) {
        if (user.getCompanyId() == null) {
            this.company = null;
        } else {
            this.company.setId(user.getCompanyId());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
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
