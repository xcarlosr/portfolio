package br.com.gerencia.portfolio.repository;

import br.com.gerencia.portfolio.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Carlos Roberto
 * @created 08/05/2023
 * @since 1.0
 */
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query(value = "SELECT p.* FROM pessoa p INNER JOIN membros m ON p.id = m.idpessoa WHERE m.idprojeto = :idProjeto",
            nativeQuery = true)
    List<Pessoa> findAllByIdPessoas(@Param("idProjeto") Long idProjeto);
}
