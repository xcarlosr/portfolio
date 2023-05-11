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

    private final MembrosNaoSalvosResponse membrosNaoSalvosResponse;
    public MembrosNaosalvosException(MembrosNaoSalvosResponse membrosNaoSalvosResponse){
        this.membrosNaoSalvosResponse = membrosNaoSalvosResponse;
    }

}
