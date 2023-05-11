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
public class Membro {

    @EmbeddedId
    private MembroPk id;

}
