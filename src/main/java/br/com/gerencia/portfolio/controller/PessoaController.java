package br.com.gerencia.portfolio.controller;

import br.com.gerencia.portfolio.dto.request.PessoaRequest;
import br.com.gerencia.portfolio.dto.response.PessoaResponse;
import br.com.gerencia.portfolio.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> adicionarPessoa(@RequestBody PessoaRequest pessoaRequest) {
        pessoaService.cadastrarPessoa(pessoaRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa criada com sucesso");
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaResponse> consultarPessoa(@PathVariable Long id) {
        return ResponseEntity.ok().body(pessoaService.consultarPessoa(id));
    }

    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PessoaResponse>> listarPessoa(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(pessoaService.listarPessoa(pageable));
    }
}
