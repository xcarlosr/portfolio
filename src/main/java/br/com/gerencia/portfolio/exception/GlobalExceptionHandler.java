package br.com.gerencia.portfolio.exception;

import br.com.gerencia.portfolio.dto.response.MembrosNaoSalvosResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String LOG_MSG = "%s - %s";

    @ExceptionHandler(ProjetoNotFoundException.class)
    public ResponseEntity<ErrorResponse> projetoNotFoundException(final ProjetoNotFoundException ex) {
        geraLogError(ex);
        return new ResponseEntity<>(getErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PessoaNotFoundException.class)
    public ResponseEntity<ErrorResponse> pessoaNotFoundException(final PessoaNotFoundException ex) {
        geraLogError(ex);
        return new ResponseEntity<>(getErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErrorResponse> projetoRegraNegocioExceptin(final RegraNegocioException ex){
        geraLogError(ex);
        return new ResponseEntity<>(getErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProjetoErrorException.class)
    public ResponseEntity<ErrorResponse> projetoRegraNegocioExceptin(final ProjetoErrorException ex){
        geraLogError(ex);
        return new ResponseEntity<>(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MembroErrorException.class)
    public ResponseEntity<ErrorResponse> membroErrorException(MembroErrorException ex) {
        geraLogError(ex);
        return new ResponseEntity<>(getErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponse(HttpStatus httpStatus, String message) {
        return new ErrorResponse(String.format(LOG_MSG, httpStatus.value(), httpStatus.name()), message);
    }

    private void geraLogError(final Exception e) {
        final String message = String.format(LOG_MSG, e.getClass().getSimpleName(), e.getMessage());
        log.error(message, e);
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ToString
    private static class ErrorResponse {
        private String status;
        private String message;

        public ErrorResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}

