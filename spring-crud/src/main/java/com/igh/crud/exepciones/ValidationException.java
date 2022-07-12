package com.igh.crud.exepciones;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ValidationException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
 
        
        Map<String,Object> errors = new HashMap<String, Object>();
        
        ex.getBindingResult().getAllErrors().forEach(error->{
        	String fieldName = ((FieldError)error).getField();
        	String errorMessage = error.getDefaultMessage();
        	errors.put(fieldName, errorMessage);
        });
        
        return errors;
    }

   
	
}
