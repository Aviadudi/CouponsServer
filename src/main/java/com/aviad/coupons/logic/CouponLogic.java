package com.aviad.coupons.logic;


import com.aviad.coupons.beans.SuccessfulLoginDetails;
import com.aviad.coupons.dal.ICategoriesDal;
import com.aviad.coupons.dal.ICouponsDal;
import com.aviad.coupons.dto.Coupon;
import com.aviad.coupons.dto.User;
import com.aviad.coupons.entities.CouponEntity;
import com.aviad.coupons.enums.ErrorType;
import com.aviad.coupons.enums.UserType;
import com.aviad.coupons.exceptions.ApplicationException;
import com.aviad.coupons.utils.TokenDecodedDataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponLogic {
    private ICouponsDal couponsDal;
    private UserLogic userLogic;
    private ICategoriesDal categoriesDal;

    public CouponLogic(ICouponsDal couponsDal, UserLogic userLogic, ICategoriesDal categoriesDal) {
        this.couponsDal = couponsDal;
        this.userLogic = userLogic;
        this.categoriesDal = categoriesDal;
    }

    public Integer addCoupon(Coupon coupon, String token) throws ApplicationException {
        coupon = initiateCouponData(coupon, token);
        validateCouponData(coupon);

        CouponEntity couponEntity = new CouponEntity(coupon);
        couponEntity = this.couponsDal.save(couponEntity);
        coupon.setId(couponEntity.getId());
        return coupon.getId();
    }

    private Coupon initiateCouponData(Coupon coupon, String token) throws ApplicationException {
        SuccessfulLoginDetails userData = TokenDecodedDataUtil.decodedUserData(token);
        UserType userType = userData.getUserType();

        if (userType == UserType.ADMIN) {
            coupon.setCompanyId(coupon.getCompanyId());
        }
        if (userType == UserType.COMPANY) {
            int companyId = userData.getCompanyId();
            coupon.setCompanyId(companyId);
        } else {
            // mean you are customer
            throw new ApplicationException(ErrorType.UNAUTHORIZED_TO_ADD_COUPON);
        }
        int userId = userData.getId();
        coupon.setUserId(userId);
        return coupon;
    }

    public Coupon getCoupon(Integer id) throws ApplicationException {
        return this.couponsDal.getCoupon(id);
    }

    public Coupon getCouponData(Integer id) {
        CouponEntity couponEntity = couponsDal.findById(id).get();
        Coupon coupon = new Coupon(couponEntity);
        return coupon;
    }

    public void deleteCoupon(Integer id, String token) throws ApplicationException {
        validateUserPermissionToDelete(id, token);
        this.couponsDal.deleteById(id);
    }

    public void updateCoupon(Coupon coupon, String token) throws ApplicationException {
        coupon = initiateUpdatedCouponData(coupon, token);
        validateCouponData(coupon);
        CouponEntity couponEntity = new CouponEntity(coupon);
        this.couponsDal.save(couponEntity);
    }

    private Coupon initiateUpdatedCouponData(Coupon coupon, String token) throws ApplicationException {
        SuccessfulLoginDetails userData = TokenDecodedDataUtil.decodedUserData(token);
        Coupon oldCoupon = getCouponData(coupon.getId());
        if (coupon.getCompanyId() == 0){
            coupon.setCompanyId(oldCoupon.getCompanyId());
        }
        if (coupon.getCategoryId() == 0){
            coupon.setCategoryId(oldCoupon.getCategoryId());
        }
        UserType userType = userData.getUserType();
        if (userType == UserType.CUSTOMER || userType == UserType.COMPANY && userData.getCompanyId() != coupon.getCompanyId()) {
            throw new ApplicationException(ErrorType.UNAUTHORIZED_TO_UPDATE_COUPON);
        } else if (userType == UserType.COMPANY || coupon.getCompanyId()==0) {
            coupon.setUserId((userData.getId()));
            coupon.setCompanyId(oldCoupon.getCompanyId());
        } else {
            coupon.setUserId(oldCoupon.getUserId());
        }
        coupon.setId(oldCoupon.getId());
        return coupon;
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
        return getAllAvailableCoupons();
    }

    public Page<Coupon> getCouponsByFilters(String token, int page, Integer[] categoryIds, String searchInput, float minPrice, float maxPrice) throws ApplicationException {
        if (categoryIds[0] == -1 || !searchInput.equals("")) {
            categoryIds = categoriesDal.getAllCategoryIds();
        }
        UserType userType;
        if (token != null) {
            userType = extractUserType(token);
        } else {
            userType = UserType.CUSTOMER;
        }

        int couponsPerPage;
        if (userType == UserType.CUSTOMER) {
            couponsPerPage = 5;
        } else {
            couponsPerPage = 4;
        }

        Pageable pageable = PageRequest.of(page - 1, couponsPerPage);
        Page<Coupon> DBCouponsResponse;

        // Admin
        if (userType == UserType.ADMIN) {
            DBCouponsResponse = this.couponsDal.getByFiltersForAdmin(categoryIds, searchInput, minPrice, maxPrice, pageable);
        }
        // Company
        else if (userType == UserType.COMPANY) {
            int companyId = TokenDecodedDataUtil.decodedCompanyId(token);
            DBCouponsResponse = this.couponsDal.getByFiltersForCompany(companyId, categoryIds, searchInput, minPrice, maxPrice, pageable);
        }
        // Customer
        else {
            DBCouponsResponse = this.couponsDal.getByFiltersForCustomer(new Date(), categoryIds, searchInput, minPrice, maxPrice, pageable);
        }

        return DBCouponsResponse;
    }

    private UserType extractUserType(String token) throws ApplicationException {
        if (token == null) {
            return UserType.CUSTOMER;
        } else {
            SuccessfulLoginDetails successfulLoginDetails = TokenDecodedDataUtil.decodedUserData(token);
            UserType userType = successfulLoginDetails.getUserType();
            return userType;
        }
    }

    public List<Coupon> getAllAvailableCoupons() throws ApplicationException {
        return this.couponsDal.getAvailableCoupons(new Date());
    }

    private void validateCouponData(Coupon coupon) throws ApplicationException {
        validateCouponName(coupon.getName());
        validateCouponDescription(coupon.getDescription());
        validateCouponPrice(coupon.getPrice());
        validateCouponAmount(coupon.getAmount());
        validateCouponDate(coupon.getStartDate(), coupon.getEndDate());
    }

    private void validateCouponDate(Date startDate, Date endDate) throws ApplicationException {
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
        if (price > 5000) {
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
        if (couponName.length() > 30) {
            throw new ApplicationException(ErrorType.COUPON_NAME_TOO_LONG, ErrorType.COUPON_NAME_TOO_LONG.getErrorMessage());
        }
    }

    // Validate if user is ADMIN or company user from the same company as the coupon
    private void validateUserPermissionToDelete(Integer id, String token) throws ApplicationException {
        int userId = TokenDecodedDataUtil.decodedUserId(token);
        User user = userLogic.getUser(userId);
        if (user.getUserType().equals(UserType.CUSTOMER)) {
            throw new ApplicationException(ErrorType.UNAUTHORIZED_TO_DELETE_COUPON);
        } else if (user.getUserType().equals(UserType.COMPANY)) {
            Coupon coupon = getCouponData(id);
            int couponCompanyId = coupon.getCompanyId();
            int userCompanyId = TokenDecodedDataUtil.decodedCompanyId(token);
            if (userCompanyId != couponCompanyId) {
                throw new ApplicationException(ErrorType.UNAUTHORIZED_TO_DELETE_COUPON);
            }
        }
    }

    public Float getMaxPrice() throws ApplicationException {
        Float maxPrice;
        try {
            maxPrice = couponsDal.getMaxPrice();
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to retrieve highest coupon price", e);
        }
        return maxPrice;
    }

    public Float getMinPrice() throws ApplicationException {
        Float minPrice;
        try {
            minPrice = couponsDal.getMinPrice();
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to retrieve lowest coupon price", e);
        }
        return minPrice;
    }
}
