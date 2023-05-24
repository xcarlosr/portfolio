package br.com.gerencia.portfolio.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gerencia.portfolio.dto.request.MembroRequest;
import br.com.gerencia.portfolio.dto.response.MembroResponse;
import br.com.gerencia.portfolio.exception.MembrosNaosalvosException;
import br.com.gerencia.portfolio.service.MembroService;
import lombok.RequiredArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projetos/{idProjeto}/membros")
public class MemebroRestController {

    private final MembroService membroService;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MembroResponse> listarMembrosPaginado(@PathVariable Long idProjeto){
        return ResponseEntity.ok(membroService.listarMembros(idProjeto));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> criarMembro(@PathVariable Long idProjeto, @RequestBody List<MembroRequest> listMembros){

        try{
            membroService.vincularMembrosProjeto(idProjeto, listMembros);
            return ResponseEntity.status(HttpStatus.CREATED).body("Operação realizada com sucesso");
        } catch (MembrosNaosalvosException ex){
            return ResponseEntity.status(HttpStatus.CREATED).body(ex.getMembrosNaoSalvosResponse());
        }
    }
}
