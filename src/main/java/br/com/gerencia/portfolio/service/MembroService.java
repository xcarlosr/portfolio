package br.com.gerencia.portfolio.service;

import br.com.gerencia.portfolio.dto.request.MembroRequest;
import br.com.gerencia.portfolio.dto.response.MembroResponse;
import br.com.gerencia.portfolio.dto.response.MembrosNaoSalvosResponse;
import br.com.gerencia.portfolio.entity.Membro;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.exception.*;
import br.com.gerencia.portfolio.mappers.MembroMapper;
import br.com.gerencia.portfolio.repository.MembroRepository;
import br.com.gerencia.portfolio.repository.PessoaRepository;
import br.com.gerencia.portfolio.repository.ProjetoRepository;
import br.com.gerencia.portfolio.validator.PessoaValidator;
import br.com.gerencia.portfolio.validator.ProjetoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MembroService {

    private final MembroRepository membroRepository;

    private final MembroMapper membroMapper;

    private final PessoaValidator pessoaValidator;

    private final ProjetoValidator projetoValidator;

    private final ProjetoRepository projetoRepository;

    private final PessoaRepository pessoaRepository;

    @Transactional(propagation = Propagation.REQUIRED, timeout = 10)
    public String vincularMembrosProjeto(Long idProjeto, List<MembroRequest> listMembros) throws PessoaNotFoundException,
            RegraNegocioException, ProjetoNotFoundException, MembrosNaosalvosException {
        List<String> listMembrosNaoSalvos = new ArrayList<>();
        try {

            if(!projetoRepository.existsById(idProjeto))
                throw new ProjetoNotFoundException(String.format("Projeto não encontrado para o id: %d", idProjeto));

            List<Membro> membroList = membroMapper.mapToListMembro(idProjeto, listMembros);
            membroList.forEach(membro -> {
                Pessoa pessoa = pessoaValidator.getPessoaById(membro.getId().getIdPessoa());
                if(pessoa.isFuncionario()){
                    membroRepository.save(membro);
                } else {
                    listMembrosNaoSalvos.add(String.format("Pessoa: id = %d, nome = %s, funcionário = %b",
                            pessoa.getId(), pessoa.getNome(), pessoa.isFuncionario()));
                }
            });

            if(!listMembrosNaoSalvos.isEmpty())
                throw new MembrosNaosalvosException(MembrosNaoSalvosResponse.builder()
                        .message("Operação realizada parcialmente, alguns membros não foram vinculados ao projeto.")
                        .membrosNaoVinculados(listMembrosNaoSalvos).build());

            return "Operação realizado com sucsseo.";

        } catch (ProjetoNotFoundException | PessoaNotFoundException | RegraNegocioException | MembrosNaosalvosException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new MembroErrorException("Não foi possível salvar o(s) membro(s)", ex);
        }
    }

    public MembroResponse listarMembros(Long idProjeto) {
        return membroMapper.mapToListMembroResponse(
                projetoValidator.getProjetoById(idProjeto),
                pessoaRepository.findAllByIdPessoas(idProjeto));
    }
}
