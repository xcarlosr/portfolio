package br.com.gerencia.portfolio.mappers;

import br.com.gerencia.portfolio.dto.request.ProjetoRequest;
import br.com.gerencia.portfolio.dto.response.ProjetoResponse;
import br.com.gerencia.portfolio.entity.Projeto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;

/**
 * @author Carlos Roberto
 * @created 07/05/2023
 * @since 1.0
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class, MapperBaseImpl.class})
public interface ProjetoMapper {

    @Mapping(source = "risco", target = "risco")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "idGerente", target = "gerente.id")
    Projeto mapToProejto(ProjetoRequest projetoRequest);

    @Mapping(source = "risco", target = "risco")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "dataInicio", target = "dataInicio", conditionExpression = "java(projeto.getDataInicio() != null)", qualifiedByName = "localDateToString")
    @Mapping(source = "dataPrevisaoFim", target = "dataPrevisaoFim", conditionExpression = "java(projeto.getDataPrevisaoFim() != null)", qualifiedByName = "localDateToString")
    @Mapping(source = "dataFim", target = "dataFim", conditionExpression = "java(projeto.getDataFim() != null)", qualifiedByName = "localDateToString")
    @Mapping(source = "orcamento", target = "orcamento")
    ProjetoResponse mapToProjetoResponse(Projeto projeto);

    List<ProjetoResponse> mapToListProjetoResponses(List<Projeto> projetos);

    default Projeto mapProjetoToUpdate(ProjetoRequest projetoRequest, Projeto projetoUpdate) {

        if (Objects.nonNull(projetoRequest.getNome())) {
            projetoUpdate.setNome(projetoRequest.getNome());
        }

        if (Objects.nonNull(projetoRequest.getDataInicio())) {
            projetoUpdate.setDataInicio(projetoRequest.getDataInicio());
        }

        if (Objects.nonNull(projetoRequest.getDataPrevisaoFim())) {
            projetoUpdate.setDataPrevisaoFim(projetoRequest.getDataPrevisaoFim());
        }

        if (Objects.nonNull(projetoRequest.getDataFim())) {
            projetoUpdate.setDataFim(projetoRequest.getDataFim());
        }

        if (Objects.nonNull(projetoRequest.getDescricao())) {
            projetoUpdate.setDescricao(projetoRequest.getDescricao());
        }

        if (Objects.nonNull(projetoRequest.getStatus())) {
            projetoUpdate.setStatus(projetoRequest.getStatus());
        }

        if (Objects.nonNull(projetoRequest.getOrcamento())) {
            projetoUpdate.setOrcamento(projetoRequest.getOrcamento());
        }

        if (Objects.nonNull(projetoRequest.getRisco())) {
            projetoUpdate.setRisco(projetoRequest.getRisco());
        }

        if (Objects.nonNull(projetoRequest.getIdGerente())) {
            projetoUpdate.getGerente().setId(projetoRequest.getIdGerente());
        }
        return projetoUpdate;
    }
}
