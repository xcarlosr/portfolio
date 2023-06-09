package br.com.gerencia.portfolio.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gerencia.portfolio.dto.request.ProjetoRequest;
import br.com.gerencia.portfolio.dto.response.ProjetoResponse;
import br.com.gerencia.portfolio.service.ProjetoService;
import lombok.RequiredArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 05/05/2023
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projetos")
public class ProjetoRestController {

	private final ProjetoService projetoService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> cadastrarProjeto(@RequestBody ProjetoRequest projetoResquest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projetoService.cadastrarProjeto(projetoResquest));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProjetoResponse> listarProjetos(
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return projetoService.listarProjetos(pageable);
    }

    @GetMapping(value = "/{id:[0-9]{1,4}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> consultarProjeto(@PathVariable Long id) {
        return ResponseEntity.ok().body(projetoService.consultarProjeto(id));
    }

    @PutMapping(value = "/{id:[0-9]{1,4}}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoRequest projetoResquest) {
       return ResponseEntity.ok().body(projetoService.atualizarProjeto(id, projetoResquest));
    }

    @PatchMapping(value = "/{id:[0-9]{1,4}}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> atualizarParcialProjeto(@PathVariable Long id, @RequestBody ProjetoRequest projetoResquest) {
        return ResponseEntity.ok().body(projetoService.atualizarProjeto(id, projetoResquest));
    }

    @DeleteMapping(value = "/{id:[0-9]{1,4}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProjetoResponse>> excluirProjeto(
            @PathVariable Long id,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(projetoService.excluirProjeto(id, pageable));
    }
}
