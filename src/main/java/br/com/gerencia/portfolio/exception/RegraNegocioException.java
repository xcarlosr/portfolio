package br.com.gerencia.portfolio.exception;

/**
 * @author Carlos Roberto
 * @created 08/05/2023
 * @since 1.0
 */
public class RegraNegocioException extends RuntimeException {

	private static final long serialVersionUID = 2191767992044140054L;

	public RegraNegocioException(String message){
        super(message);
    }

    public RegraNegocioException(String message, Exception ex){
        super(message, ex);
    }
}
