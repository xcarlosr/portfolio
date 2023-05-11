package br.com.gerencia.portfolio.validator;

import br.com.gerencia.portfolio.dto.request.ProjetoRequest;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.entity.Projeto;
import br.com.gerencia.portfolio.enums.ProjetoStatusEnum;
import br.com.gerencia.portfolio.exception.PessoaNotFoundException;
import br.com.gerencia.portfolio.exception.ProjetoNotFoundException;
import br.com.gerencia.portfolio.exception.RegraNegocioException;
import br.com.gerencia.portfolio.mappers.ProjetoMapper;
import br.com.gerencia.portfolio.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Carlos Roberto
 * @created 08/05/2023
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ProjetoValidator {

    private final ProjetoMapper projetoMapper;

    private final PessoaValidator pessoaValidator;


    private final ProjetoRepository projetoRepository;

    private Projeto projeto;

    public ProjetoValidator setProjeto(Projeto projeto) throws ProjetoNotFoundException {
        if(Objects.isNull(projeto))
            throw new ProjetoNotFoundException("Pessoa não é valida");

        this.projeto = projeto;
        return this;
    }

    public ProjetoValidator convertToProjeto(ProjetoRequest request) throws PessoaNotFoundException {
        this.projeto = projetoMapper.mapToProejto(request);
        Pessoa pessoa = getPessoa(projeto.getGerente().getId());
        this.projeto.setGerente(pessoa);
        return this;
    }

    public ProjetoValidator isFuncionario() throws PessoaNotFoundException, RegraNegocioException {
        if(!getPessoa(projeto.getGerente().getId()).isFuncionario())
            throw new RegraNegocioException("Não foi possível criar ou atualizar o projeto. O Gerente não é um funcionário!");
        return this;
    }

    private Pessoa getPessoa(Long idPessoa) {
       return pessoaValidator.getPessoaById(idPessoa);
    }

    public ProjetoValidator isPossivelDeletar() throws RegraNegocioException {
        if(!projeto.isPossivelDeletar())
            throw new RegraNegocioException(String.format("O Projeto não pode ser excluido nós status: %s, %s, %s",
                    ProjetoStatusEnum.INICIADO.name(), ProjetoStatusEnum.EM_ANDAMENTO.name(), ProjetoStatusEnum.ENCERRADO.name()));
        return this;
    }

    public Projeto getProjetoById(Long idProjeto){
        return projetoRepository.findById(idProjeto).orElseThrow(() ->
                new ProjetoNotFoundException(String.format("Projeto não encontrada para o id: %d", idProjeto)));
    }

    public Projeto getProjeto(){
        return this.projeto;
    }
}
