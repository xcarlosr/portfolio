package br.com.gerencia.portfolio.repository;

import br.com.gerencia.portfolio.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Carlos Roberto
 * @created 08/05/2023
 * @since 1.0
 */
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
