package com.aviad.coupons.exceptions;

import com.aviad.coupons.dto.ErrorBean;
import com.aviad.coupons.enums.ErrorType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;


// Exception handler class
@RestControllerAdvice
public class ExceptionsHandler {

	//	Response - Object in Spring
	@ExceptionHandler
	@ResponseBody
	// Variable name is throwable in order to remember that it handles Exception and Error
	public ErrorBean toResponse(Throwable throwable, HttpServletResponse httpServletResponse) {
		if(throwable instanceof ApplicationException) {
			ApplicationException appException = (ApplicationException) throwable;

			if(appException.getErrorType().isShowStackTrace()) {
				appException.printStackTrace();
			}

			ErrorType errorType = appException.getErrorType();
			int errorNumber = errorType.getErrorNumber();
			String errorMessage = errorType.getErrorMessage();
			String errorName = errorType.getErrorName();
			ErrorBean errorBean = new ErrorBean(errorNumber, errorMessage, errorName);
			httpServletResponse.setStatus(errorNumber);
			return errorBean;
		}

		throwable.printStackTrace();

		ErrorBean errorBean = new ErrorBean(601,"General error", "General error");
//		httpServletResponse.setStatus(601);
		return errorBean;
	}

}


