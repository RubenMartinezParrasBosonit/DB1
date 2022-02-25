package io.github.ruben.persona.infrastructure.controller;

import io.github.ruben.persona.application.PersonaService;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RequestMapping("persona")
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET})
public class ReadPersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping
    public PersonaOutputDtoList findAll(@Value("simple") @RequestParam(name = "outputType", defaultValue = "simple", required = false) String outputType){
        return personaService.findAll(outputType);
    }

    @GetMapping("{id}")
    public PersonaOutputDto getPersonaById(@PathVariable Integer id, @Value("simple") @RequestParam(name = "outputType", defaultValue = "simple", required = false) String outputType){
        return personaService.filtrarPersonasPorId(id, outputType);
    }

    @GetMapping("/{usuario}/usuario")
    public PersonaOutputDtoList getPersonaByUsuario(@PathVariable String usuario, @Value("simple") @RequestParam(name = "outputType", defaultValue = "simple", required = false) String outputType){
        return personaService.filtrarPersonaPorNombreUsuario(usuario, outputType);
    }
}
