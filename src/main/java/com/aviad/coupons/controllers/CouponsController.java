package com.aviad.coupons.controllers;

import com.aviad.coupons.dto.Coupon;
import com.aviad.coupons.exceptions.ApplicationException;
import com.aviad.coupons.logic.CouponLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponsController {
    private CouponLogic couponLogic;

    @Autowired
    public CouponsController(CouponLogic couponLogic) {
        this.couponLogic = couponLogic;
    }

    // Add coupon
    @PostMapping
    public void createCoupon(@RequestBody Coupon coupon, @RequestHeader("Authorization") String token) throws ApplicationException {
        this.couponLogic.addCoupon(coupon, token);
    }

    // Update coupon
    @PutMapping
    public void updateCoupon(@RequestBody Coupon coupon, @RequestHeader("Authorization") String token) throws ApplicationException {
        System.out.println();
        this.couponLogic.updateCoupon(coupon, token);
    }

    // Get all coupons
    @GetMapping
    public List<Coupon> getCoupons() throws ApplicationException {
        return this.couponLogic.getAllCoupons();
    }

    @GetMapping("/accordingUserType")
    public List<Coupon> getCouponsAccordingUserType(@RequestHeader("Authorization") String token) throws ApplicationException {
        return this.couponLogic.getAllCouponsAccordingUserType(token);
    }
    // Get all available coupons
//    @GetMapping("/available")
//    public List<Coupon> getAllAvailableCoupons() throws ApplicationException{
//        return this.couponLogic.getAllAvailableCoupons();
//    }

    // Get specific coupon
    @GetMapping("/{id}")
    public Coupon getCoupon(@PathVariable("id") Integer id) throws ApplicationException {
        return this.couponLogic.getCoupon(id);
    }

//    @GetMapping("/couponData")
//    public Coupon getCouponData(@RequestParam("id") Integer id) throws ApplicationException {
//        return this.couponLogic.getCouponData(id);
//    }

    // Delete specific coupon
    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token) throws ApplicationException {
        this.couponLogic.deleteCoupon(id, token);
    }

    // Get all coupons below specific price
    @GetMapping("/byMaxPrice")
    public List<Coupon> getCouponsByMaxPrice(@RequestParam("maxPrice") float maxPrice) throws ApplicationException {
        return this.couponLogic.getCouponsByMaxPrice(maxPrice);
    }

    // Get all coupons by company id
    @GetMapping("/byCompanyId")
    public List<Coupon> getCouponsByCompanyId(@RequestParam("id") int id) throws ApplicationException {
        return this.couponLogic.getCouponsByCompanyId(id);
    }

    // Get all coupons by category id
    @GetMapping("/byCategoryId")
    public List<Coupon> getCouponsByCategoryId(@RequestParam("id") short id) throws ApplicationException {
        return this.couponLogic.getCouponsByCategoryId(id);
    }
}
