package com.desafio.encurtadorurl.exception;

import com.desafio.encurtadorurl.util.*;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super(UrlUtils.NO_RECORDS_FOUND);
    }
}
