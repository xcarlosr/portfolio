package br.com.gerencia.portfolio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.test.context.ContextConfiguration;

import br.com.gerencia.portfolio.dto.request.MembroRequest;
import br.com.gerencia.portfolio.entity.Membro;
import br.com.gerencia.portfolio.entity.MembroPk;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.entity.Projeto;
import br.com.gerencia.portfolio.enums.ProjetoRiscoEnum;
import br.com.gerencia.portfolio.enums.ProjetoStatusEnum;
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

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {MembroService.class, MembroMapper.class})
class MemebroSeviceTest {

	private static final String MSG_ERROR_CONNECTION_TIME_OUT = "Connection time out";
	
	private static final String MSG_ERROR_PROJETO_NAO_ENCONTRADO_PARA_O_ID = "Projeto não encontrado para o id: %d";

	private static final String MSG_FORMATADA_MEMBRO_NAO_SALVO = "Pessoa: id = %d, nome = %s, funcionário = %b";

	private static final String MSG_ERROR_NAO_FOI_POSSIVEL_SALVAR_OS_MEMBROS = "Não foi possível salvar o(s) membro(s)";

	private static final String MSG_OPERACAO_REALIZADO_COM_SUCESSO = "Operação realizado com sucesso.";

	private static final String MSG_OPERACAO_REALIZADA_PARCIALMENTE_MEMBROS_NAO_SALVOS = "Operação realizada parcialmente, alguns membros não foram vinculados ao projeto.";

	@Mock
	private MembroRepository membroRepository;

	@Mock
	private MembroMapper membroMapper;

	@Mock
	private PessoaValidator pessoaValidator;

	@Mock
	private ProjetoValidator projetoValidator;

	@Mock
	private ProjetoRepository projetoRepository;

	@Mock
	private PessoaRepository pessoaRepository;

	@InjectMocks
	private MembroService membroService;
	
	private List<MembroRequest> listaMembroRequest = new ArrayList<>();
	
	private List<Membro> membros = new ArrayList<>();

	private List<MembroRequest> membrosRequest = new ArrayList<>();
	
	private Projeto projeto;
	
	private Long idprojeto;
	
	private Long idPessoaFuncionario;
	
	private Long idPessoaNaoFuncionario;

	private Pessoa pessoaFuncionario;
	
	private Pessoa pessoaNaoFuncionario;
	

	@BeforeEach
    void setUp() {
		
		projeto = Projeto.builder().id(1L)
								.nome("Projeto 1")
								.dataInicio(LocalDate.of(2023, 5, 15))
								.dataPrevisaoFim(LocalDate.of(2023, 6, 15))
								.descricao("Descricao Projeto 1")
								.gerente(pessoaFuncionario)
								.status(ProjetoStatusEnum.ANALIS_REALIZADA)
								.orcamento(BigDecimal.ONE)
								.risco(ProjetoRiscoEnum.BAIXO_RISCO)
								.build();
		
		idprojeto = projeto.getId();
		
		pessoaFuncionario = Pessoa.builder().id(22L).nome("Pessoa 22").cargo("Cargo pessoa 22 teste").funcionario(Boolean.TRUE).build();
		
		idPessoaFuncionario = pessoaFuncionario.getId();
		
		pessoaNaoFuncionario = Pessoa.builder().id(33L).nome("Pessoa 33").cargo("Cargo pessoa 33 teste").funcionario(Boolean.FALSE).build();
		
		idPessoaNaoFuncionario = pessoaNaoFuncionario.getId();
		
		listaMembroRequest = Arrays.asList(
							MembroRequest.builder().idPessoa(pessoaFuncionario.getId()).build(),
							MembroRequest.builder().idPessoa(pessoaNaoFuncionario.getId()).build());
		
		membros = Arrays.asList(
				Membro.builder().id(MembroPk.builder()
										.idPessoa(idprojeto)
										.idProjeto(pessoaFuncionario
										.getId()).build() ).build(),
				Membro.builder().id(MembroPk.builder()
										.idPessoa(idprojeto)
										.idProjeto(pessoaNaoFuncionario
										.getId()).build() ).build());
		
	}

