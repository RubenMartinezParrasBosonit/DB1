package io.github.ruben.mappers;

import io.github.ruben.dtos.PersonaDTO;
import io.github.ruben.objetos.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonaMapper {

    PersonaMapper INSTANCE = Mappers.getMapper( PersonaMapper.class );
    PersonaDTO PersonaToPersonaDTO(Persona persona);
    Persona PersonaDTOToPersona(PersonaDTO personaDTO);
}
