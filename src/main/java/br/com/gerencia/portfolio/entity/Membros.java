package br.com.gerencia.portfolio.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
@Data
@Entity
@Table(name = "membros")
public class Membros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idprojeto")
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "idpessoa")
    private Pessoa pessoa;

}
