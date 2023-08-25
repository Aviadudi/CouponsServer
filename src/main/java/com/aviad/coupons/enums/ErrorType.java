package com.aviad.coupons.enums;

public enum ErrorType {
    GENERAL_ERROR(601, "GENERAL ERROR","General error", true),
    USERNAME_TOO_SHORT(602, "USERNAME TOO SHORT","user name is too short", false),
    USERNAME_TOO_LONG(603,"USERNAME TOO LONG", "user name is too long", false),
    INVALID_PASSWORD(604,"INVALID PASSWORD", "invalid password", false),
    INVALID_COMPANY_ID(605,"INVALID COMPANY ID", "invalid company id, please use numbers only", false),
    COUPON_NAME_TOO_SHORT(606,"COUPON NAME TOO SHORT", "coupon name is too short", false),
    COUPON_NAME_TOO_LONG(607,"COUPON NAME TOO LONG", "coupon name is too long", false),
    COUPON_DESCRIPTION_TOO_SHORT(608,"COUPON DESCRIPTION TOO SHORT", "coupon description is too short", false),
    COUPON_DESCRIPTION_TOO_LONG(609,"COUPON DESCRIPTION TOO LONG", "coupon description is too long", false),
    COUPON_PRICE_TOO_LOW(610,"COUPON PRICE TOO LOW","coupon price is too low", false),
    COUPON_PRICE_TOO_HIGH(611,"COUPON PRICE TOO HIGH", "coupon price is too high", false),
    USER_PASSWORD_TOO_SHORT(612,"USER PASSWORD TOO SHORT", "User password is too short", false),
    USER_PASSWORD_TOO_LONG(613,"USER PASSWORD TOO LONG", "User password is too long", false),
    COMPANY_NAME_TOO_SHORT(614,"COMPANY NAME TOO SHORT", "company name is too short", false),
    COMPANY_NAME_TOO_LONG(615,"COMPANY NAME TOO LONG", "company name is too long", false),
    EMAIL_NOT_PROVIDED(616,"EMAIL NOT PROVIDED", "Email not provided", false),
    INVALID_EMAIL(617,"INVALID EMAIL", "Invalid email address", false),
    COMPANY_DO_NOT_EXIST(618,"COMPANY DO NOT EXIST", "company don't exist", false),
    CATEGORY_DO_NOT_EXIST(619,"CATEGORY DO NOT EXIST","category don't exist", false),
    CATEGORY_NAME_TOO_SHORT(620,"CATEGORY NAME TOO SHORT", "category name is too short", false),
    CATEGORY_NAME_TOO_LONG(621,"CATEGORY NAME TOO LONG", "category name is too long", false),
    COMPANY_ADDRESS_TOO_SHORT(622,"COMPANY ADDRESS TOO SHORT", "company address is too short", false),
    COMPANY_ADDRESS_TOO_LONG(623,"COMPANY ADDRESS TOO LONG", "company address is too long", false),
    PHONE_NUMBER_IS_INCORRECT(624,"PHONE NUMBER IS INCORRECT","phone number is incorrect", false),
    INVALID_COUPON_AMOUNT(625,"INVALID COUPON AMOUNT","Invalid amount of coupons", false),
    INVALID_COUPON_START_DATE(626,"INVALID COUPON START DATE","Invalid start date",false),
    INVALID_COUPON_END_DATE(626,"INVALID COUPON END DATE","Invalid end date",false),
    INVALID_COUPON_DATE_ORDER(627,"INVALID COUPON DATE ORDER", "Start date can't be later than end date", false),
    INVALID_PURCHASE_ID(628,"INVALID PURCHASE ID", "purchase id can't be 0 or negative number", false),
    INVALID_PURCHASE_DATE(629,"INVALID PURCHASE DATE", "purchase can't be in the future", false),
    INVALID_PURCHASE_AMOUNT(630,"INVALID PURCHASE AMOUNT", "Invalid purchase amount, can't be less than 1", false),
    INVALID_USER_ID(631,"INVALID USER ID", "Invalid user id, have to be positive number",false),
    INVALID_COUPON_ID(632,"INVALID COUPON_ID", "Invalid coupon id, have to be positive number",false),
    FAILED_LOGIN(633,"FAILED LOGIN", "Failed login", false),
    INVALID_COUPON_AMOUNT_TO_PURCHASE(634,"INVALID COUPON AMOUNT TO PURCHASE", "Not enough coupons left to purchase", false),
    UNAUTHORIZED_TO_ADD_COUPON(635,"UNAUTHORIZED TO ADD COUPON", "Customers not allowed to add coupons", false),
    UNAUTHORIZED_TO_UPDATE_COUPON(635,"UNAUTHORIZED TO UPDATE COUPON", "Customers not allowed to update coupons", false),
    UNAUTHORIZED_TO_DELETE_COUPON(635,"UNAUTHORIZED TO DELETE COUPON", "You are not allowed to delete this coupon", false);








    private int errorNumber;
    private String errorMessage;
    private String errorName;
    private boolean isShowStackTrace;

    ErrorType (int errorNumber,String errorName, String errorMessage, boolean isShowStackTrace){
        this.errorNumber = errorNumber;
        this.errorName = errorName;
        this.errorMessage = errorMessage;
        this.isShowStackTrace = isShowStackTrace;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public String getErrorName() {
        return errorName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isShowStackTrace() {
        return isShowStackTrace;
    }

}
