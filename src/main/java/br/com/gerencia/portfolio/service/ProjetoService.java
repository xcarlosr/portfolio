package br.com.gerencia.portfolio.service;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author Carlos Roberto
 * @created 05/05/2023
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjetoService {
    private final PessoaRepository pessoaRepository;
    private final ProjetoRepository projetoRepository;
    private final ProjetoMapper projetoMapper;
    private final ProjetoValidator projetoValidator;

    @Transactional(propagation = Propagation.REQUIRED, timeout = 10)
    public Page<ProjetoResponse> cadastrarProjeto(ProjetoRequest projetoRequest) throws RegraNegocioException, ProjetoErrorException, PessoaNotFoundException {

        try {
            Projeto projeto = projetoValidator.convertToProjeto(projetoRequest).isFuncionario().getProjeto();
            projetoRepository.save(projeto);
            return createPageComProjeto(projeto);

        } catch (RegraNegocioException | PessoaNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ProjetoErrorException("Ocorreu um error ao tentar salvar o projeto.", ex);
        }
    }

    public Page<ProjetoResponse> listarProjetos(Pageable pageable) throws ProjetoErrorException {
        try{
            Page<Projeto> projetoPage = projetoRepository.findAllByIdPessoa(pageable);
            List<ProjetoResponse> projetoResponses = projetoMapper.mapToListProjetoResponses(projetoPage.getContent());
            return new PageImpl<>(projetoResponses, pageable, projetoPage.getTotalElements());

        } catch (Exception ex) {
            throw new ProjetoErrorException("Ocorreu um error ao tentar listar os projetos", ex);
        }
    }

    public Page<ProjetoResponse> consultarProjeto(Long id) throws ProjetoNotFoundException, ProjetoErrorException {
        try{
            Projeto projeto = findByProjetoId(id);

            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
            List<ProjetoResponse> projetoResponses = projetoMapper.mapToListProjetoResponses(Collections.singletonList(projeto));
            return new PageImpl<>(projetoResponses, pageable, 1);

        } catch (ProjetoNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ProjetoErrorException(String.format("Ocorreu um error ao tentar consultar o projeto com o id: %d", id), ex);
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

            return createPageComProjeto(projetoUpdate);

        } catch (ProjetoNotFoundException | PessoaNotFoundException | RegraNegocioException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ProjetoErrorException(String.format("Ocorreu um error ao tentar atualizar o projeto com o id: %d", id), ex);
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
            throw new ProjetoErrorException(String.format("Ocorreu um error ao tentar excluir o projeto com o id: %d", id), ex);
        }
    }

    private Page<ProjetoResponse> createPageComProjeto(Projeto projeto) {
        try{
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
            return new PageImpl<>(List.of(projetoMapper.mapToResponse(projeto)), pageable, 1);

        } catch (Exception ex) {
            throw new ProjetoErrorException("Erro ao listar os projeto", ex);
        }
    }

    private Projeto findByProjetoId(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new ProjetoNotFoundException(String.format("Projeto não encontrato com o id: %d", id)));
    }

    private Pessoa findByPessoaId(Long id) {
        return  pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNotFoundException(String.format("Pessoa não encontrato com o id: %d", id)));
    }

}
