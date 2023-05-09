package br.com.gerencia.portfolio.dto.response;

import br.com.gerencia.portfolio.entity.Pessoa;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 07/05/2023
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id","nome", "dataInicio", "dataPrevisaoFim", "dataFim",
        "descricao", "status", "orcamento", "risco", "gerente"})
public class ProjetoResponse {

    private Long id;
    private String nome;
    private String dataInicio;
    private String dataPrevisaoFim;
    private String dataFim;
    private String descricao;
    private String status;
    private String orcamento;
    private String risco;
    private PessoaResponse gerente;
}
