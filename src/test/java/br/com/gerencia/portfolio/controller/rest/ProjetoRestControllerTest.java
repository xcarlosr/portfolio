package br.com.gerencia.portfolio.controller.rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import br.com.gerencia.portfolio.config.ConfigTest;
import br.com.gerencia.portfolio.dto.request.ProjetoRequest;
import br.com.gerencia.portfolio.dto.response.ProjetoResponse;
import br.com.gerencia.portfolio.repository.PessoaRepository;
import br.com.gerencia.portfolio.repository.ProjetoRepository;
import br.com.gerencia.portfolio.service.ProjetoService;
import br.com.gerencia.portfolio.validator.PessoaValidator;
import br.com.gerencia.portfolio.validator.ProjetoValidator;
import io.restassured.http.ContentType;

/**
 * @author Carlos Roberto
 * @created 19/05/2023
 * @since 1.0
 */
@Sql(scripts = {"classpath:/sql/data-projeto.sql"}, executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ProjetoRestControllerTest extends ConfigTest {


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
    
    @BeforeEach
    void reset() {
        Mockito.reset(projetoRepository);
        Mockito.reset(pessoaRepository);
    }

    
    @Test
    @DisplayName("Deve cadastrar um novo projeto")
    void caso1() throws IOException {
    	
        String responseBody = given()
				                .contentType(ContentType.JSON)
				                .body(getJsonAsString("projeto/request_cadastrar_projeto.json"))
			                .when()
			                	.post("/projetos")
			                .then()
				                .assertThat()
				                .statusCode(HttpStatus.CREATED.value())
				                .extract().response().asString();


        ProjetoResponse projetoResponse = getPageContent(responseBody, ProjetoResponse.class).get(0);
        
        ProjetoResponse projetoResponseExpected = getResponseExpected("projeto/response_cadastrar_projeto_expected.json", ProjetoResponse.class);
        
        assertEquals(projetoResponseExpected.getId(), projetoResponse.getId());
        assertEquals(projetoResponseExpected.getNome(), projetoResponse.getNome());
        assertEquals(projetoResponseExpected.getDescricao(), projetoResponse.getDescricao());
        assertEquals(projetoResponseExpected.getOrcamento(), projetoResponse.getOrcamento());
        assertEquals(projetoResponseExpected.getGerente().getId(), projetoResponse.getGerente().getId());
        assertEquals(projetoResponseExpected.getGerente().getNome(), projetoResponse.getGerente().getNome());
        assertEquals(projetoResponseExpected.getGerente().getCpf(), projetoResponse.getGerente().getCpf());
        
        verify(projetoService, times(1)).cadastrarProjeto(any(ProjetoRequest.class));

    }

}

