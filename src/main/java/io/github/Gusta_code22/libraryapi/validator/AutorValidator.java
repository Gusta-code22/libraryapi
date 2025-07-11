package io.github.Gusta_code22.libraryapi.validator;

import io.github.Gusta_code22.libraryapi.exceptions.RegistoDuplicadoException;
import io.github.Gusta_code22.libraryapi.model.Autor;
import io.github.Gusta_code22.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor){
        if (existeAutorCadastrado(autor)){
            throw new RegistoDuplicadoException("Autor Já cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = autorRepository.buscaPorNomeDataNascimentoENacionalidade(
                autor.getNome(), autor.getData_nascimento(), autor.getNacionalidade()
        );

        if(autor.getId() == null){ // se o autor for novo
            return autorEncontrado.isPresent(); // se ja tiver um cadastrado recebe um True
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
        // se o id que eu estou mexendo nao for igual ao que está e ja tem um igual ele retorna true
    }


}
