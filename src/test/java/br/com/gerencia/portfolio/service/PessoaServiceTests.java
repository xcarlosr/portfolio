package br.com.gerencia.portfolio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.test.context.ContextConfiguration;

import br.com.gerencia.portfolio.dto.request.PessoaRequest;
import br.com.gerencia.portfolio.dto.response.PessoaResponse;
import br.com.gerencia.portfolio.entity.Pessoa;
import br.com.gerencia.portfolio.exception.PessoaErrorException;
import br.com.gerencia.portfolio.exception.PessoaNotFoundException;
import br.com.gerencia.portfolio.mappers.PessoaMapper;
import br.com.gerencia.portfolio.mappers.PessoaMapperImpl;
import br.com.gerencia.portfolio.repository.PessoaRepository;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {PessoaService.class, PessoaMapperImpl.class})
public class PessoaServiceTests {
	
	private static final String MSG_ERROR_CONNECTION_TIME_OUT = "Connection time out";

	private static final String MSG_ERROR_PESSOA_NAO_ENCONTRADA_COM_O_ID = "Pessoa não encontrada com o id: %d";
	
	private static final String MSG_ERROR_NAO_FOI_POSSIVEL_SALVAR_A_PESSOA = "Não foi possível salvar a pessoa.";

	@Mock
    private PessoaRepository pessoaRepository;

	@Mock
	private PessoaMapper pessoaMapper;
	
    @InjectMocks
    private PessoaService pessoaService;
        
    private PessoaRequest pessoaRequest;
    
    private PessoaResponse pessoaResponseExpected;
    
    private List<PessoaResponse> pessoasResponseExpected;
    
    private Pessoa pessoa;
    
	private List<Pessoa> pessoas;
    
    @BeforeEach
    void setUp() {
    	
    	pessoas = new ArrayList<>();
    	
    	pessoa = Pessoa.builder()
    			.id(1L)
        		.nome("Pessoa 1")
        		.cargo("Cargo pessoa 1")
        		.build();
    			
    	pessoaRequest = PessoaRequest.builder()
    			.nome("Pessoa 1")
        		.cargo("Cargo pessoa 1")
        		.build();
    	
    	pessoaResponseExpected = PessoaResponse.builder()
    			.id(1L)
    			.nome("Pessoa 1")
        		.cargo("Cargo pessoa 1")
    			.build();
    	
    	Pessoa pessoa2 = Pessoa.builder()
    			.id(2L)
        		.nome("Pessoa 2")
        		.cargo("Cargo da pessoa 2")
        		.build();
    	
    	Pessoa pessoa3 = Pessoa.builder()
    			.id(3L)
        		.nome("Pessoa 3")
        		.cargo("Cargo da pessoa 3")
        		.build();
    	
    	pessoas = Arrays.asList(
        		pessoa,
        		pessoa2,
        		pessoa3);
    	
		pessoasResponseExpected = Arrays.asList(
				PessoaResponse.builder().id(1L).nome("Pessoa 1").cargo("Cargo pessoa 1").build(),
				PessoaResponse.builder().id(2L).nome("Pessoa 2").cargo("Cargo pessoa 2").build(),
				PessoaResponse.builder().id(3L).nome("Pessoa 3").cargo("Cargo pessoa 3").build());
    }

    @Test
    @DisplayName("Deve cadatrar uma nova pessoa.")
    void teste01() {

    	when(pessoaMapper.mapToPessoa(any())).thenReturn(pessoa);
    	when(pessoaMapper.mapToPessoaResponse(any())).thenReturn(pessoaResponseExpected);
    	when(pessoaRepository.save(any())).thenReturn(pessoa);

        PessoaResponse result = pessoaService.cadastrarPessoa(pessoaRequest);

        assertNotNull(result);
        assertEquals(pessoaResponseExpected.getId(), result.getId());
        assertEquals(pessoaResponseExpected.getNome(), result.getNome());
    }
    
