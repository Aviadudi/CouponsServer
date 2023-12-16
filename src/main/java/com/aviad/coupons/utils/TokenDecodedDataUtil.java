package com.aviad.coupons.utils;

import com.aviad.coupons.beans.SuccessfulLoginDetails;
import com.aviad.coupons.enums.UserType;
import com.aviad.coupons.exceptions.ApplicationException;

public class TokenDecodedDataUtil {
    public static SuccessfulLoginDetails decodedUserData(String token) throws ApplicationException {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        return successfulLoginDetails;
    }
    public static int decodedUserId(String token) throws ApplicationException {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        int userId = successfulLoginDetails.getId();
        return userId;
    }

    public static int decodedCompanyId(String token) throws ApplicationException {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        int companyId = successfulLoginDetails.getCompanyId();
        return companyId;
    }

    public static UserType decodedUserType(String token) throws ApplicationException {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        return userType;
    }
}
