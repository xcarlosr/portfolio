package br.com.gerencia.portfolio.controller;

import br.com.gerencia.portfolio.dto.request.ProjetoRequest;
import br.com.gerencia.portfolio.dto.response.ProjetoResponse;
import br.com.gerencia.portfolio.service.ProjetoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Carlos Roberto
 * @created 05/05/2023
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService portfolioService;

    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> cadastrarProjeto(@RequestBody ProjetoRequest projetoResquest) {
        return ResponseEntity.ok().body(portfolioService.cadastrarProjeto(projetoResquest));
    }

    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> listarProjetos(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(portfolioService.listarProjetos(pageable));
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> consultarProjeto(@PathVariable Long id) {
        return ResponseEntity.ok().body(portfolioService.consultarProjeto(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoRequest projetoResquest) {
       return ResponseEntity.ok().body(portfolioService.atualizarProjeto(id, projetoResquest));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> atualizarParcialProjeto(@PathVariable Long id, @RequestBody ProjetoRequest projetoResquest) {
        return ResponseEntity.ok().body(portfolioService.atualizarProjeto(id, projetoResquest));
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> excluirProjeto(@PathVariable Long id) {
        return ResponseEntity.ok().body(portfolioService.excluirProjeto(id));
    }
}
