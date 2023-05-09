package br.com.gerencia.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
@Data
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(generator = "seqPessoa", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqPessoa", sequenceName = "seq_pessoa", allocationSize = 1)
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo")
    @Column(length = 100, nullable = false)
    private String nome;

    @Column(name = "datanascimento")
    private LocalDate dataNascimento;

    @CPF
    @Column(length = 14)
    private String cpf;

    @Column
    private boolean funcionario;
}
