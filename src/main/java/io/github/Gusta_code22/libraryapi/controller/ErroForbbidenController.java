package io.github.Gusta_code22.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class ErroForbbidenController {

    @GetMapping("/403")
    public String acessoNegado(){
        return "403";
    }
}
