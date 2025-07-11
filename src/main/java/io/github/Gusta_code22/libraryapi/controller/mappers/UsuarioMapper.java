package io.github.Gusta_code22.libraryapi.controller.mappers;

import io.github.Gusta_code22.libraryapi.model.Usuario;
import io.github.Gusta_code22.libraryapi.controller.dto.UsuarioDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO usuarioDTO);
}
