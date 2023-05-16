package br.com.gerencia.portfolio.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"projeto","pessoas"})
public class MembroResponse {
    
	
	private ProjetoResponse projeto;
    private List<PessoaResponse> pessoas;

}
