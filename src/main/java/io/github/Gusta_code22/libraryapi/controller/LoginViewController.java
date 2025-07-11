package io.github.Gusta_code22.libraryapi.controller;

import io.github.Gusta_code22.libraryapi.security.CustomAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "LoginView")
public class LoginViewController {

    @GetMapping("/login")
    @Operation(summary = "Login Page", description = "Volta para a página de login")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping("/")
    @Operation(summary = "PaginaHome")
    @ResponseBody
    public String paginaHome(Authentication authentication){
        if (authentication instanceof CustomAuthentication customAuth){
            System.out.println(customAuth.getUsuario());
        }

        return "Olá " + authentication.getName();

    }

    @GetMapping("/authorized")
    @ResponseBody
    @Operation(summary = "Pegar authorization Code")
    public String getAuthorizationCode(@RequestParam("code") String code){
        return "Seu authorization code: " + code;
    }
}
