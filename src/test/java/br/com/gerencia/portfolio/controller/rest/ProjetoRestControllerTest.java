package br.com.gerencia.portfolio.controller.rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.gerencia.portfolio.config.ConfigTest;
import br.com.gerencia.portfolio.dto.request.ProjetoRequest;
import br.com.gerencia.portfolio.dto.response.ProjetoResponse;
import br.com.gerencia.portfolio.entity.Projeto;
import br.com.gerencia.portfolio.exception.GlobalExceptionHandler.ErrorResponse;
import br.com.gerencia.portfolio.mappers.ProjetoMapper;
import br.com.gerencia.portfolio.repository.PessoaRepository;
import br.com.gerencia.portfolio.repository.ProjetoRepository;
import br.com.gerencia.portfolio.service.ProjetoService;
import br.com.gerencia.portfolio.validator.PessoaValidator;
import br.com.gerencia.portfolio.validator.ProjetoValidator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * @author Carlos Roberto
 * @created 19/05/2023
 * @since 1.0
 */
@Sql(scripts = {"classpath:/sql/data-projeto.sql"}, executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProjetoRestControllerTest extends ConfigTest {

    private static final int TOTAL_ELEMENTOS = 15;

	private static final int PAGE_SIZE = 5;

	private static final int PAGE_NUMBER = 0;

	@SpyBean
    private ProjetoRepository projetoRepository;

    @SpyBean
    private PessoaRepository pessoaRepository;

    @SpyBean
    private ProjetoValidator projetoValidator;

    @SpyBean
    private PessoaValidator pessoaValidator;
    
    @SpyBean
    private ProjetoService projetoService;
    
    @SpyBean
    private ProjetoMapper projetoMapper;
    
    @BeforeEach
    void reset() {
        Mockito.reset(projetoRepository);
        Mockito.reset(pessoaRepository);
    }
    
    @Test
    @DisplayName("Deve cadastrar um novo projeto")
    void caso01() throws JsonProcessingException  {
    	
        Response response = given()
                .contentType(ContentType.JSON)
                .body(getJsonAsString("projeto/requests/request_cadastrar_projeto.json"))
            .when()
                .post("/projetos")
            .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                	.response();


        ProjetoResponse projetoResponse = getPageContent(response.asString(), ProjetoResponse.class).get(0);
        
        ProjetoResponse projetoResponseSucesso = getResponseExpected("projeto/responses/response_cadastrar_projeto.json", ProjetoResponse.class);
        
        assertEquals(projetoResponseSucesso.getId(), projetoResponse.getId());
        assertEquals(projetoResponseSucesso.getNome(), projetoResponse.getNome());
        assertEquals(projetoResponseSucesso.getDescricao(), projetoResponse.getDescricao());
        assertEquals(projetoResponseSucesso.getOrcamento(), projetoResponse.getOrcamento());
        assertEquals(projetoResponseSucesso.getGerente().getId(), projetoResponse.getGerente().getId());
        assertEquals(projetoResponseSucesso.getGerente().getNome(), projetoResponse.getGerente().getNome());
        assertEquals(projetoResponseSucesso.getGerente().getCpf(), projetoResponse.getGerente().getCpf());
        
        verify(projetoService, times(1)).cadastrarProjeto(any(ProjetoRequest.class));

    }

    @Test
    @DisplayName("Deve lançar uma RegraNegocioException, ao tentar cadastrar um novo projeto com um gerente que não é funcionário.")
    void caso02() throws JsonProcessingException{

        ErrorResponse errorResponse = given()
                .contentType(ContentType.JSON)
                .body(getJsonAsString("projeto/requests/request_cadastrar_projeto_gerente_nao_funcionario.json"))
            .when()
                .post("/projetos")
            .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);
        
        ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_400_gerente_nao_funcionario.json", ErrorResponse.class);

        assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
        assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());

        verify(projetoService, times(1)).cadastrarProjeto(any(ProjetoRequest.class));
        
    }
    
    @Test
    @DisplayName("Deve lançar uma PessoaNotFoundException, ao tentar cadastrar um novo projeto com um gerente não encontrado.")
    void caso03() throws JsonProcessingException{

        ErrorResponse errorResponse = given()
                .contentType(ContentType.JSON)
                .body(getJsonAsString("projeto/requests/request_cadastrar_projeto_gerente_nao_encontrada.json"))
            .when()
                .post("/projetos")
            .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract().as(ErrorResponse.class);
        
        ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_404_pessoa_nao_encontrada.json", ErrorResponse.class);

        assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
        assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());

        verify(projetoService, times(1)).cadastrarProjeto(any(ProjetoRequest.class));
        
    }
    
    @Test
    @DisplayName("Deve lançar uma ProjetoErrorException, ao tentar cadastrar um novo projeto.")
    void caso04() throws JsonProcessingException {
    	
    	when(projetoMapper.mapToProjeto(any(ProjetoRequest.class))).thenThrow(RuntimeException.class);
    	
    	ErrorResponse errorResponse = given()
                .contentType(ContentType.JSON)
                .body(getJsonAsString("projeto/requests/request_cadastrar_projeto.json"))
            .when()
                .post("/projetos")
            .then()
                .assertThat()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .extract().as(ErrorResponse.class);

		ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_500_salvar_projeto.json", ErrorResponse.class);
		
		assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
		assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());
		
		verify(projetoService, times(1)).cadastrarProjeto(any(ProjetoRequest.class));
    	
    }
    
    
    @Test
    @DisplayName("Deve lançar uma ProjetoErrorException, ao tentar listar os projetos.")
    void caso05() throws JsonProcessingException {
    	
    	when(projetoMapper.mapToListProjetoResponses(anyList())).thenThrow(new RuntimeException("Error ao listar projeto"));

    	ErrorResponse errorResponse = given()
			.when()
				.get("/projetos")
			.then()
    			.assertThat()
    			.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
    			.extract().as(ErrorResponse.class);
    	
    	
    	ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_500_listar_projeto.json", ErrorResponse.class);
		
		assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
		assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());
		
		verify(projetoService, times(1)).listarProjetos(any(Pageable.class));
    	
    }
    
    
    @Test
    @DisplayName("Deve lançar uma ProjetoNotFoundException, ao tentar consultar um projeto por id.")
    void caso06() throws JsonProcessingException {
    	
    	when(projetoMapper.mapToListProjetoResponses(anyList())).thenThrow(RuntimeException.class);

    	ErrorResponse errorResponse = given()
			.when()
				.get("/projetos/99")
			.then()
    			.assertThat()
    			.statusCode(HttpStatus.NOT_FOUND.value())
    			.extract().as(ErrorResponse.class);
    	
    	
    	ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_404_buscar_projeto_por_id_99.json", ErrorResponse.class);
		
		assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
		assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());
		
		verify(projetoService, times(1)).consultarProjeto(any(Long.class));
    	
    }

    @Test
    @DisplayName("Deve lançar uma ProjetoErrorException, ao tentar consultar um projeto por id.")
    void caso07() throws JsonProcessingException {
    	
    	when(projetoMapper.mapToProjetoResponse(any(Projeto.class))).thenThrow(RuntimeException.class);

    	ErrorResponse errorResponse = given()
			.when()
				.get("/projetos/2")
			.then()
    			.assertThat()
    			.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
    			.extract().as(ErrorResponse.class);
    	
    	
    	ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_500_busucar_projeto_por_id_2.json", ErrorResponse.class);
		
		assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
		assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());
		
		verify(projetoService, times(1)).consultarProjeto(any(Long.class));
    	
    }
    
    
    @Test
    @DisplayName("Deve lançar uma RegraNegocioException, ao tentar atualizar um projeto quando o gerente não é funcionário.")
    void caso08() throws JsonProcessingException {
    	
    	ErrorResponse errorResponse = given()
                .contentType(ContentType.JSON)
                .body(getJsonAsString("projeto/requests/request_cadastrar_projeto_gerente_nao_funcionario.json"))
            .when()
                .put("/projetos/2")
            .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);
        
        ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_400_gerente_nao_funcionario.json", ErrorResponse.class);

        assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
        assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());

        verify(projetoService, times(1)).atualizarProjeto(anyLong(), any(ProjetoRequest.class));
    	
    }
    
    @Test
    @DisplayName("Deve lançar uma ProjetoNotFoundException, ao tentar atualizar um projeto quando o projeto não é encontrado.")
    void caso09() throws JsonProcessingException{

        ErrorResponse errorResponse = given()
                .contentType(ContentType.JSON)
                .body(getJsonAsString("projeto/requests/request_cadastrar_projeto_gerente_nao_encontrada.json"))
            .when()
            .put("/projetos/99")
            .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract().as(ErrorResponse.class);
        
        ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_404_buscar_projeto_por_id_99.json", ErrorResponse.class);

        assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
        assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());

        verify(projetoService, times(1)).atualizarProjeto(anyLong(), any(ProjetoRequest.class));
        
    }
    
    @Test
    @DisplayName("Deve lançar uma PessoaNotFoundException, ao tentar atualizar um projeto quando o pessoa não é encontrado.")
    void caso10() throws JsonProcessingException{

        ErrorResponse errorResponse = given()
                .contentType(ContentType.JSON)
                .body(getJsonAsString("projeto/requests/request_cadastrar_projeto_gerente_nao_encontrada.json"))
            .when()
            .put("/projetos/2")
            .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract().as(ErrorResponse.class);
        
        ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_404_pessoa_nao_encontrada.json", ErrorResponse.class);

        assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
        assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());

        verify(projetoService, times(1)).atualizarProjeto(anyLong(), any(ProjetoRequest.class));
    }
    
    @Test
    @DisplayName("Deve lançar uma ProjetoErrorException, ao tentar atualizar um projeto.")
    void caso11() throws JsonProcessingException {
    	
    	when(projetoMapper.mapProjetoToUpdate(any(ProjetoRequest.class), any(Projeto.class))).thenThrow(RuntimeException.class);
    	
    	ErrorResponse errorResponse = given()
                .contentType(ContentType.JSON)
                .body(getJsonAsString("projeto/requests/request_atualizar_projeto.json"))
            .when()
            .put("/projetos/2")
            .then()
                .assertThat()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .extract().as(ErrorResponse.class);

		ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_500_atualizar_projeto_por_id_2.json", ErrorResponse.class);
		
		assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
		assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());
		
		verify(projetoService, times(1)).atualizarProjeto(anyLong(), any(ProjetoRequest.class));
    	
    }
    
     @Test
     @DisplayName("Deve listar os projetos pagaginado, na primeira página, com 5 elementos por página e total de 15 elementos.")
    void caso12() throws JsonProcessingException {
    	
    	Response response = given()
			.when()
				.get("/projetos")
			.then()
    			.assertThat()
    			.statusCode(HttpStatus.OK.value())
			.extract()
				.response();
    	
    	ProjetoResponse projetoResponse = getPageContent(response.asString(), ProjetoResponse.class).get(0);
    	
    	
    	ProjetoResponse projetoResponseExpected = getResponseExpected("projeto/responses/response_listar_projeto.json", ProjetoResponse.class);
        
        assertEquals(projetoResponseExpected.getId(), projetoResponse.getId());
        assertEquals(projetoResponseExpected.getNome(), projetoResponse.getNome());
        assertEquals(projetoResponseExpected.getDescricao(), projetoResponse.getDescricao());
        assertEquals(projetoResponseExpected.getOrcamento(), projetoResponse.getOrcamento());
        assertEquals(projetoResponseExpected.getGerente().getId(), projetoResponse.getGerente().getId());
        assertEquals(projetoResponseExpected.getGerente().getNome(), projetoResponse.getGerente().getNome());
        assertEquals(projetoResponseExpected.getGerente().getCpf(), projetoResponse.getGerente().getCpf());
        
        assertEquals(PAGE_NUMBER, response.jsonPath().getInt("number"));
        assertEquals(PAGE_SIZE, response.jsonPath().getInt("size"));
        assertEquals(TOTAL_ELEMENTOS, response.jsonPath().getInt("totalElements"));
		
		verify(projetoService, times(1)).listarProjetos(any(Pageable.class));
    	
    }
    
	@Test
	@DisplayName("Deve retornar um projeto pelo id.")
	void caso13() throws JsonProcessingException {

		Response response = given()
				.when()
					.get("/projetos/2")
				.then()
					.assertThat()
					.statusCode(HttpStatus.OK.value())
				.extract()
					.response();

		ProjetoResponse projetoResponse = getPageContent(response.asString(), ProjetoResponse.class).get(0);

		ProjetoResponse projetoResponseExpected = getResponseExpected("projeto/responses/response_buscar_projeto_por_id.json", ProjetoResponse.class);

		assertEquals(projetoResponseExpected.getId(), projetoResponse.getId());
		assertEquals(projetoResponseExpected.getNome(), projetoResponse.getNome());
		assertEquals(projetoResponseExpected.getDescricao(), projetoResponse.getDescricao());
		assertEquals(projetoResponseExpected.getOrcamento(), projetoResponse.getOrcamento());
		assertEquals(projetoResponseExpected.getGerente().getId(), projetoResponse.getGerente().getId());
		assertEquals(projetoResponseExpected.getGerente().getNome(), projetoResponse.getGerente().getNome());
		assertEquals(projetoResponseExpected.getGerente().getCpf(), projetoResponse.getGerente().getCpf());

		verify(projetoService, times(1)).consultarProjeto(anyLong());

	}
	
     @Test
     @DisplayName("Deve retornar o projeto atualizado.")
     void caso14() throws JsonProcessingException {
    	 
    	 Response response = given()
    			 .contentType(ContentType.JSON)
    			 .body(getJsonAsString("projeto/requests/request_atualizar_projeto.json"))
    			 .when()
    			 	.put("/projetos/10")
    			 .then()
    			 	.assertThat()
    			 	.statusCode(HttpStatus.OK.value())
    			 .extract()
    			 	.response();
    	 
    	 ProjetoResponse projetoResponse = getPageContent(response.asString(), ProjetoResponse.class).get(0);
    	 
    	 ProjetoResponse projetoResponseExpected = getResponseExpected("projeto/responses/response_atualizar_projeto.json", ProjetoResponse.class);
    	 
    	 assertEquals(projetoResponseExpected.getId(), projetoResponse.getId());
         assertEquals(projetoResponseExpected.getNome(), projetoResponse.getNome());
         assertEquals(projetoResponseExpected.getDescricao(), projetoResponse.getDescricao());
         assertEquals(projetoResponseExpected.getOrcamento(), projetoResponse.getOrcamento());
         assertEquals(projetoResponseExpected.getGerente().getId(), projetoResponse.getGerente().getId());
         assertEquals(projetoResponseExpected.getGerente().getNome(), projetoResponse.getGerente().getNome());
         assertEquals(projetoResponseExpected.getGerente().getCpf(), projetoResponse.getGerente().getCpf());
         
 		verify(projetoService, times(1)).atualizarProjeto(anyLong(), any(ProjetoRequest.class));
    	 
    }
     
     @Test
     @DisplayName("Deve retornar o projeto atualizado, somente o que foi enviado.")
     void caso15 () throws JsonProcessingException {
    	 
    	 Response response = given()
    			 .contentType(ContentType.JSON)
    			 .body(getJsonAsString("projeto/requests/request_atualizar_alguns_campos_projeto.json"))
    			 .when()
    			 	.patch("/projetos/10")
    			 .then()
    			 	.assertThat()
    			 	.statusCode(HttpStatus.OK.value())
    			 .extract()
    			 	.response();
    	 
    	 ProjetoResponse projetoResponse = getPageContent(response.asString(), ProjetoResponse.class).get(0);
    	 
    	 ProjetoResponse projetoResponseExpected = getResponseExpected("projeto/responses/response_atualizar_alguns_campos_projeto.json", ProjetoResponse.class);
    	 
    	 assertEquals(projetoResponseExpected.getId(), projetoResponse.getId());
         assertEquals(projetoResponseExpected.getNome(), projetoResponse.getNome());
         assertEquals(projetoResponseExpected.getDescricao(), projetoResponse.getDescricao());
         assertEquals(projetoResponseExpected.getOrcamento(), projetoResponse.getOrcamento());
         assertEquals(projetoResponseExpected.getGerente().getId(), projetoResponse.getGerente().getId());
         assertEquals(projetoResponseExpected.getGerente().getNome(), projetoResponse.getGerente().getNome());
         assertEquals(projetoResponseExpected.getGerente().getCpf(), projetoResponse.getGerente().getCpf());
         
 		verify(projetoService, times(1)).atualizarProjeto(anyLong(), any(ProjetoRequest.class));
    	 
    }
     
     @Test
     @DisplayName("Deve deletar o projeto.")
     void caso16 () throws JsonProcessingException {
    	 
    	 	given()
    			 .when()
    			 	.delete("/projetos/10")
    			 .then()
    			 	.assertThat()
    			 	.statusCode(HttpStatus.OK.value())
    			 .extract()
    			 	.response();
    	 	
    	 	ErrorResponse errorResponse = given()
			    	 	.when()
						 	.get("/projetos/10")
						 .then()
						 	.assertThat()
						 	.statusCode(HttpStatus.NOT_FOUND.value())
						 .extract()
						 	.as(ErrorResponse.class);	
    	 	
			ErrorResponse erroResponseExpected =  getResponseExpected("projeto/errors/response_error_404_buscar_projeto_por_id_10.json", ErrorResponse.class);

			assertEquals(erroResponseExpected.getStatus(), errorResponse.getStatus());
			assertEquals(erroResponseExpected.getMessage(), errorResponse.getMessage());

			verify(projetoService, times(1)).excluirProjeto(anyLong(), any());

			verify(projetoService, times(1)).consultarProjeto(anyLong());
    	 
    }
}