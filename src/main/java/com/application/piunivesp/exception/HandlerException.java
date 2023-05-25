package com.application.piunivesp.exception;

import com.application.piunivesp.exception.type.BusinessException;
import com.application.piunivesp.exception.type.InternalException;
import com.application.piunivesp.exception.type.NotFoundException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity handleBusinessException(BusinessException businessException, WebRequest webRequest) {
        return handleExceptionInternal(businessException, businessException.getMessage(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, webRequest);
    }

    @ExceptionHandler(InternalException.class)
    protected ResponseEntity handleInternalException(InternalException internalException, WebRequest webRequest) {
        return handleExceptionInternal(internalException, internalException.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity handleNotFoundException(NotFoundException notFoundException, WebRequest webRequest) {
        return handleExceptionInternal(notFoundException, notFoundException.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(TokenExpiredException.class)
    protected ResponseEntity handleTokenExpiredException(TokenExpiredException tokenExpiredException, WebRequest webRequest) {
        return handleExceptionInternal(tokenExpiredException, tokenExpiredException.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, webRequest);
    }
}
