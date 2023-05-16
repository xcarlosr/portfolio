package br.com.gerencia.portfolio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.gerencia.portfolio.dto.request.PessoaRequest;
import br.com.gerencia.portfolio.dto.response.PessoaResponse;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.exception.PessoaErrorException;
import br.com.gerencia.portfolio.exception.PessoaNotFoundException;
import br.com.gerencia.portfolio.mappers.PessoaMapper;
import br.com.gerencia.portfolio.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PessoaService {

    private static final String MSG_ERROR_PESSOA_NAO_ENCONTRADA_COM_O_ID = "Pessoa não encontrada com o id: %d";

	private static final String MSG_ERROR_NAO_FOI_POSSIVEL_SALVAR_A_PESSOA = "Não foi possível salvar a pessoa.";

	private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    @Transactional(propagation = Propagation.REQUIRED, timeout = 10)
    public PessoaResponse cadastrarPessoa(PessoaRequest pessoaRequest)throws PessoaErrorException {
        try {
        
        	Pessoa pessoa = pessoaMapper.mapToPessoa(pessoaRequest);
        	return pessoaMapper.mapToPessoaResponse(pessoaRepository.save(pessoa));
        
        } catch (Exception ex) {
            throw new PessoaErrorException(MSG_ERROR_NAO_FOI_POSSIVEL_SALVAR_A_PESSOA, ex);
        }
    }

    public PessoaResponse consultarPessoa(Long id) throws PessoaNotFoundException {
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(() ->
                new PessoaNotFoundException(String.format(MSG_ERROR_PESSOA_NAO_ENCONTRADA_COM_O_ID, id)));

        return pessoaMapper.mapToPessoaResponse(pessoa);
    }

    public Page<PessoaResponse> listarPaginado(Pageable pageable) {
        return pessoaRepository.findAll(pageable).map(pessoaMapper::mapToPessoaResponse);
    }

	public List<PessoaResponse> listar() {
		List<Pessoa> pessoas = pessoaRepository.findAllPessoas();
		return pessoas.stream()
				.map(pessoaMapper::mapToPessoaResponse)
				.collect(Collectors.toList());
	}
}
