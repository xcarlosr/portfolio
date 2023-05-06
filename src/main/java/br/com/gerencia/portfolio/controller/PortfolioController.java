package br.com.gerencia.portfolio.controller;

import br.com.gerencia.portfolio.entity.Projeto;
import br.com.gerencia.portfolio.service.PortfolioService;
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
public class PortfolioController {

    private final PortfolioService portfolioService;


    @GetMapping
    public ResponseEntity<Page<Projeto>> listarProjetos(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Projeto> projetos = portfolioService.listarProjetos(pageable);
        return ResponseEntity.ok(projetos);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Projeto> consultarProjeto(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.consultarProjeto(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Projeto> cadastrarProjeto(@RequestBody Projeto projeto) {
        return ResponseEntity.ok(portfolioService.cadastrarProjeto(projeto));
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Projeto> atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
       return ResponseEntity.ok(portfolioService.atualizarProjeto(id, projeto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirProjeto(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.excluirProjeto(id));
    }
}
