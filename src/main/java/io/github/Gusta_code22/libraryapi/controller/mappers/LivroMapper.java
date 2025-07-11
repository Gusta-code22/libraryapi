package io.github.Gusta_code22.libraryapi.controller.mappers;

import io.github.Gusta_code22.libraryapi.controller.dto.AutorDTO;
import io.github.Gusta_code22.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.Gusta_code22.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.Gusta_code22.libraryapi.model.Autor;
import io.github.Gusta_code22.libraryapi.model.Livro;
import io.github.Gusta_code22.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.id_autor()).orElse(null)) ")
    public abstract Livro toEntity(CadastroLivroDTO dto);

//    @Mapping(target = "id_autor", source = "autor.id")
    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
