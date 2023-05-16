package br.com.gerencia.portfolio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.gerencia.portfolio.dto.request.ProjetoRequest;
import br.com.gerencia.portfolio.dto.response.ProjetoResponse;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.entity.Projeto;
import br.com.gerencia.portfolio.exception.PessoaNotFoundException;
import br.com.gerencia.portfolio.exception.ProjetoErrorException;
import br.com.gerencia.portfolio.exception.ProjetoNotFoundException;
import br.com.gerencia.portfolio.exception.RegraNegocioException;
import br.com.gerencia.portfolio.mappers.ProjetoMapper;
import br.com.gerencia.portfolio.repository.PessoaRepository;
import br.com.gerencia.portfolio.repository.ProjetoRepository;
import br.com.gerencia.portfolio.validator.ProjetoValidator;
import lombok.RequiredArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 05/05/2023
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProjetoService {
	
    private static final String PESSOA_NAO_ENCONTRATO_COM_O_ID = "Pessoa não encontrato com o id: %d";

	private static final String MSG_PROJETO_NAO_ENCONTRATO_COM_O_ID = "Projeto não encontrato com o id: %d";

	private static final String MSG_RROR_AO_TENTAR_EXCLUIR_O_PROJETO_COM_O_ID = "rror ao tentar excluir o projeto com o id: %d";

	private static final String MSG_ERROR_AO_TENTAR_ATUALIZAR_O_PROJETO_COM_O_ID = "Error ao tentar atualizar o projeto com o id: %d";

	private static final String MSG_ERROR_AO_TENTAR_CONSULTAR_O_PROJETO_COM_O_ID = "Error ao tentar consultar o projeto com o id: %d";

	private static final String MSG_ERROR_AO_TENTAR_LISTAR_OS_PROJETOS = "Error ao tentar listar os projetos";

	private static final String MSG_ERROR_AO_TENTAR_SALVAR_O_PROJETO = "Error ao tentar salvar o projeto.";
    
	private final PessoaRepository pessoaRepository;
    private final ProjetoRepository projetoRepository;
    private final ProjetoMapper projetoMapper;
    private final ProjetoValidator projetoValidator;

    @Transactional(propagation = Propagation.REQUIRED, timeout = 10)
    public Page<ProjetoResponse> cadastrarProjeto(ProjetoRequest projetoRequest) throws RegraNegocioException, ProjetoErrorException, PessoaNotFoundException {

        try {
            Projeto projeto = projetoValidator.convertToProjeto(projetoRequest).isFuncionario().getProjeto();
            projetoRepository.save(projeto);
            return getCreatePage(projeto);

        } catch (RegraNegocioException | PessoaNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ProjetoErrorException(MSG_ERROR_AO_TENTAR_SALVAR_O_PROJETO, ex);
        }
    }

    public Page<ProjetoResponse> listarProjetos(Pageable pageable) throws ProjetoErrorException {
        try{
            Page<Projeto> projetoPage = projetoRepository.findAllPessoas(pageable);
            List<ProjetoResponse> projetoResponses = projetoMapper.mapToListProjetoResponses(projetoPage.getContent());
            return new PageImpl<>(projetoResponses, pageable, projetoPage.getTotalElements());

        } catch (Exception ex) {
            throw new ProjetoErrorException(MSG_ERROR_AO_TENTAR_LISTAR_OS_PROJETOS, ex);
        }
    }

    public Page<ProjetoResponse> consultarProjeto(Long id) throws ProjetoNotFoundException, ProjetoErrorException {
        try{
            Projeto projeto = findByProjetoId(id);

            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
            List<ProjetoResponse> projetoResponses = projetoMapper.mapToListProjetoResponses(List.of(projeto));
            return new PageImpl<>(projetoResponses, pageable, 1);

        } catch (ProjetoNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ProjetoErrorException(String.format(MSG_ERROR_AO_TENTAR_CONSULTAR_O_PROJETO_COM_O_ID, id), ex);
        }
    }

    public Page<ProjetoResponse> atualizarProjeto(Long id, ProjetoRequest projetoRequest) throws ProjetoNotFoundException, ProjetoErrorException,
            PessoaNotFoundException, RegraNegocioException {
        
    	try {
    		
            Projeto projetoUpdate = findByProjetoId(id);
            Pessoa pessoa = findByPessoaId(projetoRequest.getIdGerente());
            projetoUpdate.setGerente(pessoa);

            projetoValidator.setProjeto(projetoUpdate).isFuncionario();

            projetoRepository.save(projetoMapper.mapProjetoToUpdate(projetoRequest, projetoUpdate));

            return getCreatePage(projetoUpdate);

        } catch (ProjetoNotFoundException | PessoaNotFoundException | RegraNegocioException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ProjetoErrorException(String.format(MSG_ERROR_AO_TENTAR_ATUALIZAR_O_PROJETO_COM_O_ID, id), ex);
        }
    }

    public Page<ProjetoResponse> excluirProjeto(Long id, Pageable pageable) throws RegraNegocioException, ProjetoErrorException, PessoaNotFoundException  {

        try{
            Projeto projeto = projetoValidator.setProjeto(findByProjetoId(id)).isFuncionario().isPossivelDeletar().getProjeto();
            projetoRepository.delete(projeto);
            Page<Projeto> projetoPage = projetoRepository.findAll(pageable);
            List<ProjetoResponse> projetoResponses = projetoMapper.mapToListProjetoResponses(projetoPage.getContent());
            return new PageImpl<>(projetoResponses, pageable, projetoPage.getTotalElements());

        } catch (RegraNegocioException | PessoaNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ProjetoErrorException(String.format(MSG_RROR_AO_TENTAR_EXCLUIR_O_PROJETO_COM_O_ID, id), ex);
        }
    }

    private Page<ProjetoResponse> getCreatePage(Projeto projeto) {
        try{
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id"));
            return new PageImpl<>(List.of(projetoMapper.mapToProjetoResponse(projeto)), pageable, 1);

        } catch (Exception ex) {
            throw new ProjetoErrorException("Erro ao listar os projeto", ex);
        }
    }

    private Projeto findByProjetoId(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new ProjetoNotFoundException(String.format(MSG_PROJETO_NAO_ENCONTRATO_COM_O_ID, id)));
    }

    private Pessoa findByPessoaId(Long id) {
        return  pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNotFoundException(String.format(PESSOA_NAO_ENCONTRATO_COM_O_ID, id)));
    }

	public Page<ProjetoResponse> pesquisarProjetos(String valorParam, Pageable pageable) {

		if (valorParam.matches("-?\\d+(\\.\\d+)?")) {
			Optional<Projeto> projetoOptional = projetoRepository.findById(Long.parseLong(valorParam));
			if (projetoOptional.isPresent()) {
				ProjetoResponse projetoResponse = projetoMapper.mapToProjetoResponse(projetoOptional.get());
				return new PageImpl<>(List.of(projetoResponse), pageable, 1);
			}
		}

		Page<Projeto> projetoPage = projetoRepository.pesquisarProjeto(valorParam, pageable);
		if (projetoPage.getTotalElements() > 0) {
			List<ProjetoResponse> listProjetoResponses = projetoMapper
					.mapToListProjetoResponses(projetoPage.getContent());
			return new PageImpl<>(listProjetoResponses, pageable, projetoPage.getTotalElements());
		}

		return new PageImpl<>(List.of(), pageable, 0L);
	}

}
