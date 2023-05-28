package br.com.gerencia.portfolio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import br.com.gerencia.portfolio.dto.request.ProjetoRequest;
import br.com.gerencia.portfolio.dto.response.PessoaResponse;
import br.com.gerencia.portfolio.dto.response.ProjetoResponse;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.entity.Projeto;
import br.com.gerencia.portfolio.enums.ProjetoRiscoEnum;
import br.com.gerencia.portfolio.enums.ProjetoStatusEnum;
import br.com.gerencia.portfolio.exception.PessoaNotFoundException;
import br.com.gerencia.portfolio.exception.RegraNegocioException;
import br.com.gerencia.portfolio.mappers.ProjetoMapper;
import br.com.gerencia.portfolio.mappers.ProjetoMapperImpl;
import br.com.gerencia.portfolio.repository.PessoaRepository;
import br.com.gerencia.portfolio.repository.ProjetoRepository;
import br.com.gerencia.portfolio.validator.PessoaValidator;
import br.com.gerencia.portfolio.validator.ProjetoValidator;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ProjetoService.class, ProjetoMapperImpl.class, ProjetoValidator.class, PessoaValidator.class})
class PeojetoServiceTest {
	
	private static final String MSG_ERRO_PESSOA_NAO_ENCONTRADA_COM_O_ID = "Pessoa não encontrada com o id: %d";

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ProjetoRepository projetoRepository;
    
    @Mock
    private ProjetoValidator projetoValidator;

    @Mock
    private ProjetoMapper projetoMapper;
    
    @Mock
    private PessoaValidator pessoaValidator;
    
    @InjectMocks
    private ProjetoService projetoService;

    private Projeto projeto;
    
    private ProjetoRequest projetoRequest;
    
    private ProjetoResponse projetoResponse;
    
    private PessoaResponse pessoaResponse;

    private ProjetoValidator projetoValidatorTest;
    
    private ProjetoResponse projetoResponseExpected;
    
    private Pessoa pessoaFuncionario;

    @BeforeEach
    void setUp() {
        projetoRequest = ProjetoRequest.builder()
        		.nome("Projeto 1")
				.dataInicio(LocalDate.of(2023, 5, 15))
				.dataPrevisaoFim(LocalDate.of(2023, 6, 15))
				.descricao("Descricao Projeto 1")
				.status(ProjetoStatusEnum.ANALIS_REALIZADA)
				.orcamento(BigDecimal.ONE)
				.risco(ProjetoRiscoEnum.BAIXO_RISCO)
				.idGerente(1L)
				.build();
        
        pessoaResponse = PessoaResponse.builder()
		        		.id(22L).nome("Pessoa 22")
		        		.cargo("Cargo pessoa 22 teste")
		        		.funcionario(Boolean.TRUE).build();
        
        projetoResponse = ProjetoResponse.builder()
        		.id(1L)
        		.nome("Projeto 1")
				.dataInicio("15/05/2023")
				.dataPrevisaoFim("15/06/2023")
				.descricao("Descricao Projeto 1")
				.status(ProjetoStatusEnum.ANALIS_REALIZADA.name())
				.orcamento(BigDecimal.ONE.toString())
				.risco(ProjetoRiscoEnum.BAIXO_RISCO.name())
				.gerente(pessoaResponse)
				.build();
        
        projetoResponseExpected = ProjetoResponse.builder()
        		.id(1L)
        		.nome("Projeto 1")
				.dataInicio("15/05/2023")
				.dataPrevisaoFim("15/06/2023")
				.descricao("Descricao Projeto 1")
				.status(ProjetoStatusEnum.ANALIS_REALIZADA.name())
				.orcamento(BigDecimal.ONE.toString())
				.risco(ProjetoRiscoEnum.BAIXO_RISCO.name())
				.gerente(pessoaResponse)
				.build();

        pessoaFuncionario = Pessoa.builder()
        		.id(22L)
        		.nome("Pessoa 22")
        		.cargo("Cargo pessoa 22 teste")
        		.funcionario(Boolean.TRUE).build();
        
        projeto = Projeto.builder()
        		.id(1L)
				.nome("Projeto 1")
				.dataInicio(LocalDate.of(2023, 5, 15))
				.dataPrevisaoFim(LocalDate.of(2023, 6, 15))
				.descricao("Descricao Projeto 1")
				.status(ProjetoStatusEnum.ANALIS_REALIZADA)
				.orcamento(BigDecimal.ONE)
				.risco(ProjetoRiscoEnum.BAIXO_RISCO)
				.gerente(pessoaFuncionario)
				.build();

        projetoValidatorTest = new ProjetoValidator(projetoMapper, pessoaValidator, projetoRepository);
        projetoValidatorTest.setProjeto(projeto);
        
    }
    
