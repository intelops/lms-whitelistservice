package com.spring.exception;

public class WishListItemNotExistException extends IllegalArgumentException {
    public WishListItemNotExistException(String msg) {
        super(msg);
    }
}
