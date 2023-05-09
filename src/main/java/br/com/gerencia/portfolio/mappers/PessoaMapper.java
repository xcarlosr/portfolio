package br.com.gerencia.portfolio.mappers;

import br.com.gerencia.portfolio.dto.request.PessoaRequest;
import br.com.gerencia.portfolio.dto.response.PessoaResponse;
import br.com.gerencia.portfolio.entity.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Carlos Roberto
 * @created 07/05/2023
 * @since 1.0
 */
@Mapper(componentModel = "spring", uses = MapperBaseImpl.class)
public interface PessoaMapper {

    Pessoa mapToPessoa(PessoaRequest pessoaRequest);

    @Mapping(source = "dataNascimento", target = "dataNascimento", qualifiedByName = "localDateToString")
    @Mapping(source = "cpf", target = "cpf", qualifiedByName = "ofuscarCPF")
    PessoaResponse mapToPessoaResponse(Pessoa pessoa);

}
