package com.exception;


import com.util.json.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

/**
 * Custom ExceptionMapper to return an appropriate response to the client when
 * a http client error occurred.
 *
 */
@Order(400)
@ControllerAdvice
@Slf4j
public class RestAPIExceptionMapper {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<JsonResponse> toResponse(final HttpServletRequest request,
            final HttpClientErrorException exception) {

        // get request url and remote address
        String url = null;
        String remoteAddress = "unknown";
        if (request != null) {
            url = request.getRequestURL() != null ? request.getRequestURL().toString() : null;
            remoteAddress = request.getRemoteAddr();
        }

        final HttpStatus status = exception.getStatusCode();
        log.warn("Client request can not be processed ({}) from {} to {}. Business failure: {}", status,
                remoteAddress, url, exception.getMessage());

        final JsonResponse jsonResponse = new JsonResponse()
                .with("message", exception.getMessage())
                .with("status", status.value() + " (" + status.getReasonPhrase() + ")")
                .done();

        return ResponseEntity.status(status.value()).body(jsonResponse);
    }
}