	@Test
	@DisplayName("Deve vincular membros a um projeto com sucesso.")
	void teste01() throws PessoaNotFoundException, RegraNegocioException,
								ProjetoNotFoundException, MembrosNaoSalvosException {
		
		when(projetoRepository.existsById(anyLong())).thenReturn(true);

		String result = membroService.vincularMembrosProjeto(idprojeto, listaMembroRequest);

		assertEquals(MSG_OPERACAO_REALIZADO_COM_SUCESSO, result);
		
	}
	
	@Test
	@DisplayName("Deve lançar ProjetoNotFoundException ao vincular membros a um projeto inexistente.")
	void teste02() throws PessoaNotFoundException, RegraNegocioException, MembrosNaoSalvosException {
	    
	    when(projetoRepository.existsById(any())).thenReturn(false);

	    assertThrows(
	    		ProjetoNotFoundException.class, 
	    		() -> membroService.vincularMembrosProjeto(idprojeto, membrosRequest),
	            String.format(MSG_ERROR_PROJETO_NAO_ENCONTRADO_PARA_O_ID, idprojeto)
	    );
	}
	
	@Test
	@DisplayName("Deve lançar MembrosNaosalvosException ao tentar vincular membros não funcionários.")
	void teste03() throws PessoaNotFoundException, RegraNegocioException, ProjetoNotFoundException {

		membrosRequest.add(MembroRequest.builder().idPessoa(idPessoaFuncionario).build());
		membrosRequest.add(MembroRequest.builder().idPessoa(idPessoaNaoFuncionario).build());
		
		
		when(projetoRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);
	    when(membroMapper.mapToListMembro(anyLong(), any())).thenReturn(membros);
	    when(pessoaValidator.getPessoaById(anyLong())).thenReturn(pessoaNaoFuncionario);
	    
	    MembrosNaoSalvosException exception = assertThrows(
	            MembrosNaoSalvosException.class,
	            () -> membroService.vincularMembrosProjeto(idPessoaNaoFuncionario, membrosRequest)
	    );
	    
	    assertEquals(MSG_OPERACAO_REALIZADA_PARCIALMENTE_MEMBROS_NAO_SALVOS, exception.getMembrosNaoSalvosResponse().getMessage());
	    assertEquals(2, exception.getMembrosNaoSalvosResponse().getMembrosNaoVinculados().size());
	    assertEquals(
	            String.format(MSG_FORMATADA_MEMBRO_NAO_SALVO,
	                    idPessoaNaoFuncionario, pessoaNaoFuncionario.getNome(), pessoaNaoFuncionario.isFuncionario()),
	            exception.getMembrosNaoSalvosResponse().getMembrosNaoVinculados().get(0)
	    );
	}
	
	@Test
	@DisplayName("Deve lançar MembroErrorException ao ocorrer um erro inesperado.")
	void teste04() throws PessoaNotFoundException, RegraNegocioException, ProjetoNotFoundException {
		List<MembroRequest> membrosRequest = new ArrayList<>();
		membrosRequest.add(MembroRequest.builder().idPessoa(idPessoaFuncionario).build());
		membrosRequest.add(MembroRequest.builder().idPessoa(idPessoaNaoFuncionario).build());
		
		when(projetoRepository.existsById(anyLong())).thenThrow(new NoResultException());
	    
	   assertThrows(
			   MembroErrorException.class,
	            () -> membroService.vincularMembrosProjeto(idPessoaFuncionario, membrosRequest),  
        		MSG_ERROR_NAO_FOI_POSSIVEL_SALVAR_OS_MEMBROS);
	}
	
	@Test
	@DisplayName("Deve lançar MembroErrorException ao vincular membros a um projeto.")
	void teste05() throws PessoaNotFoundException, RegraNegocioException, ProjetoNotFoundException {
		List<MembroRequest> membrosRequest = new ArrayList<>();
		membrosRequest.add(MembroRequest.builder().idPessoa(idPessoaFuncionario).build());
		membrosRequest.add(MembroRequest.builder().idPessoa(idPessoaNaoFuncionario).build());
		
		when(projetoRepository.existsById(anyLong())).thenThrow(new CannotGetJdbcConnectionException(MSG_ERROR_CONNECTION_TIME_OUT));
	    
	   assertThrows(
			   MembroErrorException.class,
	            () -> membroService.vincularMembrosProjeto(idPessoaFuncionario, membrosRequest),  
	            MSG_ERROR_CONNECTION_TIME_OUT);
	}

}
