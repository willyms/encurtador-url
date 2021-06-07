package com.desafio.encurtadorurl.exception;

import com.desafio.encurtadorurl.util.*;

public class ExpirationDateException extends RuntimeException {
    public ExpirationDateException() {
        super(UrlUtils.DATE_EXPIRED);
    }
}
