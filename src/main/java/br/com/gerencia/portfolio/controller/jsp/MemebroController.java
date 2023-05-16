package br.com.gerencia.portfolio.controller;

import br.com.gerencia.portfolio.dto.request.MembroRequest;
import br.com.gerencia.portfolio.dto.response.MembroResponse;
import br.com.gerencia.portfolio.exception.MembrosNaosalvosException;
import br.com.gerencia.portfolio.service.MembroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projetos/{idProjeto}/membros")
public class MemebroController {

    private final MembroService membroService;


    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MembroResponse> listarMembros(@PathVariable Long idProjeto){
        return ResponseEntity.ok(membroService.listarMembros(idProjeto));
    }

    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> criarMembro(@PathVariable Long idProjeto, @RequestBody List<MembroRequest> listMembros){

        try{
            membroService.vincularMembrosProjeto(idProjeto, listMembros);
            return ResponseEntity.status(HttpStatus.CREATED).body("Operação realizada com sucesso");
        } catch (MembrosNaosalvosException ex){
            return ResponseEntity.status(HttpStatus.CREATED).body(ex.getMembrosNaoSalvosResponse());
        }
    }
}
