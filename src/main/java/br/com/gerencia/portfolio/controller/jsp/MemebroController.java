package br.com.gerencia.portfolio.controller.jsp;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.gerencia.portfolio.dto.response.MembroResponse;
import br.com.gerencia.portfolio.service.MembroService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 09/05/2023
 * @since 1.0
 */
@Hidden
@Controller
@RequiredArgsConstructor
@RequestMapping("/jsp/projetos/{idProjeto}/membros")
public class MemebroController {

    private final MembroService membroService;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String listarMembros(Model model, @PathVariable Long idProjeto){
    	
    	MembroResponse membros = membroService.listarMembros(idProjeto);

        model.addAttribute("membros", membros);

        return "membro-lista";
    }
}
