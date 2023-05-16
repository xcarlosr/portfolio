package br.com.gerencia.portfolio.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import br.com.gerencia.portfolio.enums.ProjetoRiscoEnum;
import br.com.gerencia.portfolio.enums.ProjetoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projeto")
public class Projeto implements Serializable {
	
	
	private static final long serialVersionUID = -7991445623474326882L;

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
    private ProjetoStatusEnum status;

    @Column
    private BigDecimal orcamento;

    @Enumerated(EnumType.STRING)
    @Column(name ="risco", length = 45)
    private ProjetoRiscoEnum risco;

    @ManyToOne
    @JoinColumn(name = "idgerente")
    private Pessoa gerente;

    public boolean isFuncionario() {
        return Objects.nonNull(gerente.getNome()) && gerente.isFuncionario();
    }

    public boolean isPossivelDeletar(){
        if(ProjetoStatusEnum.INICIADO.equals(status) || ProjetoStatusEnum.EM_ANDAMENTO.equals(status) ||
           ProjetoStatusEnum.ENCERRADO.equals(status)) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

}
