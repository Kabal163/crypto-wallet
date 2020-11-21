package com.github.kabal163.web.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kabal163.balance.FlushWaitingException;
import com.github.kabal163.web.response.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(value = FlushWaitingException.class)
    protected ResponseEntity<Object> handleFlushWaitingException(Exception ex, WebRequest request) {
        WebResponse<Void> response = WebResponse.failed(
                "Impossible to update balance! Please, contact us to report a problem.",
                ex.getClass().getCanonicalName());
        log.error("Error while waiting buffered local balance flushing!", ex);
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        WebResponse<Void> response = WebResponse.failed(
                ex.getMessage(),
                ex.getClass().getCanonicalName());
        log.error("Unknown runtime exception!", ex);
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        WebResponse<Void> response = WebResponse.failed(
                "The request contains not readable data format",
                ex.getClass().getCanonicalName());
        log.trace("Bad Request", ex);
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.trace("Bad Request", ex);
        return handleExceptionInternal(ex, buildErrorMessage(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private WebResponse<List<Map<String, String>>> buildErrorMessage(MethodArgumentNotValidException ex) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        String rawData = "[" + String.join(", ", errorList) + "]";
        try {
            List<Map<String, String>> data = objectMapper.readValue(rawData, new TypeReference<>() {});
            return WebResponse.failed("Invalid argument", ex.getClass().getCanonicalName(), data);
        } catch (JsonProcessingException e) {
            log.error("Error while preparing message for bad request! problem rawData: {}", rawData, e);
            return WebResponse.failed("Invalid argument", ex.getClass().getCanonicalName(), null);
        }
    }
}
