package com.aviad.coupons.exceptions;


import com.aviad.coupons.enums.ErrorType;

public class ApplicationException extends Exception {
    private ErrorType errorType;

    public ApplicationException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ApplicationException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    // Ctor used when wrapping another exception to a 3rd party
    public ApplicationException(ErrorType errorType, String message, Exception innerException){
        super(message,innerException);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
