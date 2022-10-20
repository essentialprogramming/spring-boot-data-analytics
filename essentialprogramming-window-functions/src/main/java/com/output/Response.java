package com.output;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    public static <T> ResponseEntity<T> created(T body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);
    }
}
