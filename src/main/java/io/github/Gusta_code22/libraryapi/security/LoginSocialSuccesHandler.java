package io.github.Gusta_code22.libraryapi.security;

import io.github.Gusta_code22.libraryapi.model.Usuario;
import io.github.Gusta_code22.libraryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccesHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SENHA_PADRAO = "123";

    private final UsuarioService service;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request
            ,HttpServletResponse response, Authentication authentication) throws ServletException, IOException {


        // pegamos o usuario com o Oauth2 e pegamos o principal(USER)
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        // do user pegamos o Email (Campo Universal)
        String email = oAuth2User.getAttribute("email");

        // Pega o nome do provedor usado (ex: "google", "github")
        String provider = auth2AuthenticationToken.getAuthorizedClientRegistrationId();

        if (email == null){
            String login = oAuth2User.getAttribute("login");
            email = login + "@"+ provider +".com"; // e-mail fictício
        }

        Usuario usuario = service.obterPorEmail(email);

        if (usuario == null){
            usuario = cadastrarUsuarioNoBanco(email, provider);
        }

        // Cria a nova autenticação com base no usuário do banco
        Authentication customAuth = new CustomAuthentication(usuario);
        SecurityContextHolder.getContext().setAuthentication(customAuth);



        super.onAuthenticationSuccess(request, response, authentication);

        System.out.println(email);

    }

    private Usuario cadastrarUsuarioNoBanco(String email, String provider) {
        Usuario usuario;
        usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setLogin(obterLoginAPartirEmail(email));
        usuario.setSenha(SENHA_PADRAO);
        usuario.setProvider(provider);
        usuario.setRoles(List.of("OPERADOR"));
        service.salvar(usuario);
        return usuario;
    }


    private String obterLoginAPartirEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

}
