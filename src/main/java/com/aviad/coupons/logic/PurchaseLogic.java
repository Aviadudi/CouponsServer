package com.aviad.coupons.logic;


import com.aviad.coupons.dal.IPurchasesDal;
import com.aviad.coupons.dto.Coupon;
import com.aviad.coupons.dto.Purchase;
import com.aviad.coupons.entities.PurchaseEntity;
import com.aviad.coupons.enums.ErrorType;
import com.aviad.coupons.exceptions.ApplicationException;
import com.aviad.coupons.utils.TokenDecodedDataUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class PurchaseLogic {
    private IPurchasesDal purchasesDal;
    private CouponLogic couponLogic;

    public PurchaseLogic(IPurchasesDal purchasesDal, CouponLogic couponLogic) {
        this.purchasesDal = purchasesDal;
        this.couponLogic = couponLogic;
    }

    @Transactional(rollbackFor = Exception.class)
    public long addPurchase(Purchase purchase, String token) throws ApplicationException {
        int userId = TokenDecodedDataUtil.decodedUserId(token);
        int couponId = purchase.getCouponId();

        Coupon coupon = couponLogic.getCouponData(couponId);
        int companyId = coupon.getCompanyId();
        float couponPrice = coupon.getPrice();
        int amount = purchase.getAmount();
        float price = couponPrice * amount;

        Date purchaseDate = new Date();

        purchase.setUserId(userId);
        purchase.setCompanyId(companyId);
        purchase.setPrice(price);
        purchase.setPurchaseDate(purchaseDate);

        validatePurchaseData(purchase);

        PurchaseEntity purchaseEntity = new PurchaseEntity(purchase);
        this.purchasesDal.save(purchaseEntity);
        reduceCouponAmount(purchase);
        purchase.setId(purchaseEntity.getId());
        return purchase.getId();
    }

    private void reduceCouponAmount(Purchase purchase) throws ApplicationException {
        int couponId = purchase.getCouponId();
        int purchaseAmount = purchase.getAmount();

        this.couponLogic.updateCouponAmountAfterPurchase(couponId, purchaseAmount);
    }

    public Purchase getPurchase(long id) throws ApplicationException {
        return this.purchasesDal.getPurchase(id);
    }

    public void deletePurchase(long id) throws ApplicationException {
        this.purchasesDal.deleteById(id);
    }

    public void updatePurchase(Purchase purchase, String token) throws ApplicationException {
        int userId = TokenDecodedDataUtil.decodedUserId(token);
        int couponId = purchase.getCouponId();



        validatePurchaseData(purchase);
        PurchaseEntity purchaseEntity = new PurchaseEntity(purchase);
        this.purchasesDal.save(purchaseEntity);
    }

    public List<Purchase> getAllPurchases() throws ApplicationException {
        return this.purchasesDal.getPurchases();
    }

        public List<Purchase> getPurchasesByCompanyId(int id) throws ApplicationException {
        return this.purchasesDal.getPurchasesByCompanyId(id);
    }

    public List<Purchase> getPurchasesByUserId(int id) throws ApplicationException {
        return this.purchasesDal.getPurchasesByUserId(id);
    }

    public List<Purchase> getPurchasesByCategoryId(short id) throws ApplicationException {
        return this.purchasesDal.getPurchasesByCategoryId(id);
    }
//
//    public List getPurchasesByDateRange(Date startDate, Date endDate) throws ApplicationException {
//        return this.purchasesDal.getPurchasesByDateRange(startDate, endDate);
//    }

    private void validatePurchaseData(Purchase purchase) throws ApplicationException{
        // validate user id
        if (purchase.getUserId() <= 0){
            throw new ApplicationException(ErrorType.INVALID_USER_ID);
        }
        //validate coupon id
        if (purchase.getCouponId() <= 0){
            throw new ApplicationException(ErrorType.INVALID_COUPON_ID);
        }

        validatePurchaseDate(purchase);
        validatePurchaseAmount(purchase);
    }

    private void validatePurchaseAmount(Purchase purchase) throws ApplicationException {
        if (purchase.getAmount() <= 0){
            throw new ApplicationException(ErrorType.INVALID_PURCHASE_AMOUNT);
        }

        int couponId = purchase.getCouponId();
        Coupon coupon = couponLogic.getCoupon(couponId);
        int couponAmountLeft = coupon.getAmount();
        if (purchase.getAmount() > couponAmountLeft){
            throw new ApplicationException(ErrorType.INVALID_COUPON_AMOUNT_TO_PURCHASE, "Can't purchase more then " + couponAmountLeft + " coupons");
        }
    }

    private void validatePurchaseDate(Purchase purchase) throws ApplicationException {
        Date currentDate = new Date();
        if (purchase.getPurchaseDate().after(currentDate)){
            throw new ApplicationException(ErrorType.INVALID_PURCHASE_DATE);
        }
    }
}
