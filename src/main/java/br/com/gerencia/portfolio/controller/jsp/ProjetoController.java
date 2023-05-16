package br.com.gerencia.portfolio.controller.jsp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.gerencia.portfolio.dto.request.ProjetoRequest;
import br.com.gerencia.portfolio.dto.response.ProjetoResponse;
import br.com.gerencia.portfolio.service.ProjetoService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

/**
 * @author Carlos Roberto
 * @created 05/05/2023
 * @since 1.0
 */
@Hidden
@Controller
@RequiredArgsConstructor
@RequestMapping("/jsp/projetos")
public class ProjetoController {

	private final ProjetoService portfolioService;

	@ResponseBody
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> cadastrarProjeto(Model model, @RequestBody ProjetoRequest projetoResquest) {
		portfolioService.cadastrarProjeto(projetoResquest);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Operação realizada com sucesso!");

		return response;
	}

	@ResponseBody
	@GetMapping(value = "/pesquisar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProjetoResponse> pesquisarProjetos(@RequestParam("valor") String valorParam,
														    	   @RequestParam(defaultValue = "0") int page,
																   @RequestParam(defaultValue = "5") int size) {
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return portfolioService.pesquisarProjetos(valorParam, PageRequest.of(page, size, sort));
    }

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public String listarProjetos(Model model, 
								@RequestParam(defaultValue = "0") int page,
								@RequestParam(defaultValue = "5") int size) {

		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Page<ProjetoResponse> projetosPage = portfolioService.listarProjetos(PageRequest.of(page, size, sort));
		model.addAttribute("projetos", projetosPage);

		return "projeto-lista";
	}

	@ResponseBody
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoRequest projetoResquest) {

		portfolioService.atualizarProjeto(id, projetoResquest);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Operação realizada com sucesso!");

		return response;
	}

	@ResponseBody
	@DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> excluirProjeto(@PathVariable Long id,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

		portfolioService.excluirProjeto(id, pageable);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Operação realizada com sucesso!");
		return response;
	}
}
