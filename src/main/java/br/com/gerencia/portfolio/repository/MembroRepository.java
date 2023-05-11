package br.com.gerencia.portfolio.repository;

import br.com.gerencia.portfolio.entity.Membro;
import br.com.gerencia.portfolio.entity.MembroPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Repository
public interface MembroRepository extends JpaRepository<Membro, MembroPk> {
}
