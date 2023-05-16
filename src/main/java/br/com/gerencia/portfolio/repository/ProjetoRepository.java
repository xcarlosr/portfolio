package br.com.gerencia.portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gerencia.portfolio.entity.Projeto;

/**
 * @author Carlos Roberto
 * @created 06/05/2023
 * @since 1.0
 */
@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @EntityGraph(attributePaths = "gerente")
    @Query(value = "SELECT p FROM Projeto p JOIN p.gerente g where g.funcionario = true order by p.id")
    Page<Projeto> findAllPessoas(Pageable pageable);
    
    @EntityGraph(attributePaths = "gerente")
    @Query(value = "SELECT p FROM Projeto p JOIN p.gerente g WHERE (p.nome LIKE %:valorParam% OR p.descricao LIKE %:valorParam%) AND g.funcionario = true order by p.id")
    Page<Projeto> pesquisarProjeto(@Param("valorParam") String valorParam, Pageable pageable);
 
}