    @Test
    @DisplayName("Deve lançar PessoaErrorException ao cadatrar uma nova pessoa.")
    void teste02() {

    	when(pessoaRepository.save(any())).thenThrow(new CannotGetJdbcConnectionException(MSG_ERROR_CONNECTION_TIME_OUT));

    	try {
			pessoaService.cadastrarPessoa(pessoaRequest);
    		
    	}catch (PessoaErrorException e) {
			assertEquals(MSG_ERROR_CONNECTION_TIME_OUT, e.getCause().getMessage());
			assertEquals(MSG_ERROR_NAO_FOI_POSSIVEL_SALVAR_A_PESSOA, e.getMessage());
		}
    }
    
    
    @Test
    @DisplayName("Deve retornar PessoaResponse ao consultar uma pessoa.")
    void teste03() {
    	
    	when(pessoaMapper.mapToPessoaResponse(any())).thenReturn(pessoaResponseExpected);
    	when(pessoaRepository.findById(any())).thenReturn(Optional.of(pessoa));

        PessoaResponse result = pessoaService.consultarPessoa(pessoa.getId());

        assertNotNull(result);
        assertEquals(pessoaResponseExpected.getId(), result.getId());
        assertEquals(pessoaResponseExpected.getNome(), result.getNome());
    }
    
    @Test
    @DisplayName("Deve lançar PessoaNotFoundException ao consultar uma pessoa com id inválido.")
    void teste04() {
    	
    	when(pessoaRepository.findById(any())).thenReturn(Optional.empty());
    	
    	assertThrows(PessoaNotFoundException.class,() -> pessoaService.consultarPessoa(pessoa.getId()), 
    			String.format(MSG_ERROR_PESSOA_NAO_ENCONTRADA_COM_O_ID, pessoa.getId()));
			
    }
    
    @Test
    @DisplayName("Deve retornar lista paginada de PessoaResponse.")
    void teste05() {
    	
        Pageable pageable = PageRequest.of(0, 5);
        Page<Pessoa> pessoaPage = new PageImpl<>(pessoas, pageable, 3L);

    	when(pessoaMapper.mapToPessoaResponse(any()))
    	  .thenReturn(pessoasResponseExpected.get(0), 
	    			  pessoasResponseExpected.get(1), 
	    	   		  pessoasResponseExpected.get(2));
    	
        when(pessoaRepository.findAll(pageable)).thenReturn(pessoaPage);

        Page<PessoaResponse> result = pessoaService.listarPaginado(pageable);

        assertEquals(0, result.getNumber()); 
        assertEquals(5, result.getSize()); 
        assertEquals(3, result.getTotalElements()); 
        assertEquals(1, result.getTotalPages());
        
        List<PessoaResponse> pessoaResponses = result.getContent();
        assertEquals(3, pessoaResponses.size());

        assertEquals(pessoas.get(0).getId(), pessoaResponses.get(0).getId());
        assertEquals(pessoas.get(0).getNome(), pessoaResponses.get(0).getNome());
        
        assertEquals(pessoas.get(1).getId(), pessoaResponses.get(1).getId());
        assertEquals(pessoas.get(1).getNome(), pessoaResponses.get(1).getNome());
        
        assertEquals(pessoas.get(2).getId(), pessoaResponses.get(2).getId());
        assertEquals(pessoas.get(2).getNome(), pessoaResponses.get(2).getNome());
    }
    
    @Test
    @DisplayName("Deve retornar lista de PessoaResponse.")
    void teste06() {

    	when(pessoaMapper.mapToPessoaResponse(any()))
    	  .thenReturn(pessoasResponseExpected.get(0), 
	    			  pessoasResponseExpected.get(1), 
	    	   		  pessoasResponseExpected.get(2));
    	
        when(pessoaRepository.findAllPessoas()).thenReturn(pessoas);

        List<PessoaResponse> result = pessoaService.listar();

        assertEquals(3, result.size());

        assertEquals(pessoas.get(0).getId(), result.get(0).getId());
        assertEquals(pessoas.get(0).getNome(), result.get(0).getNome());
        
        assertEquals(pessoas.get(1).getId(), result.get(1).getId());
        assertEquals(pessoas.get(1).getNome(), result.get(1).getNome());
        
        assertEquals(pessoas.get(2).getId(), result.get(2).getId());
        assertEquals(pessoas.get(2).getNome(), result.get(2).getNome());
    }
}
