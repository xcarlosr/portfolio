package br.com.gerencia.portfolio.exception;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
public class ProjetoNotFoundException extends RuntimeException {

    public ProjetoNotFoundException(String message){
        super(message);
    }
    public ProjetoNotFoundException(String message, Exception ex){
        super(message, ex);
    }
}
