package br.com.gerencia.portfolio.validator;

import br.com.gerencia.portfolio.dto.request.PessoaRequest;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.exception.PessoaNotFoundException;
import br.com.gerencia.portfolio.exception.ProjetoNotFoundException;
import br.com.gerencia.portfolio.exception.RegraNegocioException;
import br.com.gerencia.portfolio.mappers.PessoaMapper;
import br.com.gerencia.portfolio.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class PessoaValidator {

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    private Pessoa pessoa;

    public PessoaValidator setPessoa(Pessoa pessoa) throws ProjetoNotFoundException {
        if(Objects.isNull(pessoa))
            throw new ProjetoNotFoundException("Pessoa não é valida");

        this.pessoa = pessoa;
        return this;
    }

    public PessoaValidator convertToPessoa(PessoaRequest pessoaRequest){
       this.pessoa = pessoaMapper.mapToPessoa(pessoaRequest);
        return this;
    }

    public PessoaValidator isFuncionario() throws RegraNegocioException {
        if(!pessoa.isFuncionario())
            throw new RegraNegocioException(String.format("%s não é um funcionário!", pessoa.getNome()));
        return this;
    }

    public Pessoa getPessoaById(Long idPessoa) {
        return pessoaRepository.findById(idPessoa).orElseThrow(() ->
                new PessoaNotFoundException(String.format("Pessoa não encontrada com o id: %d", idPessoa)));
    }
    
    public Pessoa getPessoa(){
        return pessoa;
    }
}
