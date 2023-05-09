package br.com.gerencia.portfolio.dto.response;

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
@JsonPropertyOrder({"id","nome", "dataNascimento", "cpf;", "funcionario"})
public class PessoaResponse {
    private Long id;
    private String nome;
    private String dataNascimento;
    private String cpf;
    private boolean funcionario;
}
