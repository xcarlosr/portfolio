package br.com.gerencia.portfolio.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 10/05/2023
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MembrosNaoSalvosResponse implements Serializable {

	private static final long serialVersionUID = 6220860218812652997L;
	
	private String message;
    private List<String> membrosNaoVinculados;
}
