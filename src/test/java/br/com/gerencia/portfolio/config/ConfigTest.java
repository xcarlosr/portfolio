package br.com.gerencia.portfolio.config;

import static com.github.tomakehurst.wiremock.client.WireMock.reset;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import net.minidev.json.JSONValue;

/**
 * @author Carlos Roberto
 * @created 19/05/2023
 * @since 1.0
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = PortFolioApplicationTestContext.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public abstract class ConfigTest {

    private static final String JSON = "/json/%s";

    @LocalServerPort
    private int port;
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        reset();
        RestAssured.port = port;
        enableLoggingOfRequestAndResponseIfValidationFails();
    }
    
    /**
     * 
     * Recebe o Response retornado da chamada given, 
     * e o tipo da classe para criar a Lista.
     * 
     * @param <T>
     * @param body
     * @param type
     * @return
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    public <T> List<T> getPageContent(final String body, Class<T> type) throws JsonProcessingException {
    	JsonNode jsonResponse = objectMapper.readTree(body);
        JsonNode contentNode = jsonResponse.get("content");

        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);
        return objectMapper.convertValue(contentNode, javaType);
    }
    
    /**
     * Recebe o caminho do arquivo no formado json, e o tipo esperado, 
     * para criar o objeto esperado. 
     * 
     * @param <T>
     * @param path
     * @param type
     * @return
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     * @throws IOException 
     */
    public <T> T getResponseExpected(final String path, Class<T> type) throws JsonProcessingException {
    	 JsonNode jsonResponseExpected = objectMapper.readTree(getJsonAsString(path));
         return objectMapper.convertValue(jsonResponseExpected, type);
    }

    public String getJsonAsString(final String path){
        return JSONValue.parse(
                new InputStreamReader(
                    Objects.requireNonNull(
                        getClass().getResourceAsStream(String.format(JSON, path))),
                        StandardCharsets.UTF_8)).toString();
    }

}
