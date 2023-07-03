package com.fellaverse.backend.handler;

import com.fellaverse.backend.code.ReturnCode;
import com.fellaverse.backend.exception.ConsumerException;
import com.fellaverse.backend.pojo.ResultData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ManagementExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResultData<Object> exception(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException SQL exception: " + e.getMessage());
        log.error("Exception type: " + e.getClass().getName());
        return ResultData.fail(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResultData<Object> exception(DataAccessException e) {
        log.error("DataAccessException SQL exception: " + e.getMessage());
        log.error("Exception type: " + e.getClass().getName());
        return ResultData.fail(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<Object> exception(HttpMessageNotReadableException e) {
        log.error("Bad request exception: " + e.getMessage());
        log.error("Exception type: " + e.getClass().getName());
        return ResultData.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<Object> exception(Exception e) {
        log.error("Global exception: " + e.getMessage());
        log.error("Exception type: " + e.getClass().getName());
        return ResultData.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<String> handleValidatedException(Exception e) {
        ResultData<String> resp = null;

        if (e instanceof MethodArgumentNotValidException ex) {
            // BeanValidation exception
            resp = ResultData.fail(HttpStatus.BAD_REQUEST.value(),
                    ex.getBindingResult().getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof ConstraintViolationException ex) {
            // BeanValidation GET simple param
            resp = ResultData.fail(HttpStatus.BAD_REQUEST.value(),
                    ex.getConstraintViolations().stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof BindException ex) {
            // BeanValidation GET object param
            resp = ResultData.fail(HttpStatus.BAD_REQUEST.value(),
                    ex.getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        }

        return resp;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(IllegalArgumentException e) {
        return ResultData.fail(ReturnCode.ILLEGAL_ARGUMENT.getCode(),e.getMessage());
    }

    @ExceptionHandler(ConsumerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<String> consumerException(ConsumerException e) {
        log.error("Consumer exception: " + e.getMessage());
        log.error("Exception type: " + e.getClass().getName());
        return ResultData.fail(e.getCode(),e.getMessage());
    }
}
