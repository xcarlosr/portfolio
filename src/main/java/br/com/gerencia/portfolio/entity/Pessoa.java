package br.com.gerencia.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
@Data
@Entity
@Table(name = "pessoal")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo")
    @Column(length = 100, nullable = false)
    private String nome;

    @Column
    private LocalDate dataNascimento;

    @CPF
    @Column(length = 14)
    private String cpf;

    @Column
    private boolean funcionario;
}
