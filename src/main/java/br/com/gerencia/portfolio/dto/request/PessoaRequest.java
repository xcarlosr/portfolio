package br.com.gerencia.portfolio.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Carlos Roberto
 * @created 07/05/2023
 * @since 1.0
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaRequest {
    private Long id;
    private String nome;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
    private String cpf;
    private boolean funcionario;

}
