package com.petlearning.restapi.exception;

/**
 * 資源找不到異常
 * 當查詢的資源不存在時拋出此異常
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
