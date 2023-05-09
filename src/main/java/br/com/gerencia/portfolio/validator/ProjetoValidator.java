package br.com.gerencia.portfolio.validator;

import br.com.gerencia.portfolio.dto.request.ProjetoRequest;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.entity.Projeto;
import br.com.gerencia.portfolio.enums.ProjetoStatusEnum;
import br.com.gerencia.portfolio.exception.PessoaNotFoundException;
import br.com.gerencia.portfolio.exception.ProjetoNotFoundException;
import br.com.gerencia.portfolio.exception.RegraNegocioException;
import br.com.gerencia.portfolio.mappers.ProjetoMapper;
import br.com.gerencia.portfolio.repository.PessoaRepository;
import lombok.Builder;
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
    private final PessoaRepository pessoaRepository;
    private Projeto projeto;

    public ProjetoValidator setProjeto(Projeto projeto) throws ProjetoNotFoundException {
        if(Objects.isNull(projeto))
            throw new ProjetoNotFoundException("Pessoa não é valida");

        this.projeto = projeto;
        return this;
    }

    public ProjetoValidator convertToProjeto(ProjetoRequest request) throws PessoaNotFoundException {
        this.projeto = projetoMapper.mapToProejto(request);
        Pessoa pessoa = pessoaRepository.findById(projeto.getGerente().getId()).orElseThrow(() ->
                        new PessoaNotFoundException(String.format("Pessoa não encontrada com o id: %d",
                                this.projeto.getGerente().getId())));
        this.projeto.setGerente(pessoa);
        return this;
    }

    public ProjetoValidator isFuncionario() throws RegraNegocioException {
        if(!projeto.getGerente().isFuncionario())
            throw new RegraNegocioException("Não foi possível criar um novo projeto. O Gerente não é um funcionário!");
        return this;
    }

    public ProjetoValidator isPossivelDeletar() throws RegraNegocioException {
        if(!projeto.isPossivelDeletar())
            throw new RegraNegocioException(String.format("O Projeto não pode ser excluido nos status: %s, %s, %s",
                    ProjetoStatusEnum.INICIADO.name(), ProjetoStatusEnum.EM_ANDAMENTO.name(), ProjetoStatusEnum.ENCERRADO.name()));
        return this;
    }

    public Projeto getProjeto(){
        return this.projeto;
    }
}
