package io.github.ruben.persona.application;

import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDtoList;


public interface PersonaService {
    public PersonaOutputDtoList findAll(String outputType);
    public PersonaOutputDto filtrarPersonasPorId(int id, String outputType);
    public PersonaOutputDtoList filtrarPersonaPorNombreUsuario(String usuario, String outputType);
    public PersonaOutputDto aniadirPersona(PersonaInputDto personaInputDto);
    public PersonaOutputDto modificarPersona(Integer id, PersonaInputDto personaInputDto);
    public void borrarPersona(Integer id);
}
