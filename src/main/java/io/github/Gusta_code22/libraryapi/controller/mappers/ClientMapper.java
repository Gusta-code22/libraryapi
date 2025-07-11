package io.github.Gusta_code22.libraryapi.controller.mappers;

import io.github.Gusta_code22.libraryapi.controller.dto.ClientDTO;
import io.github.Gusta_code22.libraryapi.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO dto);
}
