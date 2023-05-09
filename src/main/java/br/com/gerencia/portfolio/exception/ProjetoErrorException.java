package br.com.gerencia.portfolio.exception;

/**
 * @author Carlos Roberto
 * @created 08/05/2023
 * @since 1.0
 */
public class ProjetoErrorException extends RuntimeException {

    public ProjetoErrorException(String message){
        super(message);
    }

    public ProjetoErrorException(String message, Exception ex){
        super(message, ex);
    }
}
