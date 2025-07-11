package io.github.Gusta_code22.libraryapi.controller.mappers;

import io.github.Gusta_code22.libraryapi.controller.dto.AutorDTO;
import io.github.Gusta_code22.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "data_nascimento", target = "data_nascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
