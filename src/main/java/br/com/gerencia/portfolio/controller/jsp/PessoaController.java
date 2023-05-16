package br.com.gerencia.portfolio.controller.jsp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.gerencia.portfolio.dto.response.PessoaResponse;
import br.com.gerencia.portfolio.service.PessoaService;
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
@RequestMapping("/jsp/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @GetMapping(value = "/{id:[0-9]}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String consultarPessoa(Model model, 
    							  @PathVariable Long id) {
    	
    	model.addAttribute("pessoaResponse", pessoaService.consultarPessoa(id));
    	return "peossoa-lista";
    }
    
    @ResponseBody
    @GetMapping(value = "/lista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PessoaResponse> listar() {

    	return pessoaService.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String listarPaginado(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        
        Page<PessoaResponse> pessoas = pessoaService.listarPaginado(PageRequest.of(page, size, sort));

        model.addAttribute("pessoas", pessoas);

        return "pessoa-lista";
    }
}
