package br.com.gerencia.portfolio.entity;

import br.com.gerencia.portfolio.enums.ProjetoRiscoEnum;
import br.com.gerencia.portfolio.enums.ProjetoStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
@Data
@Entity
@Table(name = "projeto")
public class Projeto {
    @Id
    @GeneratedValue(generator = "seqProjeto", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqProjeto", sequenceName = "seq_projeto", allocationSize = 1)
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo")
    @Column(length = 200, nullable = false)
    private String nome;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_previsao_fim")
    private LocalDate dataPrevisaoFim;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(length = 5000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name ="status" ,length = 45)
    private ProjetoStatusEnum statusEnum;

    @Column
    private BigDecimal orcamento;

    @Enumerated(EnumType.STRING)
    @Column(name ="risco", length = 45)
    private ProjetoRiscoEnum riscoEnum;

    @ManyToOne
    @JoinColumn(name = "idgerente")
    private Pessoa gerente;

    public boolean isFuncionario() {
        return Objects.nonNull(gerente.getNome()) && gerente.isFuncionario();
    }

    public boolean isPossivelDeletar(){
        if(ProjetoStatusEnum.INICIADO.equals(statusEnum) || ProjetoStatusEnum.EM_ANDAMENTO.equals(statusEnum) ||
           ProjetoStatusEnum.ENCERRADO.equals(statusEnum)) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

}
