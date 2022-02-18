package io.github.ruben.persona.infrastructure.controller;

import io.github.ruben.persona.application.PersonaService;
import io.github.ruben.persona.exceptions.IdNotFoundException;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("persona")
@RestController
public class ReadPersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping
    public List<PersonaOutputDto> findAll(){
        return personaService.todasLasPersonas();
    }

    @GetMapping("{id}")
    public PersonaOutputDto getPersonaById(@PathVariable Integer id){
        return personaService.filtrarPersonasPorId(id);
    }

    @GetMapping("/{usuario}/usuario")
    public List<PersonaOutputDto> getPersonaByUsuario(@PathVariable String usuario){
        return personaService.filtrarPersonaPorNombreUsuario(usuario);
    }
}
