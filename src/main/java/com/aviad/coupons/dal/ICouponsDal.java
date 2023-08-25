package com.aviad.coupons.dal;

import com.aviad.coupons.dto.Coupon;
import com.aviad.coupons.entities.CouponEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICouponsDal extends CrudRepository<CouponEntity, Integer> {
    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price)" +
            " FROM CouponEntity c")
    List<Coupon> getCoupons();

    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price)" +
            " FROM CouponEntity c WHERE c.id = :id")
    Coupon getCoupon(@Param("id") int id);

    //get all coupons by company id
    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price)" +
            " FROM CouponEntity c  WHERE c.company.id = :id")
    List<Coupon> getCouponsByCompanyId(@Param("id") int id);

    // get all coupons bellow specific price
    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price)" +
            " FROM CouponEntity c  WHERE c.price <= :maxPrice")
    List<Coupon> getCouponsByMaxPrice(@Param("maxPrice") float maxPrice);

    // get all coupons by category
    @Query("SELECT new com.aviad.coupons.dto.Coupon (c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.category.name, c.company.name, c.user.username, c.price)" +
            " FROM CouponEntity c  WHERE c.category.id = :id")
    List<Coupon> getCouponsByCategoryId(@Param("id") short id);

}
