package com.aviad.coupons.dto;

import java.util.List;

public class CouponsPagination {
    private List<Coupon> coupons;
    private int pages;

    public CouponsPagination(List<Coupon> coupons, int pages) {
        this.coupons = coupons;
        this.pages = pages;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}

