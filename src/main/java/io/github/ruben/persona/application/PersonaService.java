package io.github.ruben.persona.application;

import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;

import java.util.List;

public interface PersonaService {
    public List<PersonaOutputDto> todasLasPersonas();
    public PersonaOutputDto filtrarPersonasPorId(int id);
    public List<PersonaOutputDto> filtrarPersonaPorNombreUsuario(String usuario);
    public PersonaOutputDto aniadirPersona(PersonaInputDto personaInputDto) throws Exception;
    public PersonaOutputDto modificarPersona(Integer id, PersonaInputDto personaInputDto) throws Exception;
    public void borrarPersona(Integer id);
}
