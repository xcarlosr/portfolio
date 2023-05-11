package br.com.gerencia.portfolio.exception;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
public class PessoaErrorException extends RuntimeException {

    public PessoaErrorException(String message){
        super(message);
    }

    public PessoaErrorException(String message, Exception ex){
        super(message, ex);
    }
}
