package br.com.gerencia.portfolio.service;

import br.com.gerencia.portfolio.entity.Projeto;
import br.com.gerencia.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author Carlos Roberto
 * @created 05/05/2023
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public Projeto cadastrarProjeto(Projeto projeto) {
        return portfolioRepository.save(projeto);
    }

    public Page<Projeto> listarProjetos(Pageable pageable) {
        return portfolioRepository.findAll(pageable);
    }

    public Projeto consultarProjeto(Long id) {
        if(!isExistsProjetoById(id)){
            throwResponseStatusExceptionNOT_FOUND(id);
        }
        return portfolioRepository.findById(id).get();
    }

    public Projeto atualizarProjeto(Long id, Projeto projeto) {
        return null;
    }

    public String excluirProjeto(Long id) {
        if(!isExistsProjetoById(id)){
            throwResponseStatusExceptionNOT_FOUND(id);
        }

        Projeto projetoDB = portfolioRepository.findById(id).get();
        portfolioRepository.delete(projetoDB);
        return "Projeto excluido com sucesso!";
    }

    private boolean isExistsProjetoById(Long id) {
        return portfolioRepository.existsById(id);
    }

    private static void throwResponseStatusExceptionNOT_FOUND(Long id) {
        throw new ResponseStatusException(NOT_FOUND, String.format("Não foi encontrada nenhuma projeto com o id: %d", id));
    }

}
