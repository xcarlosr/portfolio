package br.com.gerencia.portfolio.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

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
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pessoa")
public class Pessoa implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4566220545982984745L;

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

    @Column(length = 100)
    private String cargo;
    
    @Column
    private boolean funcionario;
}
