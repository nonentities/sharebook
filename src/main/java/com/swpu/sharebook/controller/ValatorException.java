package com.swpu.sharebook.controller;

import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValatorException {
    @ExceptionHandler(value = BindException.class)
    public ResponseResult bindExceptionErrorHandler(BindException ex) throws Exception {
        BindingResult bindingResult=ex.getBindingResult();
        List<ObjectError> errors =bindingResult.getAllErrors();
        List<String> errorMessages=new ArrayList<>();
        for(int i=0;i<errors.size();i++){
            errorMessages.add(errors.get(i).getDefaultMessage());
        }
        return ResponseResult.ERROR(400,errorMessages.toString());
    }
    }

