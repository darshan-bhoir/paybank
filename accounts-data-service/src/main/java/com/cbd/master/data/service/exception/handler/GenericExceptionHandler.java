package com.cbd.master.data.service.exception.handler;

import com.cbd.master.data.service.exception.BadRequestException;
import com.cbd.master.data.service.exception.EntityNotFoundException;
import com.cbd.master.data.service.exception.MasterDataServiceTechnicalException;
import com.cbd.master.data.service.model.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String ERROR_MESSAGE = "Unable to process your request due to an Internal Issue.";

	@ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorDTO("D001",ERROR_MESSAGE), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
	
    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<Object> handleConflict(BadRequestException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorDTO(ex.getErrorCode(),ex.getErrorMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<Object> handleConflict(EntityNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorDTO(ex.getErrorCode(),ex.getErrorMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    @ExceptionHandler(value = { MasterDataServiceTechnicalException.class })
    protected ResponseEntity<Object> handleConflict(MasterDataServiceTechnicalException ex, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorDTO("D001",ERROR_MESSAGE), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
