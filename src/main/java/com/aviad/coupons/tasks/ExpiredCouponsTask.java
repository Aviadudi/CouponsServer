//package com.aviad.coupons.tasks;
//
//
//import com.aviad.coupons.exceptions.ApplicationException;
//import com.aviad.coupons.logic.CouponLogic;
//
//import java.util.TimerTask;
//
//public class ExpiredCouponsTask extends TimerTask {
//    private CouponLogic couponLogic;
//
//    public ExpiredCouponsTask(){
//        this.couponLogic = new CouponLogic();
//    }
//
//    @Override
//    public void run() {
//        try {
//            couponLogic.deleteExpiredCoupons();
//        }
//        catch (ApplicationException e){
//            e.printStackTrace();
//        }
//    }
//}
