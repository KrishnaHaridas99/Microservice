package com.eazybytes.loans.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue){
        super(String.format("%s not found for the field %s : %s" ,resourceName ,fieldName ,fieldValue));
    }
}
