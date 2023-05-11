package br.com.gerencia.portfolio.dto.request;

import br.com.gerencia.portfolio.enums.ProjetoRiscoEnum;
import br.com.gerencia.portfolio.enums.ProjetoStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class ProjetoRequest {
    private Long id;
    private String nome;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataInicio;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataPrevisaoFim;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataFim;
    private String descricao;
    private ProjetoStatusEnum status;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "###,###,##0.00")
    private BigDecimal orcamento;
    private ProjetoRiscoEnum risco;
    private Long idGerente;
}
