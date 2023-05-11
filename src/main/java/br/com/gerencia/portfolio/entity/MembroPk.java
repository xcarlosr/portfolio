package br.com.gerencia.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class MembroPk implements Serializable {

    @Column(name = "idprojeto")
    private Long idProjeto;

    @Column(name = "idpessoa")
    private Long idPessoa;

}
