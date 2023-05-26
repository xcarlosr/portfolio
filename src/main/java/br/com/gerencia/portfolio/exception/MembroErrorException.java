package br.com.gerencia.portfolio.exception;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
public class MembroErrorException extends RuntimeException {

	private static final long serialVersionUID = -1930097484646472961L;

	public MembroErrorException(String message){
        super(message);
    }

    public MembroErrorException(String message, Exception ex){
        super(message, ex);
    }
}
