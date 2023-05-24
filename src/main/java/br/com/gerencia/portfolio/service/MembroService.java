package br.com.gerencia.portfolio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.gerencia.portfolio.dto.request.MembroRequest;
import br.com.gerencia.portfolio.dto.response.MembroResponse;
import br.com.gerencia.portfolio.dto.response.MembrosNaoSalvosResponse;
import br.com.gerencia.portfolio.entity.Membro;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.exception.MembroErrorException;
import br.com.gerencia.portfolio.exception.MembrosNaoSalvosException;
import br.com.gerencia.portfolio.exception.PessoaNotFoundException;
import br.com.gerencia.portfolio.exception.ProjetoNotFoundException;
import br.com.gerencia.portfolio.exception.RegraNegocioException;
import br.com.gerencia.portfolio.mappers.MembroMapper;
import br.com.gerencia.portfolio.repository.MembroRepository;
import br.com.gerencia.portfolio.repository.PessoaRepository;
import br.com.gerencia.portfolio.repository.ProjetoRepository;
import br.com.gerencia.portfolio.validator.PessoaValidator;
import br.com.gerencia.portfolio.validator.ProjetoValidator;
import lombok.RequiredArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MembroService {

	private static final String MSG_ERROR_PROJETO_NAO_ENCONTRADO_PARA_O_ID = "Projeto não encontrado para o id: %d";

	private static final String MSG_FORMATADA_MEMBRO_NAO_SALVO = "Pessoa: id = %d, nome = %s, funcionário = %b";

	private static final String MSG_ERROR_NAO_FOI_POSSIVEL_SALVAR_OS_MEMBROS = "Não foi possível salvar o(s) membro(s)";

	private static final String MSG_OPERACAO_REALIZADO_COM_SUCESSO = "Operação realizado com sucesso.";

	private static final String MSG_OPERACAO_REALIZADA_PARCIALMENTE_MEMBROS_NAO_SALVOS = "Operação realizada parcialmente, alguns membros não foram vinculados ao projeto.";

	private final MembroRepository membroRepository;

	private final MembroMapper membroMapper;

	private final PessoaValidator pessoaValidator;

	private final ProjetoValidator projetoValidator;

	private final ProjetoRepository projetoRepository;

	private final PessoaRepository pessoaRepository;

	@Transactional(propagation = Propagation.REQUIRED, timeout = 10)
	public String vincularMembrosProjeto(Long idProjeto, List<MembroRequest> listMembroRequest)
			throws PessoaNotFoundException, RegraNegocioException, ProjetoNotFoundException, MembrosNaoSalvosException {
		List<String> listaMembrosNaoSalvos = new ArrayList<>();
		try {

			if (!projetoRepository.existsById(idProjeto))
				throw new ProjetoNotFoundException(String.format(MSG_ERROR_PROJETO_NAO_ENCONTRADO_PARA_O_ID, idProjeto));

			List<Membro> membroList = membroMapper.mapToListMembro(idProjeto, listMembroRequest);

			membroList.forEach(membro -> {
				Pessoa pessoa = pessoaValidator.getPessoaById(membro.getId().getIdPessoa());
				if (pessoa.isFuncionario()) {
					membroRepository.save(membro);
				} else {
					listaMembrosNaoSalvos.add(String.format(MSG_FORMATADA_MEMBRO_NAO_SALVO,
							pessoa.getId(), pessoa.getNome(), pessoa.isFuncionario()));
				}
			});

			if (!listaMembrosNaoSalvos.isEmpty())
				throw new MembrosNaoSalvosException(MembrosNaoSalvosResponse.builder()
						.message(MSG_OPERACAO_REALIZADA_PARCIALMENTE_MEMBROS_NAO_SALVOS)
						.membrosNaoVinculados(listaMembrosNaoSalvos).build());

			return MSG_OPERACAO_REALIZADO_COM_SUCESSO;

		} catch (ProjetoNotFoundException | PessoaNotFoundException | RegraNegocioException
                 | MembrosNaoSalvosException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new MembroErrorException(MSG_ERROR_NAO_FOI_POSSIVEL_SALVAR_OS_MEMBROS, ex);
		}
	}

	public MembroResponse listarMembros(Long idProjeto) {
		return membroMapper.mapToListMembroResponse(projetoValidator.getProjetoById(idProjeto),
				pessoaRepository.findAllByIdProjeto(idProjeto));
	}
}
