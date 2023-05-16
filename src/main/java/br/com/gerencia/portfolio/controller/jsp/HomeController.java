package br.com.gerencia.portfolio.controller.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Carlos Roberto
 * @created 11/05/2023
 * @since 1.0
 */
@Controller
@RequestMapping({"", "/", "/home"})
public class HomeController {

    @GetMapping
    public String home(Model model){	
    	return "home";
    }
}
