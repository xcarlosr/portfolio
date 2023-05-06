package br.com.gerencia.portfolio.repository;

import br.com.gerencia.portfolio.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Projeto, Long> {
}