    @Test
    @DisplayName("Deve cadastrar um novo projeto")
    void caso1(){
    	
    	when(projetoValidator.convertToProjeto(any())).thenReturn(projetoValidatorTest);
    	when(projetoMapper.mapToProjetoResponse(any())).thenReturn(projetoResponse);
    	when(pessoaValidator.getPessoaById(any())).thenReturn(pessoaFuncionario);
    	
    	when(projetoRepository.save(any())).thenReturn(projeto);
    	
    	
        Page<ProjetoResponse> result = projetoService.cadastrarProjeto(projetoRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(projetoResponseExpected.getId(), result.getContent().get(0).getId());
        assertEquals(projetoResponseExpected.getNome(), result.getContent().get(0).getNome());

    }
    
    
    @Test
    @DisplayName("Deve listar todos os projetos cadastrados")
    void caso2() {
    	
    	Pageable pageable = PageRequest.of(0, 5);
    	
    	Page<Projeto> projetoPage = new PageImpl<>(List.of(projeto));
    	
    	
    	List<ProjetoResponse> listaProejtoResponses = new ArrayList<>();
    	listaProejtoResponses.add(projetoResponseExpected);
    	
    	when(projetoRepository.findAllPessoas(pageable)).thenReturn(projetoPage);
    	when(projetoMapper.mapToListProjetoResponses(anyList())).thenReturn(listaProejtoResponses);
    	
    	Page<ProjetoResponse> result = projetoService.listarProjetos(pageable);
    	
    	assertNotNull(result);
    	assertEquals(1, result.getTotalElements());
    	assertEquals(projetoResponseExpected.getId(), result.getContent().get(0).getId());
    	assertEquals(projetoResponseExpected.getNome(), result.getContent().get(0).getNome());
    }

    @Test
    @DisplayName("Deve retornrar o projeto pelo id informado.")
    void caso3() {
    	
    	List<ProjetoResponse> listaProejtoResponses = new ArrayList<>();
    	listaProejtoResponses.add(projetoResponseExpected);
    	
    	when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projeto));
    	when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoaFuncionario));
    	when(projetoMapper.mapToProjetoResponse(any(Projeto.class))).thenReturn(projetoResponseExpected);
    	
    	Page<ProjetoResponse> result = projetoService.consultarProjeto(projeto.getId());
    	
    	assertNotNull(result);
    	assertEquals(1, result.getTotalElements());
    	assertEquals(projetoResponseExpected.getId(), result.getContent().get(0).getId());
    	assertEquals(projetoResponseExpected.getNome(), result.getContent().get(0).getNome());
    }
    

    @Test
    @DisplayName("Deve atualizar o projeto pelo id informado.")
    void caso4() {
    	
    	List<ProjetoResponse> listaProejtoResponses = new ArrayList<>();
    	listaProejtoResponses.add(projetoResponseExpected);
    	
    	when(projetoValidator.setProjeto(any())).thenReturn(projetoValidatorTest);
    	when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projeto));
    	when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoaFuncionario));
    	when(projetoMapper.mapToProjetoResponse(any())).thenReturn(projetoResponse);
    	when(pessoaValidator.getPessoaById(any())).thenReturn(pessoaFuncionario);
    	when(projetoMapper.mapProjetoToUpdate(any(), any())).thenReturn(projeto);
    	
    	
    	Page<ProjetoResponse> result = projetoService.atualizarProjeto(projeto.getId(), projetoRequest);
    	
    	assertNotNull(result);
    	assertEquals(1, result.getTotalElements());
    	assertEquals(projetoResponseExpected.getId(), result.getContent().get(0).getId());
    	assertEquals(projetoResponseExpected.getNome(), result.getContent().get(0).getNome());
    }
    
    @Test
    @DisplayName("Deve excluir um projeto")
    void caso5()  {
    	
        Pageable pageable = PageRequest.of(0, 10);
        Page<Projeto> projetoPage = new PageImpl<>(List.of(projeto));

        when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projeto));
        
        when(projetoValidator.setProjeto(any())).thenReturn(projetoValidatorTest);
    	when(pessoaValidator.getPessoaById(any())).thenReturn(pessoaFuncionario);
        
        doNothing().when(projetoRepository).delete(projeto);
        when(projetoRepository.findAll(pageable)).thenReturn(projetoPage);
        when(projetoMapper.mapToListProjetoResponses(projetoPage.getContent())).thenReturn(List.of());

        Page<ProjetoResponse> result = projetoService.excluirProjeto(1L, pageable);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
        
    }

    @Test
    @DisplayName("Deve lançar RegraNegocioException ao cadastrar um projeto inválido")
    void caso6() {
    	
    	when(projetoValidator.convertToProjeto(any())).thenThrow(RegraNegocioException.class);
    	
    	assertThrows(RegraNegocioException.class, () -> projetoService.cadastrarProjeto(projetoRequest));
    }
    
    @Test
    @DisplayName("Deve lançar PessoaNotFoundException ao cadastrar um projeto inválido")
    void caso7() {
    	
    	when(projetoValidator.convertToProjeto(any())).thenReturn(projetoValidatorTest);
    	when(pessoaValidator.getPessoaById(any())).thenThrow(
    			new PessoaNotFoundException(String.format(MSG_ERRO_PESSOA_NAO_ENCONTRADA_COM_O_ID, pessoaFuncionario.getId())));
    	
    	PessoaNotFoundException exception = assertThrows(PessoaNotFoundException.class, () -> projetoService.cadastrarProjeto(projetoRequest));
    	assertEquals(String.format(MSG_ERRO_PESSOA_NAO_ENCONTRADA_COM_O_ID, pessoaFuncionario.getId()), exception.getMessage());
    }

}
