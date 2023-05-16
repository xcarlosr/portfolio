package br.com.gerencia.portfolio.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.gerencia.portfolio.dto.request.MembroRequest;
import br.com.gerencia.portfolio.dto.response.MembroResponse;
import br.com.gerencia.portfolio.dto.response.PessoaResponse;
import br.com.gerencia.portfolio.dto.response.ProjetoResponse;
import br.com.gerencia.portfolio.entity.Membro;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.entity.Projeto;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Mapper(componentModel = "spring", uses = MapperBaseImpl.class)
public interface MembroMapper {

    @Mapping(target = "id", expression = "java(new br.com.gerencia.portfolio.entity.MembroPk(idProjeto, membroRequest.getIdPessoa()))")
    Membro mapToMembro(Long idProjeto, MembroRequest membroRequest);

    default List<Membro> mapToListMembro(Long idProjeto, List<MembroRequest> listMembros) {
        return listMembros.stream()
                .map(membroRequest -> mapToMembro(idProjeto, membroRequest))
                .collect(Collectors.toList());
    }

    default MembroResponse mapToListMembroResponse(Projeto projeto, List<Pessoa> listPessoas) {
        return MembroResponse.builder()
                .projeto(mapToProjetoResponse(projeto))
                .pessoas(listPessoaToPessoaResponseByMembroResponse(listPessoas))
                .build();
    }

    default PessoaResponse mapToPessoaResponse(Pessoa pessoa){
        return PessoaResponse.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .funcionario(pessoa.isFuncionario())
                .build();
    }

    default ProjetoResponse mapToProjetoResponse(Projeto projeto){
        return ProjetoResponse.builder()
                .id(projeto.getId())
                .nome(projeto.getNome())
                .status(projeto.getStatus().name())
                .risco(projeto.getRisco().name())
                .build();
    }

    default List<PessoaResponse> listPessoaToPessoaResponseByMembroResponse(List<Pessoa> listPessoas){
        return listPessoas.stream()
                .map(pessoa -> PessoaResponse.builder()
                        .id(pessoa.getId())
                        .nome(pessoa.getNome())
                        .funcionario(pessoa.isFuncionario()).build())
                .collect(Collectors.toList());
    }
}
