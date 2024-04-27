package com.aviad.coupons.controllers;

import com.aviad.coupons.dto.Coupon;
import com.aviad.coupons.exceptions.ApplicationException;
import com.aviad.coupons.logic.CouponLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @PostMapping
    public void createCoupon(@RequestBody Coupon coupon, @RequestHeader("Authorization") String token) throws ApplicationException {
        this.couponLogic.addCoupon(coupon, token);
    }

    @PutMapping
    public void updateCoupon(@RequestBody Coupon coupon, @RequestHeader("Authorization") String token) throws ApplicationException {
        this.couponLogic.updateCoupon(coupon, token);
    }

    @GetMapping
    public List<Coupon> getCoupons() throws ApplicationException {
        return this.couponLogic.getAllCoupons();
    }

    @GetMapping("/byFilters")
    public Page<Coupon> getCouponsByFilters(@RequestHeader(value = "Authorization", required = false) String token,
                                            @RequestParam(value = "page") int page,
                                            @RequestParam(value = "categoryIds") Integer[] categoryIds,
                                            @RequestParam(value = "searchInput", required = false) String searchInput,
                                            @RequestParam(value = "minPrice") float minPrice,
                                            @RequestParam(value = "maxPrice") float maxPrice) throws ApplicationException {
        return this.couponLogic.getCouponsByFilters(token, page, categoryIds, searchInput, minPrice, maxPrice);
    }

    @GetMapping("/{id}")
    public Coupon getCoupon(@PathVariable("id") Integer id) throws ApplicationException {
        return this.couponLogic.getCoupon(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token) throws ApplicationException {
        this.couponLogic.deleteCoupon(id, token);
    }

    @GetMapping("/minPrice")
    public Float getMinPrice(@RequestHeader(value = "Authorization", required = false) String token) throws Exception {
        return this.couponLogic.getMinPrice();
    }

    @GetMapping("/maxPrice")
    public Float getMaxPrice(@RequestHeader(value = "Authorization", required = false) String token) throws Exception {
        return this.couponLogic.getMaxPrice();
    }
}
