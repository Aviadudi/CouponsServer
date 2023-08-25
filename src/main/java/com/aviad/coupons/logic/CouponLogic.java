package com.aviad.coupons.logic;


import com.aviad.coupons.dal.ICouponsDal;
import com.aviad.coupons.dto.Coupon;
import com.aviad.coupons.dto.User;
import com.aviad.coupons.entities.CouponEntity;
import com.aviad.coupons.enums.ErrorType;
import com.aviad.coupons.enums.UserType;
import com.aviad.coupons.exceptions.ApplicationException;
import com.aviad.coupons.utils.TokenDecodedDataUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponLogic {
    private ICouponsDal couponsDal;
    private UserLogic userLogic;

    public CouponLogic(ICouponsDal couponsDal, UserLogic userLogic) {
        this.couponsDal = couponsDal;
        this.userLogic = userLogic;
    }

    public Integer addCoupon(Coupon coupon, String token) throws ApplicationException {
        UserType userType = TokenDecodedDataUtil.decodedUserType(token);

        if (userType == UserType.CUSTOMER){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_TO_ADD_COUPON);
        }

        int companyId = TokenDecodedDataUtil.decodedCompanyId(token);
        int userId = TokenDecodedDataUtil.decodedUserId(token);

        coupon.setCompanyId(companyId);
        coupon.setUserId(userId);

        validateCouponData(coupon);

        CouponEntity couponEntity = new CouponEntity(coupon);
        this.couponsDal.save(couponEntity);
        coupon.setId(couponEntity.getId());
        return coupon.getId();
    }

    public Coupon getCoupon(Integer id) throws ApplicationException {
        return this.couponsDal.getCoupon(id);
    }

    public Coupon getCouponData(Integer id){
        CouponEntity couponEntity = couponsDal.findById(id).get();
        Coupon coupon = new Coupon(couponEntity);
        return coupon;
    }

    public void deleteCoupon(Integer id,String token) throws ApplicationException {
        validateUserPermissionToDelete(id, token);
        this.couponsDal.deleteById(id);
    }



    public void updateCoupon(Coupon coupon, String token) throws ApplicationException {
        UserType userType = TokenDecodedDataUtil.decodedUserType(token);

        if (userType == UserType.CUSTOMER){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_TO_UPDATE_COUPON);
        }

        int companyId = TokenDecodedDataUtil.decodedCompanyId(token);
        int userId = TokenDecodedDataUtil.decodedUserId(token);


        coupon.setCompanyId(companyId);
        coupon.setUserId(userId);

        validateCouponData(coupon);
        CouponEntity couponEntity = new CouponEntity(coupon);
        this.couponsDal.save(couponEntity);
    }

    // update amount without insert data from token because every one can purchase the coupon
    public void updateCouponAmountAfterPurchase(int couponId, int purchaseAmount) throws ApplicationException {
        Coupon coupon = getCouponData(couponId);
        int couponAmount = coupon.getAmount();
        int newCouponAmount = couponAmount - purchaseAmount;

        if (newCouponAmount < 0) {
            throw new ApplicationException(ErrorType.INVALID_COUPON_AMOUNT_TO_PURCHASE);
        }

        coupon.setAmount(newCouponAmount);
        CouponEntity couponEntity = new CouponEntity(coupon);
        couponsDal.save(couponEntity);
    }

    public List<Coupon> getAllCoupons() throws ApplicationException {
        return this.couponsDal.getCoupons();
    }

        public List<Coupon> getCouponsByMaxPrice(float maxPrice) throws ApplicationException {
        validateCouponPrice(maxPrice);
        return this.couponsDal.getCouponsByMaxPrice(maxPrice);
    }

    public List<Coupon> getCouponsByCompanyId(int companyId) throws ApplicationException{
        return this.couponsDal.getCouponsByCompanyId(companyId);
    }

    public List<Coupon> getCouponsByCategoryId(short categoryId) throws ApplicationException {
        return this.couponsDal.getCouponsByCategoryId(categoryId);
    }

    private void validateCouponData(Coupon coupon) throws ApplicationException {
        validateCouponName(coupon.getName());
        validateCouponDescription(coupon.getDescription());
        validateCouponPrice(coupon.getPrice());
        validateCouponAmount(coupon.getAmount());
        validateCouponDate(coupon.getStartDate(), coupon.getEndDate());
    }

    private void validateCouponDate(Date startDate, Date endDate) throws ApplicationException {
        Date currentDate = new Date();
        if (currentDate.after(startDate)) {
            throw new ApplicationException(ErrorType.INVALID_COUPON_START_DATE, ErrorType.INVALID_COUPON_START_DATE.getErrorMessage());
        }
        if (currentDate.after(endDate)) {
            throw new ApplicationException(ErrorType.INVALID_COUPON_END_DATE, ErrorType.INVALID_COUPON_END_DATE.getErrorMessage());
        }
        if (endDate.before(startDate)) {
            throw new ApplicationException(ErrorType.INVALID_COUPON_DATE_ORDER, ErrorType.INVALID_COUPON_DATE_ORDER.getErrorMessage());
        }
    }

    private void validateCouponAmount(int amount) throws ApplicationException {
        if (amount <= 0) {
            throw new ApplicationException(ErrorType.INVALID_COUPON_AMOUNT, ErrorType.INVALID_COUPON_AMOUNT.getErrorMessage());
        }
    }

    private void validateCouponPrice(float price) throws ApplicationException {
        if (price <= 0) {
            throw new ApplicationException(ErrorType.COUPON_PRICE_TOO_LOW, ErrorType.COUPON_PRICE_TOO_LOW.getErrorMessage());
        }
        if (price > 100) {
            throw new ApplicationException(ErrorType.COUPON_PRICE_TOO_HIGH, ErrorType.COUPON_PRICE_TOO_HIGH.getErrorMessage());
        }
    }

    private void validateCouponDescription(String description) throws ApplicationException {
        if (description.length() < 10) {
            throw new ApplicationException(ErrorType.COUPON_DESCRIPTION_TOO_SHORT, ErrorType.COUPON_DESCRIPTION_TOO_SHORT.getErrorMessage());
        }
        if (description.length() > 100) {
            throw new ApplicationException(ErrorType.COUPON_DESCRIPTION_TOO_LONG, ErrorType.COUPON_DESCRIPTION_TOO_LONG.getErrorMessage());
        }
    }

    private void validateCouponName(String couponName) throws ApplicationException {
        if (couponName.length() < 4) {
            throw new ApplicationException(ErrorType.COUPON_NAME_TOO_SHORT, ErrorType.COUPON_NAME_TOO_SHORT.getErrorMessage());
        }
        if (couponName.length() > 15) {
            throw new ApplicationException(ErrorType.COUPON_NAME_TOO_LONG, ErrorType.COUPON_NAME_TOO_LONG.getErrorMessage());
        }
    }
    // Validate if user is ADMIN or company user from the same company as the coupon
    private void validateUserPermissionToDelete(Integer id, String token) throws ApplicationException {
        int userId = TokenDecodedDataUtil.decodedUserId(token);
        User user = userLogic.getUser(userId);
        if (user.getUserType().equals(UserType.CUSTOMER)){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_TO_DELETE_COUPON);
        }

        Coupon coupon = getCouponData(id);
        int couponCompanyId = coupon.getCompanyId();
        int userCompanyId = TokenDecodedDataUtil.decodedCompanyId(token);
        if (userCompanyId != couponCompanyId){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_TO_DELETE_COUPON);
        }
    }
}
