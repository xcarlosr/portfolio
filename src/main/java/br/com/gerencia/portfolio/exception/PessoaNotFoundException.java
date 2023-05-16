package br.com.gerencia.portfolio.exception;

/**
 * @author Carlos Roberto
 * @created 08/05/2023
 * @since 1.0
 */
public class PessoaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 47319387467607437L;

	public PessoaNotFoundException (String message){
        super(message);
    }

    public PessoaNotFoundException (String message, Exception ex){
        super(message, ex);
    }
}
