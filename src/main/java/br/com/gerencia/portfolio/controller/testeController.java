package br.com.gerencia.portfolio.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Carlos Roberto
 * @created 10/05/2023
 * @since 1.0
 */
@Controller
public class testeController {

    @GetMapping("/home")
    public String exibirProjetos(Model model, HttpServletRequest httpServletRequest) {
        return "index";
    }
}
