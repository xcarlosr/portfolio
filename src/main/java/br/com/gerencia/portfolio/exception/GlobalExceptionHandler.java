package br.com.gerencia.portfolio.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String EXCEPTION_LOG_MSG = "e=%s,m=%s";

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> projetoResponseStatusException(final ResponseStatusException ex) {
        logError(ex);
        return new ResponseEntity<String>(ex.getReason(), HttpStatusCode.valueOf(ex.getStatusCode().value()));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> processAPIException(final ResponseStatusException ex) {
        logError(ex);
        return new ResponseEntity<String>(ex.getReason(), HttpStatusCode.valueOf(ex.getStatusCode().value()));
    }

    private void logError(final Exception e) {
        final String message = String.format(EXCEPTION_LOG_MSG, e.getClass().getSimpleName(), e.getMessage());
        log.error(message, e);
    }
}
