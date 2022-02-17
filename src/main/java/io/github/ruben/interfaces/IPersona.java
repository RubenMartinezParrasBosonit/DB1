package io.github.ruben.interfaces;

import io.github.ruben.dtos.PersonaDTO;
import io.github.ruben.dtos.PersonaInputDTO;
import io.github.ruben.dtos.PersonaOutputDTO;
import io.github.ruben.objetos.Persona;

import java.util.List;

public interface IPersona {
    public List<PersonaOutputDTO> todasLasPersonas();
    public PersonaOutputDTO filtrarPersonasPorId(int id);
    public List<PersonaOutputDTO> filtrarPersonaPorNombreUsuario(String usuario);
    public PersonaOutputDTO aniadirPersona(PersonaInputDTO persona) throws Exception;
    public PersonaOutputDTO modificarPersona(Integer id, PersonaInputDTO persona) throws Exception;
    public String borrarPersona(Integer id);
}
