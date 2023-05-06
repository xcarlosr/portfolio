package br.com.gerencia.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo")
    @Column(length = 200, nullable = false)
    private String nome;

    @Column
    private LocalDate dataInicio;

    @Column
    private LocalDate dataPrevisaoFim;

    @Column
    private LocalDate dataFim;

    @Column(length = 5000)
    private String descricao;

    @Column(length = 45)
    private String status;

    @Column
    private Float orcamento;

    @Column(length = 45)
    private String risco;

    @ManyToOne
    @JoinColumn(name = "idgerente")
    private Pessoa gerente;

}
