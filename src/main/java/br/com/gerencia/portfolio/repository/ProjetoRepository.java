package br.com.gerencia.portfolio.repository;

import br.com.gerencia.portfolio.entity.Projeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @EntityGraph(attributePaths = "gerente")
    @Query(value = "SELECT p FROM Projeto p JOIN p.gerente ps")
    Page<Projeto> findAllByIdPessoa(Pageable pageable);
}
