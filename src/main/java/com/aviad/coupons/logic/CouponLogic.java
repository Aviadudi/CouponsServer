package com.aviad.coupons.logic;


import com.aviad.coupons.beans.SuccessfulLoginDetails;
import com.aviad.coupons.dal.ICategoriesDal;
import com.aviad.coupons.dal.ICouponsDal;
import com.aviad.coupons.dto.Coupon;
import com.aviad.coupons.dto.CouponsPagination;
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
        UserType userType = TokenDecodedDataUtil.decodedUserType(token);

        if (userType == UserType.CUSTOMER) {
            throw new ApplicationException(ErrorType.UNAUTHORIZED_TO_ADD_COUPON);
        }
        int userId = TokenDecodedDataUtil.decodedUserId(token);

        if (userType == UserType.COMPANY) {
             int companyId = TokenDecodedDataUtil.decodedCompanyId(token);
            coupon.setCompanyId(companyId);
        }
        else {
            // mean you are admin
            coupon.setCompanyId(coupon.getCompanyId());
        }
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

    public Coupon getCouponData(Integer id) {
        CouponEntity couponEntity = couponsDal.findById(id).get();
        Coupon coupon = new Coupon(couponEntity);
        return coupon;
    }

    public void deleteCoupon(Integer id, String token) throws ApplicationException {
        System.out.println();
        validateUserPermissionToDelete(id, token);
        this.couponsDal.deleteById(id);
    }


    public void updateCoupon(Coupon coupon, String token) throws ApplicationException {
        SuccessfulLoginDetails userData = TokenDecodedDataUtil.decodedUserData(token);
        Coupon oldCoupon = getCouponData(coupon.getId());

        if (userData.getUserType() == UserType.CUSTOMER || userData.getUserType() == UserType.COMPANY && userData.getCompanyId() != coupon.getCompanyId()) {
            throw new ApplicationException(ErrorType.UNAUTHORIZED_TO_UPDATE_COUPON);
        } else if (userData.getUserType() == UserType.COMPANY) {
            coupon.setUserId((userData.getId()));
        } else {
            coupon.setUserId(oldCoupon.getUserId());
        }

        coupon.setCompanyId(oldCoupon.getCompanyId());
        coupon.setId(oldCoupon.getId());
//        coupon.setName(oldCoupon.getName());
//        coupon.setCategoryId(oldCoupon.getCategoryId());

//        Date now = new Date();
//        System.out.println();
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
        return getAllAvailableCoupons();
    }

    public List<Coupon> getAllCouponsAccordingUserType(String token) throws ApplicationException {
        SuccessfulLoginDetails successfulLoginDetails = TokenDecodedDataUtil.decodedUserData(token);
        UserType userType = successfulLoginDetails.getUserType();

        if (userType == UserType.ADMIN) {
            return this.couponsDal.getCoupons();
        } else if (userType == UserType.COMPANY) {
            int companyId = successfulLoginDetails.getCompanyId();
            return getCouponsByCompanyId(companyId);
        }
        return getAllAvailableCoupons();
    }

    public CouponsPagination getCouponsByFilters(String token, int page, int categoryIds, String searchString) throws ApplicationException {
        UserType userType = extractUserType(token);
//        searchString = searchString.toLowerCase();

        int couponsPerPage = 2;
//        page = 0;
//        int categoryIdss = 3;
        Pageable pageable = PageRequest.of(page-1, couponsPerPage);
        Page<Coupon> DBcouponsResponse;

        if (categoryIds == -1){
            DBcouponsResponse =  this.couponsDal.getAllCouponsWithPagination(pageable);

        }else {
            DBcouponsResponse = this.couponsDal.getByFilters(categoryIds, pageable);
        }
//        if (categoryIds.length == 0){
//            categoryIds = this.categoriesDal.getAllCategoryIds();
//        }

//        if (userType == UserType.ADMIN) {
//            return this.couponsDal.getCoupons();
//            List<Coupon>content = coupons.getContent();
//            System.out.println();
//            return coupons;
//        }

//        else if (userType == UserType.COMPANY) {
//            int companyId = successfulLoginDetails.getCompanyId();
//            return getCouponsByCompanyId(companyId);
//        }
//        return getAllAvailableCoupons();

        int pages = DBcouponsResponse.getTotalPages();
        List<Coupon> coupons = DBcouponsResponse.getContent();
        System.out.println();
        CouponsPagination couponsPagination = new CouponsPagination(coupons, pages);
        return couponsPagination;
    }

    private UserType extractUserType(String token) throws ApplicationException {
        if (token == null){
            return UserType.CUSTOMER;
        }else {
            SuccessfulLoginDetails successfulLoginDetails = TokenDecodedDataUtil.decodedUserData(token);
            UserType userType = successfulLoginDetails.getUserType();
            return userType;
        }
    }

    public List<Coupon> getAllAvailableCoupons() throws ApplicationException {
        return this.couponsDal.getAvailableCoupons(new Date());
    }

    public List<Coupon> getCouponsByMaxPrice(float maxPrice) throws ApplicationException {
        validateCouponPrice(maxPrice);
        return this.couponsDal.getCouponsByMaxPrice(maxPrice);
    }

    public List<Coupon> getCouponsByCompanyId(int companyId) throws ApplicationException {
        return this.couponsDal.getCouponsByCompanyId(companyId);
    }

    public List<Coupon> getCouponsByCategoryId(int categoryId) throws ApplicationException {
        return this.couponsDal.getCouponsByCategoryId(categoryId);
    }

    private void validateCouponData(Coupon coupon) throws ApplicationException {
        validateCouponName(coupon.getName());
        validateCouponDescription(coupon.getDescription());
        validateCouponPrice(coupon.getPrice());
        validateCouponAmount(coupon.getAmount());
        System.out.println();
        validateCouponDate(coupon.getStartDate(), coupon.getEndDate());
    }

    private void validateCouponDate(Date startDate, Date endDate) throws ApplicationException {
        System.out.println();
//        Date currentDate = new Date();
//        if (currentDate.after(startDate )) {
//            throw new ApplicationException(ErrorType.INVALID_COUPON_START_DATE, ErrorType.INVALID_COUPON_START_DATE.getErrorMessage());
//        }
//        if (currentDate.after(endDate)) {
//            throw new ApplicationException(ErrorType.INVALID_COUPON_END_DATE, ErrorType.INVALID_COUPON_END_DATE.getErrorMessage());
//        }
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
}
