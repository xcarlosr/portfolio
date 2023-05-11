package br.com.gerencia.portfolio.service;

import br.com.gerencia.portfolio.dto.request.PessoaRequest;
import br.com.gerencia.portfolio.dto.response.PessoaResponse;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.exception.PessoaErrorException;
import br.com.gerencia.portfolio.exception.PessoaNotFoundException;
import br.com.gerencia.portfolio.mappers.PessoaMapper;
import br.com.gerencia.portfolio.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    @Transactional(propagation = Propagation.REQUIRED, timeout = 10)
    public void cadastrarPessoa(PessoaRequest pessoaRequest) {
        try {
            pessoaRepository.save(pessoaMapper.mapToPessoa(pessoaRequest));

        } catch (Exception ex) {
            throw new PessoaErrorException("Não foi possível salvar a pessoa.", ex);
        }
    }

    public PessoaResponse consultarPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(() ->
                new PessoaNotFoundException(String.format("Pessoa não encontrada com o id: %d", id)));

        return pessoaMapper.mapToPessoaResponse(pessoa);
    }

    public Page<PessoaResponse> listarPessoa(Pageable pageable) {
        Page<Pessoa> pessoaPage = pessoaRepository.findAll(pageable);
        List<PessoaResponse> pessoaResponses = pessoaMapper.mapToListProjetoResponses(pessoaPage.getContent());
        return new PageImpl<>(pessoaResponses, pageable, pessoaPage.getTotalElements());
    }
}
