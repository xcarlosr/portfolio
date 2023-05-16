package br.com.gerencia.portfolio.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Data
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class MembroPk implements Serializable {

	private static final long serialVersionUID = 7766073295119911081L;

	@Column(name = "idprojeto")
    private Long idProjeto;

    @Column(name = "idpessoa")
    private Long idPessoa;

}
