package br.com.gerencia.portfolio.exception;

import br.com.gerencia.portfolio.dto.response.MembrosNaoSalvosResponse;
import lombok.Getter;

/**
 * @author Carlos Roberto
 * @created 10/05/2023
 * @since 1.0
 */
@Getter
public class MembrosNaosalvosException extends RuntimeException {

	private static final long serialVersionUID = -1877231711881766846L;
	
	private final MembrosNaoSalvosResponse membrosNaoSalvosResponse;
    
    public MembrosNaosalvosException(MembrosNaoSalvosResponse membrosNaoSalvosResponse){
        this.membrosNaoSalvosResponse = membrosNaoSalvosResponse;
    }

}
